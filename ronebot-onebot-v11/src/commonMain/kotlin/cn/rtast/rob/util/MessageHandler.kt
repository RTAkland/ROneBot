/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/26
 */

@file:Suppress("Deprecation")
@file:OptIn(ExperimentalUuidApi::class)

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
import cn.rtast.rob.onebot.OneBotListener
import co.touchlab.stately.collections.ConcurrentMutableMap
import kotlinx.coroutines.CompletableDeferred
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

internal class MessageHandler(
    private val botInstance: BotInstance,
) {
    internal val suspendedRequests = ConcurrentMutableMap<Uuid, CompletableDeferred<String>>()

    internal suspend fun onMessage(listener: OneBotListener, message: String) {
        try {
            val serializedMessage = message.fromJson<BaseEventMessage>()
            serializedMessage.echo?.let { suspendedRequests.remove(serializedMessage.echo)?.complete(message) }
            listener.onRawMessage(botInstance.action, message)
            botInstance.dispatchEvent(RawEvent(botInstance.action, message))
            if (!OneBotFactory.botManager.getBotInstanceStatus(botInstance)) return
            if (serializedMessage.metaEventType != null) {
                when (serializedMessage.metaEventType) {
                    MetaEventType.heartbeat -> {
                        val event = message.fromJson<RawHeartBeatEvent>()
                        event.action = botInstance.action
                        listener.onHeartBeatEvent(event)
                    }

                    MetaEventType.lifecycle -> {
                        val event = message.fromJson<RawConnectEvent>()
                        event.action = botInstance.action
                        listener.onConnectEvent(event)
                    }
                }
                return
            }

            if (serializedMessage.postType == PostType.message) {
                when (serializedMessage.messageType) {
                    InboundMessageType.group -> {
                        val msg = message.fromJson<GroupMessage>()
                        msg.sessionId = Uuid.random()
                        msg.action = botInstance.action
                        val newSenderWithGroupId = GroupSender(
                            msg.sender.userId,
                            msg.sender.nickname,
                            msg.sender.sex,
                            msg.sender.role,
                            msg.sender.card,
                            msg.sender.level,
                            msg.sender.age,
                            msg.sender.title,
                            msg.groupId
                        ).apply {
                            action = botInstance.action
                        }
                        msg.sender = newSenderWithGroupId
                        if (msg.groupId !in botInstance.listenedGroups && botInstance.listenedGroups.isNotEmpty()) return
                        botInstance.dispatchEvent(GroupMessageEvent(botInstance.action, msg))
                        listener.onGroupMessage(msg)
                        listener.onGroupMessage(msg, message)
                        OneBotFactory.commandManager.handleGroup(msg)
                    }

                    InboundMessageType.private -> {
                        val msg = message.fromJson<PrivateMessage>()
                        msg.sessionId = Uuid.random()
                        msg.action = botInstance.action
                        msg.sender.action = botInstance.action
                        botInstance.dispatchEvent(PrivateMessageEvent(botInstance.action, msg))
                        listener.onPrivateMessage(msg)
                        listener.onPrivateMessage(msg, message)
                        OneBotFactory.commandManager.handlePrivate(msg)
                    }

                    null -> listener.onRawMessage(botInstance.action, message)
                }
                return
            }

            if (serializedMessage.postType == PostType.request) {
                when (serializedMessage.requestType) {
                    RequestType.friend -> {
                        val event = message.fromJson<AddFriendRequestEvent>()
                        event.action = botInstance.action
                        botInstance.dispatchEvent(AddFriendEvent(botInstance.action, event))
                        listener.onAddFriendRequest(event)
                    }

                    null -> {}
                }
                serializedMessage.subType?.let {
                    when (serializedMessage.subType) {
                        SubType.add -> {
                            val event = message.fromJson<JoinGroupRequestEvent>()
                            event.action = botInstance.action
                            botInstance.dispatchEvent(RequestJoinGroupEvent(botInstance.action, event))
                            listener.onJoinRequest(event)
                        }

                        else -> {}
                    }
                }
            }

            if (serializedMessage.postType == PostType.notice) {
                val time = serializedMessage.time!!
                val msg = message.fromJson<NoticeEvent>()
                when (serializedMessage.noticeType) {
                    NoticeType.group_recall -> {
                        val revokeMessage = RawGroupRevokeMessage(
                            msg.groupId!!,
                            msg.userId,
                            msg.messageId!!,
                            msg.operatorId
                        ).apply {
                            action = botInstance.action
                        }
                        if (revokeMessage.groupId !in botInstance.listenedGroups &&
                            botInstance.listenedGroups.isNotEmpty() &&
                            botInstance.enableEventListenerFilter
                        ) return
                        botInstance.dispatchEvent(GroupMessageRevokeEvent(botInstance.action, revokeMessage))
                        listener.onGroupMessageRevoke(revokeMessage)
                        return
                    }

                    NoticeType.friend_recall -> {
                        val msg = RawPrivateRevokeMessage(
                            msg.userId,
                            msg.messageId!!,
                            msg.operatorId,
                        ).apply {
                            action = botInstance.action
                        }
                        botInstance.dispatchEvent(PrivateMessageRevokeEvent(botInstance.action, msg))
                        listener.onPrivateMessageRevoke(msg)
                        return
                    }

                    NoticeType.group_upload, NoticeType.offline_file -> {
                        val file = message.fromJson<RawFileEvent>()
                        file.action = botInstance.action
                        if (file.groupId == null) {
                            botInstance.dispatchEvent(PrivateFileUploadEvent(botInstance.action, file))
                            listener.onPrivateFileUpload(file)
                        } else {
                            if (file.groupId !in botInstance.listenedGroups &&
                                botInstance.listenedGroups.isNotEmpty() &&
                                botInstance.enableEventListenerFilter
                            ) return
                            botInstance.dispatchEvent(GroupFileUploadEvent(botInstance.action, file))
                            listener.onGroupFileUpload(file)
                        }
                        return
                    }

                    NoticeType.reaction -> {
                        val event = message.fromJson<ReactionEvent>()
                        event.action = botInstance.action
                        if (event.groupId !in botInstance.listenedGroups &&
                            botInstance.listenedGroups.isNotEmpty() &&
                            botInstance.enableEventListenerFilter
                        ) return
                        botInstance.dispatchEvent(ReactionCommonEvent(botInstance.action, event))
                        if (serializedMessage.subType == SubType.add) {
                            botInstance.dispatchEvent(ReactionAddEvent(botInstance.action, event))
                            listener.onReaction(event)
                        } else if (serializedMessage.subType == SubType.remove) {
                            botInstance.dispatchEvent(ReactionRemoveEvent(botInstance.action, event))
                            listener.onReactionRemoved(event)
                        }
                    }

                    NoticeType.group_name_change -> {
                        val event = message.fromJson<RawGroupNameChangeEvent>()
                        event.action = botInstance.action
                        if (event.groupId !in botInstance.listenedGroups &&
                            botInstance.listenedGroups.isNotEmpty() &&
                            botInstance.enableEventListenerFilter
                        ) return
                        botInstance.dispatchEvent(GroupNameChangedEvent(botInstance.action, event))
                        listener.onGroupNameChanged(event)
                    }

                    NoticeType.bot_offline -> {
                        val event = message.fromJson<RawBotOfflineEvent>()
                        event.action = botInstance.action
                        botInstance.dispatchEvent(BotOfflineEvent(botInstance.action, event))
                        listener.onBotOffline(event)
                    }

                    NoticeType.bot_online -> {
                        val event = message.fromJson<RawBotOnlineEvent>()
                        event.action = botInstance.action
                        botInstance.dispatchEvent(BotOnlineEvent(botInstance.action, event))
                        listener.onBotOnline(event)
                    }

                    null -> {}
                }
                serializedMessage.subType?.let {
                    when (serializedMessage.subType) {
                        SubType.kick -> {
                            val event =
                                RawMemberKickEvent(msg.groupId!!, msg.operatorId, time, msg.userId, botInstance.action)
                            if (event.groupId !in botInstance.listenedGroups &&
                                botInstance.listenedGroups.isNotEmpty() &&
                                botInstance.enableEventListenerFilter
                            ) return
                            botInstance.dispatchEvent(MemberKickEvent(botInstance.action, event))
                            listener.onMemberKick(event)
                        }

                        SubType.kick_me -> {
                            val event =
                                RawBotBeKickEvent(msg.groupId!!, msg.operatorId, time, msg.userId, botInstance.action)
                            botInstance.dispatchEvent(BotBeKickEvent(botInstance.action, event))
                            listener.onBeKicked(event)
                        }

                        SubType.unset -> {
                            val event = RawUnsetOperatorEvent(
                                msg.groupId!!,
                                msg.operatorId,
                                time,
                                msg.userId,
                                botInstance.action
                            )
                            if (event.groupId !in botInstance.listenedGroups &&
                                botInstance.listenedGroups.isNotEmpty() &&
                                botInstance.enableEventListenerFilter
                            ) return
                            botInstance.dispatchEvent(UnsetOperatorEvent(botInstance.action, event))
                            listener.onUnsetOperator(event)
                        }

                        SubType.set -> {
                            val event =
                                RawSetOperatorEvent(msg.groupId!!, msg.operatorId, time, msg.userId, botInstance.action)
                            if (event.groupId !in botInstance.listenedGroups &&
                                botInstance.listenedGroups.isNotEmpty() &&
                                botInstance.enableEventListenerFilter
                            ) return
                            listener.onSetOperator(event)
                        }

                        SubType.ban -> {
                            val event =
                                RawBanEvent(
                                    msg.groupId!!,
                                    msg.operatorId,
                                    msg.duration!!,
                                    time,
                                    msg.userId,
                                    botInstance.action
                                )
                            if (event.groupId !in botInstance.listenedGroups &&
                                botInstance.listenedGroups.isNotEmpty() &&
                                botInstance.enableEventListenerFilter
                            ) return
                            botInstance.dispatchEvent(BanEvent(botInstance.action, event))
                            listener.onBan(event)
                        }

                        SubType.lift_ban -> {
                            val event =
                                RawPardonBanEvent(
                                    msg.groupId!!, msg.operatorId, msg.duration!!,
                                    time, msg.userId, botInstance.action
                                )
                            if (event.groupId !in botInstance.listenedGroups &&
                                botInstance.listenedGroups.isNotEmpty() &&
                                botInstance.enableEventListenerFilter
                            ) return
                            botInstance.dispatchEvent(PardonBanEvent(botInstance.action, event))
                            listener.onPardon(event)
                        }

                        SubType.leave -> {
                            val event =
                                RawGroupMemberLeaveEvent(
                                    msg.groupId!!,
                                    msg.userId,
                                    msg.operatorId,
                                    time,
                                    botInstance.action
                                )
                            if (event.groupId !in botInstance.listenedGroups &&
                                botInstance.listenedGroups.isNotEmpty() &&
                                botInstance.enableEventListenerFilter
                            ) return
                            botInstance.dispatchEvent(GroupMemberLeaveEvent(botInstance.action, event))
                            listener.onLeaveEvent(event)
                        }

                        SubType.invite -> {
                            val event = RawMemberBeInviteEvent(
                                msg.groupId!!,
                                msg.userId,
                                msg.operatorId,
                                time,
                            ).apply {
                                action = botInstance.action
                            }
                            if (event.groupId !in botInstance.listenedGroups &&
                                botInstance.listenedGroups.isNotEmpty() &&
                                botInstance.enableEventListenerFilter
                            ) return
                            botInstance.dispatchEvent(GroupBeInviteEvent(botInstance.action, event))
                            listener.onBeInviteEvent(event)
                        }

                        SubType.approve -> {
                            val event =
                                RawJoinRequestApproveEvent(
                                    msg.groupId!!,
                                    msg.userId,
                                    msg.operatorId,
                                    time,
                                    botInstance.action
                                )
                            if (event.groupId !in botInstance.listenedGroups &&
                                botInstance.listenedGroups.isNotEmpty() &&
                                botInstance.enableEventListenerFilter
                            ) return
                            botInstance.dispatchEvent(GroupMemberApproveEvent(botInstance.action, event))
                            listener.onApproveEvent(event)
                        }

                        SubType.poke -> {
                            val poke = message.fromJson<RawPokeEvent>()
                            val selfUserId = botInstance.action.getLoginInfo().userId
                            poke.action = botInstance.action
                            if (poke.groupId != null) {
                                if (poke.groupId !in botInstance.listenedGroups &&
                                    botInstance.listenedGroups.isNotEmpty() &&
                                    botInstance.enableEventListenerFilter
                                ) return
                                botInstance.dispatchEvent(GroupPokeEvent(botInstance.action, poke))
                                listener.onGroupPoke(poke)
                                if (poke.targetId == selfUserId) {
                                    botInstance.dispatchEvent(GroupPokeSelfEvent(botInstance.action, poke))
                                    listener.onGroupPokeSelf(poke)
                                }
                            } else {
                                botInstance.dispatchEvent(PrivatePokeEvent(botInstance.action, poke))
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

    suspend fun onOpen(listener: OneBotListener, address: String) {
        println("New connection: $address")
        botInstance.dispatchEvent(WebsocketConnectedEvent(botInstance.action))
        listener.onWebsocketOpenEvent(botInstance.action)
    }

    suspend fun onClose(listener: OneBotListener, address: String) {
        println("Websocket connection closed($address})")
        val event = RawWebsocketCloseEvent(botInstance.action)
        botInstance.dispatchEvent(WebsocketCloseEvent(botInstance.action, event))
        listener.onWebsocketClosedEvent(event)
    }

    suspend fun onStart(listener: OneBotListener, port: Int) {
        println("Websocket server started on $port")
        botInstance.dispatchEvent(WebsocketServerStartedEvent(botInstance.action, port))
        listener.onWebsocketServerStartedEvent(botInstance.action)
    }

    suspend fun onError(listener: OneBotListener, ex: Exception) {
        println("Websocket connection error: ${ex.message}")
        ex.printStackTrace()
        val event = RawWebsocketErrorEvent(botInstance.action, ex)
        botInstance.dispatchEvent(WebsocketErrorEvent(botInstance.action, event))
        listener.onWebsocketErrorEvent(event)
    }
}