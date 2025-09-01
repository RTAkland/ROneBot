/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/18/25, 10:48 PM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

@file:OptIn(InternalROneBotApi::class)

package cn.rtast.rob.milky.util

import cn.rtast.rob.annotations.InternalROneBotApi
import cn.rtast.rob.event.dispatchEvent
import cn.rtast.rob.milky.BotInstance
import cn.rtast.rob.milky.enums.MessageScene
import cn.rtast.rob.milky.enums.internal.MilkyEvents
import cn.rtast.rob.milky.event.ws.WebsocketEventStruct
import cn.rtast.rob.milky.event.ws.packed.*
import cn.rtast.rob.milky.event.ws.raw.*
import cn.rtast.rob.util.fromJson

/**
 * 分发并解析milky下发的事件Json
 */
internal suspend fun BotInstance.handleDispatchEvent(message: String) {
    val eventType = message.fromJson<WebsocketEventStruct>().eventType
    when (eventType) {
        MilkyEvents.MessageReceive -> {
            val rawEvent = message.fromJson<RawMessageReceiveEvent>().data.apply {
                action = this@handleDispatchEvent.action
            }
            val event = MessageReceiveEvent(action, rawEvent)
            this@handleDispatchEvent.dispatchEvent(event)
            listener.onMessageReceive(event)
            listener.onMessageReceiveJvm(event)
            if (event.event.messageScene == MessageScene.Group) {
                val groupMessageEvent = GroupMessageEvent(action, rawEvent)
                this@handleDispatchEvent.dispatchEvent(groupMessageEvent)
                listener.onGroupMessageEvent(groupMessageEvent)
                listener.onGroupMessageEventJvm(groupMessageEvent)
            } else {
                val privateMessageEvent = PrivateMessageEvent(action, rawEvent, event.event.friend!!)
                this@handleDispatchEvent.dispatchEvent(privateMessageEvent)
                listener.onPrivateMessageEvent(privateMessageEvent)
                listener.onPrivateMessageEventJvm(privateMessageEvent)
            }
        }

        MilkyEvents.MessageRecall -> {
            val rawEvent = message.fromJson<RawMessageRecallEvent>().data.apply {
                action = this@handleDispatchEvent.action
            }
            val event = MessageRecallEvent(action, rawEvent)
            this@handleDispatchEvent.dispatchEvent(event)
            listener.onMessageRecall(event)
            listener.onMessageRecallJvm(event)
        }

        MilkyEvents.FriendRequest -> {
            val rawEvent = message.fromJson<RawFriendRequestEvent>().data.apply {
                action = this@handleDispatchEvent.action
            }
            val event = FriendRequestEvent(action, rawEvent)
            this@handleDispatchEvent.dispatchEvent(event)
            listener.onFriendRequest(event)
            listener.onFriendRequestJvm(event)
        }

        MilkyEvents.GroupJoinRequest -> {
            val rawEvent = message.fromJson<RawGroupJoinRequestEvent>().data.apply {
                action = this@handleDispatchEvent.action
            }
            val event = GroupJoinRequestEvent(action, rawEvent)
            this@handleDispatchEvent.dispatchEvent(event)
            listener.onGroupJoinRequestEvent(event)
            listener.onGroupJoinRequestEventJvm(event)
        }

        MilkyEvents.GroupInvitedJoinRequest -> {
            val rawEvent = message.fromJson<RawGroupInvitedJoinRequestEvent>().data.apply {
                action = this@handleDispatchEvent.action
            }
            val event = GroupInvitedJoinRequestEvent(action, rawEvent)
            this@handleDispatchEvent.dispatchEvent(event)
            listener.onGroupInvitedJoinRequestEvent(event)
            listener.onGroupInvitedJoinRequestEventJvm(event)
        }

        MilkyEvents.GroupInvitation -> {
            val rawEvent = message.fromJson<RawGroupInvitationRequestEvent>().data.apply {
                action = this@handleDispatchEvent.action
            }
            val event = GroupInvitationRequestEvent(action, rawEvent)
            this@handleDispatchEvent.dispatchEvent(event)
            listener.onGroupInvitationRequestEvent(event)
            listener.onGroupInvitationRequestEventJvm(event)
        }

        MilkyEvents.FriendPoke -> {
            val rawEvent = message.fromJson<RawFriendNudgeEvent>().data.apply {
                action = this@handleDispatchEvent.action
            }
            val event = FriendNudgeEvent(action, rawEvent)
            this@handleDispatchEvent.dispatchEvent(event)
            listener.onFriendNudgeEvent(event)
            listener.onFriendNudgeEventJvm(event)
        }

        MilkyEvents.FriendFileUpload -> {
            val rawEvent = message.fromJson<RawFriendFileUploadEvent>().data.apply {
                action = this@handleDispatchEvent.action
            }
            val event = FriendFileUploadEvent(action, rawEvent)
            this@handleDispatchEvent.dispatchEvent(event)
            listener.onFriendFileUploadEvent(event)
            listener.onFriendFileUploadEventJvm(event)
        }

        MilkyEvents.GroupAdminChange -> {
            val rawEvent = message.fromJson<RawGroupAdminChangeEvent>().data.apply {
                action = this@handleDispatchEvent.action
            }
            val event = GroupAdminChangeEvent(action, rawEvent)
            this@handleDispatchEvent.dispatchEvent(event)
            listener.onGroupAdminChangeEvent(event)
            listener.onGroupAdminChangeEventJvm(event)
        }

        MilkyEvents.GroupEssenceMessageChange -> {
            val rawEvent = message.fromJson<RawGroupEssenceMessageChangeEvent>().data.apply {
                action = this@handleDispatchEvent.action
            }
            val event = GroupEssenceMessageChangeEvent(action, rawEvent)
            this@handleDispatchEvent.dispatchEvent(event)
            listener.onGroupEssenceMessageChangeEvent(event)
            listener.onGroupEssenceMessageChangeEventJvm(event)
        }

        MilkyEvents.GroupMemberIncrease -> {
            val rawEvent = message.fromJson<RawGroupMemberIncreaseEvent>().data.apply {
                action = this@handleDispatchEvent.action
            }
            val event = GroupMemberIncreaseEvent(action, rawEvent)
            this@handleDispatchEvent.dispatchEvent(event)
            listener.onGroupMemberIncreaseEvent(event)
            listener.onGroupMemberIncreaseEventJvm(event)
        }

        MilkyEvents.GroupMemberDecrease -> {
            val rawEvent = message.fromJson<RawGroupMemberDecreaseEvent>().data.apply {
                action = this@handleDispatchEvent.action
            }
            val event = GroupMemberDecreaseEvent(action, rawEvent)
            this@handleDispatchEvent.dispatchEvent(event)
            listener.onGroupMemberDecreaseEvent(event)
            listener.onGroupMemberDecreaseEventJvm(event)
        }

        MilkyEvents.GroupNameChange -> {
            val rawEvent = message.fromJson<RawGroupNameChangeEvent>().data.apply {
                action = this@handleDispatchEvent.action
            }
            val event = GroupNameChangeEvent(action, rawEvent)
            this@handleDispatchEvent.dispatchEvent(event)
            listener.onGroupNameChangeEvent(event)
            listener.onGroupNameChangeEventJvm(event)
        }

        MilkyEvents.GroupMessageReaction -> {
            val rawEvent = message.fromJson<RawGroupMessageReactionEvent>().data.apply {
                action = this@handleDispatchEvent.action
            }
            val event = GroupMessageReactionEvent(action, rawEvent)
            this@handleDispatchEvent.dispatchEvent(event)
            listener.onGroupMessageReactionEvent(event)
            listener.onGroupMessageReactionEventJvm(event)
        }

        MilkyEvents.GroupMute -> {
            val rawEvent = message.fromJson<RawGroupMuteEvent>().data.apply {
                action = this@handleDispatchEvent.action
            }
            val event = GroupMuteEvent(action, rawEvent)
            this@handleDispatchEvent.dispatchEvent(event)
            listener.onGroupMuteEvent(event)
            listener.onGroupMuteEventJvm(event)
        }

        MilkyEvents.GroupWholeMute -> {
            val rawEvent = message.fromJson<RawGroupWholeMuteEvent>().data.apply {
                action = this@handleDispatchEvent.action
            }
            val event = GroupWholeMuteEvent(action, rawEvent)
            this@handleDispatchEvent.dispatchEvent(event)
            listener.onGroupWholeMuteEvent(event)
            listener.onGroupWholeMuteEventJvm(event)
        }

        MilkyEvents.GroupNudge -> {
            val rawEvent = message.fromJson<RawGroupNudgeEvent>().data.apply {
                action = this@handleDispatchEvent.action
            }
            val event = GroupNudgeEvent(action, rawEvent)
            this@handleDispatchEvent.dispatchEvent(event)
            listener.onGroupNudgeEvent(event)
            listener.onGroupNudgeEventJvm(event)
        }

        MilkyEvents.GroupFileUpload -> {
            val rawEvent = message.fromJson<RawGroupFileUploadEvent>().data.apply {
                action = this@handleDispatchEvent.action
            }
            val event = GroupFileUploadEvent(action, rawEvent)
            this@handleDispatchEvent.dispatchEvent(event)
            listener.onGroupFileUploadEvent(event)
            listener.onGroupFileUploadEventJvm(event)
        }

        MilkyEvents.BotOffline -> {
            val rawEvent = message.fromJson<RawBotOfflineEvent>().data.apply {
                action = this@handleDispatchEvent.action
            }
            val event = BotOfflineEvent(action, rawEvent)
            this@handleDispatchEvent.dispatchEvent(event)
            listener.onBotOfflineEvent(event)
            listener.onBotOfflineEventJvm(event)
        }
    }
}