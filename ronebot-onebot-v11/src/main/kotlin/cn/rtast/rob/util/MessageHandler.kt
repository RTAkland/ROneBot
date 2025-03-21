/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/26
 */

@file:Suppress("Deprecation")

package cn.rtast.rob.util

import cn.rtast.rob.BotInstance
import cn.rtast.rob.OneBotFactory
import cn.rtast.rob.enums.InboundMessageType
import cn.rtast.rob.enums.internal.*
import cn.rtast.rob.event.dispatchEvent
import cn.rtast.rob.event.packed.*
import cn.rtast.rob.event.raw.*
import cn.rtast.rob.event.raw.custom.*
import cn.rtast.rob.event.raw.lagrange.RawFileEvent
import cn.rtast.rob.event.raw.lagrange.RawPokeEvent
import cn.rtast.rob.event.raw.metadata.*
import cn.rtast.rob.onebot.OneBotAction
import cn.rtast.rob.onebot.OneBotListener
import kotlinx.coroutines.CompletableDeferred
import org.java_websocket.WebSocket
import java.util.*
import java.util.concurrent.ConcurrentHashMap

internal class MessageHandler(
    private val botInstance: BotInstance,
    private val action: OneBotAction,
) {
    private val logger = Logger.getLogger()
    internal val suspendedRequests = ConcurrentHashMap<UUID, CompletableDeferred<String>>()

    internal suspend fun onMessage(listener: OneBotListener, message: String) {
        try {
            val serializedMessage = message.fromJson<BaseEventMessage>()
            serializedMessage.echo?.let { suspendedRequests.remove(serializedMessage.echo)?.complete(message) }
            listener.onRawMessage(action, message)
            botInstance.dispatchEvent(RawEvent(action, message))
            if (!OneBotFactory.botManager.getBotInstanceStatus(botInstance)) return
            if (serializedMessage.metaEventType != null) {
                when (serializedMessage.metaEventType) {
                    MetaEventType.heartbeat -> {
                        val event = message.fromJson<RawHeartBeatEvent>()
                        event.action = action
                        listener.onHeartBeatEvent(event)
                    }

                    MetaEventType.lifecycle -> {
                        val event = message.fromJson<RawConnectEvent>()
                        event.action = action
                        listener.onConnectEvent(event)
                    }
                }
                return
            }

            if (serializedMessage.postType == PostType.message) {
                when (serializedMessage.messageType) {
                    InboundMessageType.group -> {
                        val msg = message.fromJson<GroupMessage>()
                        msg.sessionId = UUID.randomUUID()
                        msg.action = action
                        val newSenderWithGroupId = GroupSender(
                            action,
                            msg.sender.userId,
                            msg.sender.nickname,
                            msg.sender.sex,
                            msg.sender.role,
                            msg.sender.card,
                            msg.sender.level,
                            msg.sender.age,
                            msg.sender.title,
                            msg.groupId
                        )
                        msg.sender = newSenderWithGroupId
                        if (msg.groupId !in botInstance.listenedGroups && botInstance.listenedGroups.isNotEmpty()) return
                        botInstance.dispatchEvent(GroupMessageEvent(action, msg))
                        listener.onGroupMessage(msg)
                        listener.onGroupMessage(msg, message)
                        OneBotFactory.commandManager.handleGroup(msg)
                    }

                    InboundMessageType.private -> {
                        val msg = message.fromJson<PrivateMessage>()
                        msg.sessionId = UUID.randomUUID()
                        msg.action = action
                        msg.sender.action = action
                        botInstance.dispatchEvent(PrivateMessageEvent(action, msg))
                        listener.onPrivateMessage(msg)
                        listener.onPrivateMessage(msg, message)
                        OneBotFactory.commandManager.handlePrivate(msg)
                    }

                    null -> listener.onRawMessage(action, message)
                }
                return
            }

            if (serializedMessage.postType == PostType.request) {
                when (serializedMessage.requestType) {
                    RequestType.friend -> {
                        val event = message.fromJson<AddFriendRequestEvent>()
                        event.action = action
                        botInstance.dispatchEvent(AddFriendEvent(action, event))
                        listener.onAddFriendRequest(event)
                    }

                    null -> {}
                }
                serializedMessage.subType?.let {
                    when (serializedMessage.subType) {
                        SubType.add -> {
                            val event = message.fromJson<JoinGroupRequestEvent>()
                            event.action = action
                            botInstance.dispatchEvent(RequestJoinGroupEvent(action, event))
                            listener.onJoinRequest(event)
                        }

                        else -> {}
                    }
                }
            }

            if (serializedMessage.postType == PostType.notice) {
                val time = serializedMessage.time
                val msg = message.fromJson<NoticeEvent>()
                when (serializedMessage.noticeType) {
                    NoticeType.group_recall -> {
                        val msg = RawGroupRevokeMessage(
                            action,
                            msg.groupId!!,
                            msg.userId,
                            msg.messageId!!,
                            msg.operatorId
                        )
                        if (msg.groupId !in botInstance.listenedGroups &&
                            botInstance.listenedGroups.isNotEmpty() &&
                            botInstance.enableEventListenerFilter
                        ) return
                        botInstance.dispatchEvent(GroupMessageRevokeEvent(action, msg))
                        listener.onGroupMessageRevoke(msg)
                        return
                    }

                    NoticeType.friend_recall -> {
                        val msg = RawPrivateRevokeMessage(
                            action,
                            msg.userId,
                            msg.messageId!!,
                            msg.operatorId
                        )
                        botInstance.dispatchEvent(PrivateMessageRevokeEvent(action, msg))
                        listener.onPrivateMessageRevoke(msg)
                        return
                    }

                    NoticeType.group_upload, NoticeType.offline_file -> {
                        val file = message.fromJson<RawFileEvent>()
                        file.action = action
                        if (file.groupId == null) {
                            botInstance.dispatchEvent(PrivateFileUploadEvent(action, file))
                            listener.onPrivateFileUpload(file)
                        } else {
                            if (file.groupId !in botInstance.listenedGroups &&
                                botInstance.listenedGroups.isNotEmpty() &&
                                botInstance.enableEventListenerFilter
                            ) return
                            botInstance.dispatchEvent(GroupFileUploadEvent(action, file))
                            listener.onGroupFileUpload(file)
                        }
                        return
                    }

                    NoticeType.reaction -> {
                        val event = message.fromJson<ReactionEvent>()
                        event.action = action
                        if (event.groupId !in botInstance.listenedGroups &&
                            botInstance.listenedGroups.isNotEmpty() &&
                            botInstance.enableEventListenerFilter
                        ) return
                        botInstance.dispatchEvent(ReactionCommonEvent(action, event))
                        if (serializedMessage.subType == SubType.add) {
                            botInstance.dispatchEvent(ReactionAddEvent(action, event))
                            listener.onReaction(event)
                        } else if (serializedMessage.subType == SubType.remove) {
                            botInstance.dispatchEvent(ReactionRemoveEvent(action, event))
                            listener.onReactionRemoved(event)
                        }
                    }

                    NoticeType.group_name_change -> {
                        val event = message.fromJson<RawGroupNameChangeEvent>()
                        event.action = action
                        if (event.groupId !in botInstance.listenedGroups &&
                            botInstance.listenedGroups.isNotEmpty() &&
                            botInstance.enableEventListenerFilter
                        ) return
                        botInstance.dispatchEvent(GroupNameChangedEvent(action, event))
                        listener.onGroupNameChanged(event)
                    }

                    NoticeType.bot_offline -> {
                        val event = message.fromJson<RawBotOfflineEvent>()
                        event.action = action
                        botInstance.dispatchEvent(BotOfflineEvent(action, event))
                        listener.onBotOffline(event)
                    }

                    NoticeType.bot_online -> {
                        val event = message.fromJson<RawBotOnlineEvent>()
                        event.action = action
                        botInstance.dispatchEvent(BotOnlineEvent(action, event))
                        listener.onBotOnline(event)
                    }

                    null -> {}
                }
                serializedMessage.subType?.let {
                    when (serializedMessage.subType) {
                        SubType.kick -> {
                            val event = RawMemberKickEvent(msg.groupId!!, msg.operatorId, time, msg.userId, action)
                            if (event.groupId !in botInstance.listenedGroups &&
                                botInstance.listenedGroups.isNotEmpty() &&
                                botInstance.enableEventListenerFilter
                            ) return
                            botInstance.dispatchEvent(MemberKickEvent(action, event))
                            listener.onMemberKick(event)
                        }

                        SubType.kick_me -> {
                            val event = RawBotBeKickEvent(msg.groupId!!, msg.operatorId, time, msg.userId, action)
                            botInstance.dispatchEvent(BotBeKickEvent(action, event))
                            listener.onBeKicked(event)
                        }

                        SubType.unset -> {
                            val event = RawUnsetOperatorEvent(msg.groupId!!, msg.operatorId, time, msg.userId, action)
                            if (event.groupId !in botInstance.listenedGroups &&
                                botInstance.listenedGroups.isNotEmpty() &&
                                botInstance.enableEventListenerFilter
                            ) return
                            botInstance.dispatchEvent(UnsetOperatorEvent(action, event))
                            listener.onUnsetOperator(event)
                        }

                        SubType.set -> {
                            val event = RawSetOperatorEvent(msg.groupId!!, msg.operatorId, time, msg.userId, action)
                            if (event.groupId !in botInstance.listenedGroups &&
                                botInstance.listenedGroups.isNotEmpty() &&
                                botInstance.enableEventListenerFilter
                            ) return
                            listener.onSetOperator(event)
                        }

                        SubType.ban -> {
                            val event =
                                RawBanEvent(msg.groupId!!, msg.operatorId, msg.duration!!, time, msg.userId, action)
                            if (event.groupId !in botInstance.listenedGroups &&
                                botInstance.listenedGroups.isNotEmpty() &&
                                botInstance.enableEventListenerFilter
                            ) return
                            botInstance.dispatchEvent(BanEvent(action, event))
                            listener.onBan(event)
                        }

                        SubType.lift_ban -> {
                            val event =
                                RawPardonBanEvent(
                                    msg.groupId!!, msg.operatorId, msg.duration!!,
                                    time, msg.userId, action
                                )
                            if (event.groupId !in botInstance.listenedGroups &&
                                botInstance.listenedGroups.isNotEmpty() &&
                                botInstance.enableEventListenerFilter
                            ) return
                            botInstance.dispatchEvent(PardonBanEvent(action, event))
                            listener.onPardon(event)
                        }

                        SubType.leave -> {
                            val event =
                                RawGroupMemberLeaveEvent(msg.groupId!!, msg.userId, msg.operatorId, time, action)
                            if (event.groupId !in botInstance.listenedGroups &&
                                botInstance.listenedGroups.isNotEmpty() &&
                                botInstance.enableEventListenerFilter
                            ) return
                            botInstance.dispatchEvent(GroupMemberLeaveEvent(action, event))
                            listener.onLeaveEvent(event)
                        }

                        SubType.invite -> {
                            val event = RawMemberBeInviteEvent(msg.groupId!!, msg.userId, msg.operatorId, time, action)
                            if (event.groupId !in botInstance.listenedGroups &&
                                botInstance.listenedGroups.isNotEmpty() &&
                                botInstance.enableEventListenerFilter
                            ) return
                            botInstance.dispatchEvent(GroupBeInviteEvent(action, event))
                            listener.onBeInviteEvent(event)
                        }

                        SubType.approve -> {
                            val event =
                                RawJoinRequestApproveEvent(msg.groupId!!, msg.userId, msg.operatorId, time, action)
                            if (event.groupId !in botInstance.listenedGroups &&
                                botInstance.listenedGroups.isNotEmpty() &&
                                botInstance.enableEventListenerFilter
                            ) return
                            botInstance.dispatchEvent(GroupMemberApproveEvent(action, event))
                            listener.onApproveEvent(event)
                        }

                        SubType.poke -> {
                            val poke = message.fromJson<RawPokeEvent>()
                            val selfUserId = action.getLoginInfo().userId
                            poke.action = action
                            if (poke.groupId != null) {
                                if (poke.groupId !in botInstance.listenedGroups &&
                                    botInstance.listenedGroups.isNotEmpty() &&
                                    botInstance.enableEventListenerFilter
                                ) return
                                botInstance.dispatchEvent(GroupPokeEvent(action, poke))
                                listener.onGroupPoke(poke)
                                if (poke.targetId == selfUserId) {
                                    botInstance.dispatchEvent(GroupPokeSelfEvent(action, poke))
                                    listener.onGroupPokeSelf(poke)
                                }
                            } else {
                                botInstance.dispatchEvent(PrivatePokeEvent(action, poke))
                                listener.onPrivatePoke(poke)
                                if (poke.targetId == selfUserId) listener.onPrivatePokeSelf(poke)
                            }
                        }

                        else -> {}
                    }
                }
                return
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            this.onError(listener, ex)
        }
    }

    suspend fun onOpen(listener: OneBotListener, websocket: WebSocket) {
        logger.info("New connection: ${websocket.remoteSocketAddress}")
        botInstance.dispatchEvent(WebsocketConnectedEvent(action))
        listener.onWebsocketOpenEvent(action)
    }

    suspend fun onClose(listener: OneBotListener, code: Int, reason: String, remote: Boolean, ws: WebSocket) {
        logger.info("Websocket connection closed(${ws.remoteSocketAddress})")
        val event = RawWebsocketCloseEvent(action, code, reason, remote)
        botInstance.dispatchEvent(WebsocketCloseEvent(action, event))
        listener.onWebsocketClosedEvent(event)
    }

    suspend fun onStart(listener: OneBotListener, port: Int) {
        logger.info("Websocket server started on $port")
        botInstance.dispatchEvent(WebsocketServerStartedEvent(action, port))
        listener.onWebsocketServerStartedEvent(action)
    }

    suspend fun onError(listener: OneBotListener, ex: Exception) {
        logger.error("Websocket connection error: ${ex.message}")
        ex.printStackTrace()
        val event = RawWebsocketErrorEvent(action, ex)
        botInstance.dispatchEvent(WebsocketErrorEvent(action, event))
        listener.onWebsocketErrorEvent(event)
    }
}