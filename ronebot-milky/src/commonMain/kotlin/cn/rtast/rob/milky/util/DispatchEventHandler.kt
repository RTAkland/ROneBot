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
import cn.rtast.rob.milky.MilkyBotFactory
import cn.rtast.rob.milky.enums.MessageScene
import cn.rtast.rob.milky.enums.internal.MilkyEvents
import cn.rtast.rob.milky.event.ws.WebsocketEventStruct
import cn.rtast.rob.milky.event.ws.packed.*
import cn.rtast.rob.milky.event.ws.raw.*
import cn.rtast.rob.milky.milky.dispatch
import cn.rtast.rob.util.fromJson

/**
 * 分发并解析milky下发的事件Json
 */
internal suspend fun BotInstance.handleDispatchEvent(message: String) {
    val eventType = message.fromJson<WebsocketEventStruct>().eventType
    when (eventType) {
        MilkyEvents.MessageReceive -> {
            val rawEvent = message.fromJson<RawMessageReceiveEvent>().apply {
                data.action = this@handleDispatchEvent.action
            }
            val event = MessageReceiveEvent(action, rawEvent.data)
            this@handleDispatchEvent.dispatchEvent(event)
            listener.dispatch(event)
            if (event.event.messageScene == MessageScene.Group) {
                if (this.listeningGroups.isNotEmpty() && event.event.group!!.groupId !in this.listeningGroups) return
                MilkyBotFactory.commandManager.handleCommand(rawEvent.data)
                val groupMessageEvent = GroupMessageEvent(action, rawEvent.data)
                this@handleDispatchEvent.dispatchEvent(groupMessageEvent)
                listener.dispatch(groupMessageEvent)
            } else {
                MilkyBotFactory.commandManager.handleCommand(rawEvent.data)
                val privateMessageEvent = PrivateMessageEvent(action, rawEvent.data, event.event.friend!!)
                this@handleDispatchEvent.dispatchEvent(privateMessageEvent)
                listener.dispatch(privateMessageEvent)
            }
        }

        MilkyEvents.MessageRecall -> {
            val rawEvent = message.fromJson<RawMessageRecallEvent>().data.apply {
                action = this@handleDispatchEvent.action
            }
            val event = MessageRecallEvent(action, rawEvent)
            this@handleDispatchEvent.dispatchEvent(event)
            listener.dispatch(event)
        }

        MilkyEvents.FriendRequest -> {
            val rawEvent = message.fromJson<RawFriendRequestEvent>().data.apply {
                action = this@handleDispatchEvent.action
            }
            val event = FriendRequestEvent(action, rawEvent)
            this@handleDispatchEvent.dispatchEvent(event)
            listener.dispatch(event)
        }

        MilkyEvents.GroupJoinRequest -> {
            val rawEvent = message.fromJson<RawGroupJoinRequestEvent>().data.apply {
                action = this@handleDispatchEvent.action
            }
            val event = GroupJoinRequestEvent(action, rawEvent)
            this@handleDispatchEvent.dispatchEvent(event)
            listener.dispatch(event)
        }

        MilkyEvents.GroupInvitedJoinRequest -> {
            val rawEvent = message.fromJson<RawGroupInvitedJoinRequestEvent>().data.apply {
                action = this@handleDispatchEvent.action
            }
            val event = GroupInvitedJoinRequestEvent(action, rawEvent)
            this@handleDispatchEvent.dispatchEvent(event)
            listener.dispatch(event)
        }

        MilkyEvents.GroupInvitation -> {
            val rawEvent = message.fromJson<RawGroupInvitationRequestEvent>().data.apply {
                action = this@handleDispatchEvent.action
            }
            val event = GroupInvitationRequestEvent(action, rawEvent)
            this@handleDispatchEvent.dispatchEvent(event)
            listener.dispatch(event)
        }

        MilkyEvents.FriendPoke -> {
            val rawEvent = message.fromJson<RawFriendNudgeEvent>().data.apply {
                action = this@handleDispatchEvent.action
            }
            val event = FriendNudgeEvent(action, rawEvent)
            this@handleDispatchEvent.dispatchEvent(event)
            listener.dispatch(event)
        }

        MilkyEvents.FriendFileUpload -> {
            val rawEvent = message.fromJson<RawFriendFileUploadEvent>().data.apply {
                action = this@handleDispatchEvent.action
            }
            val event = FriendFileUploadEvent(action, rawEvent)
            this@handleDispatchEvent.dispatchEvent(event)
            listener.dispatch(event)
        }

        MilkyEvents.GroupAdminChange -> {
            val rawEvent = message.fromJson<RawGroupAdminChangeEvent>().data.apply {
                action = this@handleDispatchEvent.action
            }
            val event = GroupAdminChangeEvent(action, rawEvent)
            this@handleDispatchEvent.dispatchEvent(event)
            listener.dispatch(event)
        }

        MilkyEvents.GroupEssenceMessageChange -> {
            val rawEvent = message.fromJson<RawGroupEssenceMessageChangeEvent>().data.apply {
                action = this@handleDispatchEvent.action
            }
            val event = GroupEssenceMessageChangeEvent(action, rawEvent)
            this@handleDispatchEvent.dispatchEvent(event)
            listener.dispatch(event)
        }

        MilkyEvents.GroupMemberIncrease -> {
            val rawEvent = message.fromJson<RawGroupMemberIncreaseEvent>().data.apply {
                action = this@handleDispatchEvent.action
            }
            val event = GroupMemberIncreaseEvent(action, rawEvent)
            this@handleDispatchEvent.dispatchEvent(event)
            listener.dispatch(event)
        }

        MilkyEvents.GroupMemberDecrease -> {
            val rawEvent = message.fromJson<RawGroupMemberDecreaseEvent>().data.apply {
                action = this@handleDispatchEvent.action
            }
            val event = GroupMemberDecreaseEvent(action, rawEvent)
            this@handleDispatchEvent.dispatchEvent(event)
            listener.dispatch(event)
        }

        MilkyEvents.GroupNameChange -> {
            val rawEvent = message.fromJson<RawGroupNameChangeEvent>().data.apply {
                action = this@handleDispatchEvent.action
            }
            val event = GroupNameChangeEvent(action, rawEvent)
            this@handleDispatchEvent.dispatchEvent(event)
            listener.dispatch(event)
        }

        MilkyEvents.GroupMessageReaction -> {
            val rawEvent = message.fromJson<RawGroupMessageReactionEvent>().data.apply {
                action = this@handleDispatchEvent.action
            }
            val event = GroupMessageReactionEvent(action, rawEvent)
            this@handleDispatchEvent.dispatchEvent(event)
            listener.dispatch(event)
        }

        MilkyEvents.GroupMute -> {
            val rawEvent = message.fromJson<RawGroupMuteEvent>().data.apply {
                action = this@handleDispatchEvent.action
            }
            val event = GroupMuteEvent(action, rawEvent)
            this@handleDispatchEvent.dispatchEvent(event)
            listener.dispatch(event)
        }

        MilkyEvents.GroupWholeMute -> {
            val rawEvent = message.fromJson<RawGroupWholeMuteEvent>().data.apply {
                action = this@handleDispatchEvent.action
            }
            val event = GroupWholeMuteEvent(action, rawEvent)
            this@handleDispatchEvent.dispatchEvent(event)
            listener.dispatch(event)
        }

        MilkyEvents.GroupNudge -> {
            val rawEvent = message.fromJson<RawGroupNudgeEvent>().data.apply {
                action = this@handleDispatchEvent.action
            }
            val event = GroupNudgeEvent(action, rawEvent)
            this@handleDispatchEvent.dispatchEvent(event)
            listener.dispatch(event)
        }

        MilkyEvents.GroupFileUpload -> {
            val rawEvent = message.fromJson<RawGroupFileUploadEvent>().data.apply {
                action = this@handleDispatchEvent.action
            }
            val event = GroupFileUploadEvent(action, rawEvent)
            this@handleDispatchEvent.dispatchEvent(event)
            listener.dispatch(event)
        }

        MilkyEvents.BotOffline -> {
            val rawEvent = message.fromJson<RawBotOfflineEvent>().data.apply {
                action = this@handleDispatchEvent.action
            }
            val event = BotOfflineEvent(action, rawEvent)
            this@handleDispatchEvent.dispatchEvent(event)
            listener.dispatch(event)
        }
    }
}