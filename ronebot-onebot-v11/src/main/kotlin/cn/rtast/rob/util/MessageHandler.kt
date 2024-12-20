/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/26
 */


package cn.rtast.rob.util

import cn.rtast.rob.BotInstance
import cn.rtast.rob.ROneBotFactory
import cn.rtast.rob.entity.*
import cn.rtast.rob.entity.custom.*
import cn.rtast.rob.entity.lagrange.FileEvent
import cn.rtast.rob.entity.lagrange.PokeEvent
import cn.rtast.rob.entity.metadata.*
import cn.rtast.rob.enums.ArrayMessageType
import cn.rtast.rob.enums.BrigadierMessageType
import cn.rtast.rob.enums.MessageType
import cn.rtast.rob.enums.internal.*
import cn.rtast.rob.onebot.OneBotAction
import cn.rtast.rob.onebot.OneBotListener
import kotlinx.coroutines.CompletableDeferred
import org.java_websocket.WebSocket
import java.util.*
import java.util.concurrent.ConcurrentHashMap

class MessageHandler(
    private val botInstance: BotInstance,
    private val action: OneBotAction,
) {
    private val logger = Logger.getLogger()
    internal val suspendedRequests = ConcurrentHashMap<UUID, CompletableDeferred<String>>()

    suspend fun onMessage(listener: OneBotListener, message: String) {
        try {
            val serializedMessage = message.fromJson<BaseEventMessage>()
            serializedMessage.echo?.let { suspendedRequests.remove(serializedMessage.echo)?.complete(message) }
            listener.onMessage(action, message)
            if (serializedMessage.metaEventType != null) {
                when (serializedMessage.metaEventType) {
                    MetaEventType.heartbeat -> {
                        val event = message.fromJson<HeartBeatEvent>()
                        event.action = action
                        listener.onHeartBeatEvent(event)
                    }

                    MetaEventType.lifecycle -> {
                        val event = message.fromJson<ConnectEvent>()
                        event.action = action
                        listener.onConnectEvent(event)
                    }
                }
                return
            }

            if (serializedMessage.postType == PostType.message) {
                when (serializedMessage.messageType) {
                    MessageType.group -> {
                        val msg = message.fromJson<GroupMessage>()
                        msg.action = action
                        val oldSender = msg.sender
                        val newSenderWithGroupId = GroupSender(
                            action,
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
                        if (msg.groupId !in botInstance.listenedGroups && botInstance.listenedGroups.isNotEmpty()) return
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
                        ROneBotFactory.commandManager.handleGroup(msg)
                        ROneBotFactory.brigadierCommandManager.execute(msg.text, msg, BrigadierMessageType.Group)
                    }

                    MessageType.private -> {
                        val msg = message.fromJson<PrivateMessage>()
                        msg.action = action
                        msg.message.forEach {
                            if (it.type == ArrayMessageType.reply) {
                                listener.onBeRepliedInPrivate(msg)
                                return@forEach
                            }
                        }
                        listener.onPrivateMessage(msg, message)
                        ROneBotFactory.commandManager.handlePrivate(msg)
                        ROneBotFactory.brigadierCommandManager.execute(msg.text, msg, BrigadierMessageType.Private)
                    }

                    null -> listener.onMessage(action, message)
                }
                return
            }

            if (serializedMessage.postType == PostType.request) {
                when (serializedMessage.requestType) {
                    RequestType.friend -> {
                        val event = message.fromJson<AddFriendRequestEvent>()
                        event.action = action
                        listener.onAddFriendRequest(event)
                    }

                    null -> {}
                }
                serializedMessage.subType?.let {
                    when (serializedMessage.subType) {
                        SubType.add -> {
                            val event = message.fromJson<JoinGroupRequestEvent>()
                            event.action = action
                            listener.onJoinRequest(event)
                        }

                        else -> {}
                    }
                }
            }

            if (serializedMessage.postType == PostType.notice) {
                val time = serializedMessage.time
                val msg = message.fromJson<NoticeEvent>()
                if (msg.groupId != null && msg.groupId !in botInstance.listenedGroups
                    && botInstance.listenedGroups.isNotEmpty()
                ) return
                when (serializedMessage.noticeType) {
                    NoticeType.group_recall -> {
                        listener.onGroupMessageRevoke(
                            GroupRevokeMessage(
                                action,
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
                                action,
                                msg.userId,
                                msg.messageId!!,
                                msg.operatorId
                            )
                        )
                        return
                    }

                    NoticeType.group_upload, NoticeType.offline_file -> {
                        val file = message.fromJson<FileEvent>()
                        file.action = action
                        if (file.groupId == null) {
                            listener.onPrivateFileUpload(file)
                        } else {
                            listener.onGroupFileUpload(file)
                        }
                        return
                    }

                    NoticeType.reaction -> {
                        val event = message.fromJson<ReactionEvent>()
                        event.action = action
                        if (serializedMessage.subType == SubType.remove) {
                            listener.onReaction(event)
                        } else if (serializedMessage.subType == SubType.add) {
                            listener.onReactionRemoved(event)
                        }
                    }

                    NoticeType.group_name_change -> {
                        val event = message.fromJson<GroupNameChangeEvent>()
                        event.action = action
                        listener.onGroupNameChanged(event)
                    }

                    NoticeType.bot_offline -> {
                        val event = message.fromJson<BotOfflineEvent>()
                        event.action = action
                        listener.onBotOffline(event)
                    }

                    NoticeType.bot_online -> {
                        val event = message.fromJson<BotOnlineEvent>()
                        event.action = action
                        listener.onBotOnline(event)
                    }

                    null -> {}
                }
                serializedMessage.subType?.let {
                    when (serializedMessage.subType) {
                        SubType.kick -> listener.onMemberKick(
                            MemberKickEvent(msg.groupId!!, msg.operatorId, time, msg.userId, action)
                        )

                        SubType.kick_me -> listener.onBeKicked(
                            BeKickEvent(msg.groupId!!, msg.operatorId, time, msg.userId, action)
                        )

                        SubType.unset -> listener.onUnsetOperator(
                            UnsetOperatorEvent(msg.groupId!!, msg.operatorId, time, msg.userId, action)
                        )

                        SubType.set -> {
                            listener.onSetOperator(
                                SetOperatorEvent(msg.groupId!!, msg.operatorId, time, msg.userId, action)
                            )
                        }

                        SubType.ban -> listener.onBan(
                            BanEvent(msg.groupId!!, msg.operatorId, msg.duration!!, time, msg.userId, action)
                        )

                        SubType.lift_ban -> listener.onPardon(
                            PardonEvent(msg.groupId!!, msg.operatorId, msg.duration!!, time, msg.userId, action)
                        )

                        SubType.leave -> listener.onLeaveEvent(
                            MemberLeaveEvent(msg.groupId!!, msg.userId, msg.operatorId, time, action)
                        )

                        SubType.invite -> listener.onBeInviteEvent(
                            BeInviteEvent(msg.groupId!!, msg.userId, msg.operatorId, time, action)
                        )

                        SubType.approve -> listener.onApproveEvent(
                            ApproveEvent(msg.groupId!!, msg.userId, msg.operatorId, time, action)
                        )

                        SubType.poke -> {
                            val poke = message.fromJson<PokeEvent>()
                            poke.action = action
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
        logger.info("New connection: ${websocket.remoteSocketAddress}")
        listener.onWebsocketOpenEvent(action)
    }

    suspend fun onClose(listener: OneBotListener, code: Int, reason: String, remote: Boolean, ws: WebSocket) {
        logger.info("Websocket connection closed(${ws.remoteSocketAddress})")
        listener.onWebsocketCloseEvent(CloseEvent(action, code, reason, remote))
    }

    suspend fun onStart(listener: OneBotListener, port: Int) {
        logger.info("Websocket server started on $port")
        listener.onWebsocketServerStartEvent(action)
    }

    suspend fun onError(listener: OneBotListener, ex: Exception) {
        logger.info("Websocket connection error: ${ex.message}")
        listener.onWebsocketErrorEvent(ErrorEvent(action, ex))
    }
}