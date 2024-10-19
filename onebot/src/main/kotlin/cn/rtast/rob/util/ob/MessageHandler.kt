/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/26
 */


package cn.rtast.rob.util.ob

import cn.rtast.rob.ROneBotFactory
import cn.rtast.rob.ROneBotFactory.commandManager
import cn.rtast.rob.common.util.fromJson
import cn.rtast.rob.entity.*
import cn.rtast.rob.entity.lagrange.FileEvent
import cn.rtast.rob.entity.lagrange.PokeEvent
import cn.rtast.rob.entity.metadata.*
import cn.rtast.rob.enums.*
import cn.rtast.rob.enums.internal.*
import cn.rtast.rob.enums.MessageType
import cn.rtast.rob.enums.internal.MetaEventType
import cn.rtast.rob.enums.internal.NoticeType
import cn.rtast.rob.enums.internal.PostType
import cn.rtast.rob.enums.internal.SubType
import kotlinx.coroutines.CompletableDeferred
import org.java_websocket.WebSocket
import java.util.concurrent.ConcurrentHashMap

object MessageHandler {
    private val listeningGroups = ROneBotFactory.getListeningGroups()
    internal val suspendedRequests = ConcurrentHashMap<MessageEchoType, CompletableDeferred<String>>()

    suspend fun onMessage(listener: OneBotListener, message: String) {
        val serializedMessage = message.fromJson<BaseMessage>()
        serializedMessage.echo?.let {
            suspendedRequests.remove(serializedMessage.echo)?.complete(message)
        }
        try {
            listener.onMessage(message)
            if (serializedMessage.metaEventType != null) {
                when (serializedMessage.metaEventType) {
                    MetaEventType.heartbeat -> listener.onHeartBeatEvent(message.fromJson<HeartBeatEvent>())
                    MetaEventType.lifecycle -> listener.onConnectEvent(message.fromJson<ConnectEvent>())
                }
                return
            }

            if (serializedMessage.postType == PostType.message) {
                when (serializedMessage.messageType) {
                    MessageType.group -> {
                        val msg = message.fromJson<GroupMessage>()
                        val oldSender = msg.sender
                        val newSenderWithGroupId = GroupSender(
                            oldSender.userId,
                            oldSender.nickname,
                            oldSender.sex,
                            oldSender.role,
                            oldSender.card,
                            oldSender.level,
                            oldSender.age,
                            oldSender.title,
                            msg.groupId
                        )
                        msg.sender = newSenderWithGroupId
                        if (msg.groupId !in listeningGroups && listeningGroups.isNotEmpty()) return
                        msg.message.distinctBy { it.type }.forEach {
                            if (it.type == ArrayMessageType.reply) {
                                listener.onBeRepliedInGroup(msg)
                                return@forEach
                            }
                            if (it.type == ArrayMessageType.at) {
                                listener.onBeAt(msg)
                                return@forEach
                            }
                        }
                        listener.onGroupMessage(msg, message)
                        commandManager.handleGroup(listener, msg)
                    }

                    MessageType.private -> {
                        val msg = message.fromJson<PrivateMessage>()
                        msg.message.forEach {
                            if (it.type == ArrayMessageType.reply) {
                                listener.onBeRepliedInPrivate(msg)
                                return@forEach
                            }
                        }
                        listener.onPrivateMessage(msg, message)
                        commandManager.handlePrivate(listener, msg)
                    }

                    null -> listener.onMessage(message)
                }
                return
            }

            if (serializedMessage.postType == PostType.request) {
                when (serializedMessage.requestType) {
                    RequestType.friend -> listener.onAddFriendRequest(message.fromJson<AddFriendRequest>())
                    null -> {}
                }
                serializedMessage.subType?.let {
                    when (serializedMessage.subType) {
                        SubType.add -> listener.onJoinRequest(message.fromJson<JoinGroupRequest>())
                        else -> {}
                    }
                }
            }

            if (serializedMessage.postType == PostType.notice) {
                val time = serializedMessage.time
                val msg = message.fromJson<NoticeEvent>()
                if (msg.groupId != null && msg.groupId !in listeningGroups && listeningGroups.isNotEmpty()) return
                when (serializedMessage.noticeType) {
                    NoticeType.group_recall -> {
                        listener.onGroupMessageRevoke(
                            GroupRevokeMessage(
                                msg.groupId!!,
                                msg.userId,
                                msg.messageId!!,
                                msg.operatorId
                            )
                        )
                        return
                    }

                    NoticeType.friend_recall -> {
                        listener.onPrivateMessageRevoke(
                            PrivateRevokeMessage(
                                msg.userId,
                                msg.messageId!!,
                                msg.operatorId
                            )
                        )
                        return
                    }

                    NoticeType.group_upload, NoticeType.offline_file -> {
                        val file = message.fromJson<FileEvent>()
                        if (file.groupId == null) {
                            listener.onPrivateFileUpload(file)
                        } else {
                            listener.onGroupFileUpload(file)
                        }
                        return
                    }

                    NoticeType.reaction -> {
                        val event = message.fromJson<ReactionEvent>()
                        if (serializedMessage.subType == SubType.remove) {
                            listener.onReaction(event)
                        } else if (serializedMessage.subType == SubType.add) {
                            listener.onReactionRemoved(event)
                        }
                    }

                    null -> {}
                }
                serializedMessage.subType?.let {
                    when (serializedMessage.subType) {
                        SubType.kick -> listener.onMemberKick(msg.groupId!!, msg.operatorId, time)
                        SubType.kick_me -> listener.onBeKicked(msg.groupId!!, msg.operatorId, time)
                        SubType.unset -> listener.onUnsetOperator(msg.groupId!!, msg.operatorId, time)
                        SubType.set -> listener.onSetOperator(msg.groupId!!, msg.operatorId, time)
                        SubType.ban -> listener.onBan(msg.groupId!!, msg.operatorId, msg.duration!!, time)
                        SubType.lift_ban -> listener.onPardon(msg.groupId!!, msg.operatorId, msg.duration!!, time)
                        SubType.leave -> listener.onLeaveEvent(msg.groupId!!, msg.userId, msg.operatorId, time)
                        SubType.invite -> listener.onInviteEvent(msg.groupId!!, msg.userId, msg.operatorId, time)
                        SubType.approve -> listener.onApproveEvent(msg.groupId!!, msg.userId, msg.operatorId, time)
                        SubType.poke -> {
                            val poke = message.fromJson<PokeEvent>()
                            if (poke.groupId != null) listener.onGroupPoke(poke) else listener.onPrivatePoke(poke)
                        }
                        else -> {}
                    }
                }
                return
            }
        } catch (ex: Exception) {
            this.onError(listener, ex)
        }
    }

    suspend fun onOpen(listener: OneBotListener, websocket: WebSocket) {
        println("New connection: ${websocket.remoteSocketAddress}")
        listener.onWebsocketOpenEvent()
    }

    suspend fun onClose(listener: OneBotListener, code: Int, reason: String, remote: Boolean, ws: WebSocket) {
        println("Websocket connection closed(${ws.remoteSocketAddress})")
        listener.onWebsocketCloseEvent(code, reason, remote)
    }

    suspend fun onStart(listener: OneBotListener, port: Int) {
        println("Websocket server started on $port")
        listener.onWebsocketServerStartEvent()
    }

    suspend fun onError(listener: OneBotListener, ex: Exception) {
        listener.onWebsocketErrorEvent(ex)
    }
}