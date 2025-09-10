/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 9/11/25, 1:17 AM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */


package cn.rtast.rob.milky.milky

import cn.rtast.rob.milky.event.MilkyEvent
import cn.rtast.rob.milky.event.ws.packed.*
import cn.rtast.rob.milky.exceptions.UnknownEventTypeException

// internal registrar

internal inline fun <reified T : MilkyEvent> MilkyListener.registerHandler(
    noinline block: suspend (T) -> Unit,
) {
    val wrapper: suspend (MilkyEvent) -> Unit = { event ->
        if (event is T) block(event)
    }
    handlers.getOrPut(T::class) { wrapper }
}

internal suspend fun MilkyListener.dispatch(event: MilkyEvent) {
    handlers[event::class]?.invoke(event)
    when (event) {
        is WebsocketConnectedEvent -> onConnected(event)
        is WebsocketDisconnectedEvent -> onDisconnected(event)
        is RawMessageEvent -> onRawMessage(event)
        is MessageReceiveEvent -> onMessageReceive(event)
        is MessageRecallEvent -> onMessageRecall(event)
        is FriendRequestEvent -> onFriendRequest(event)
        is GroupJoinRequestEvent -> onGroupJoinRequest(event)
        is GroupInvitedJoinRequestEvent -> onGroupInvitedJoinRequest(event)
        is GroupInvitationRequestEvent -> onGroupInvitationRequest(event)
        is FriendNudgeEvent -> onFriendNudge(event)
        is FriendFileUploadEvent -> onFriendFileUpload(event)
        is GroupAdminChangeEvent -> onGroupAdminChange(event)
        is GroupEssenceMessageChangeEvent -> onGroupEssenceMessageChange(event)
        is GroupMemberIncreaseEvent -> onGroupMemberIncrease(event)
        is GroupMemberDecreaseEvent -> onGroupMemberDecrease(event)
        is GroupNameChangeEvent -> onGroupNameChange(event)
        is GroupMessageReactionEvent -> onGroupMessageReaction(event)
        is GroupMuteEvent -> onGroupMute(event)
        is GroupWholeMuteEvent -> onGroupWholeMute(event)
        is GroupNudgeEvent -> onGroupNudge(event)
        is GroupFileUploadEvent -> onGroupFileUpload(event)
        is GroupMessageEvent -> onGroupMessage(event)
        is PrivateMessageEvent -> onPrivateMessage(event)
        is BotOfflineEvent -> onBotOffline(event)
        else -> throw UnknownEventTypeException(event::class.qualifiedName ?: "未知类型")
    }
}

// dsl for kotlin user

/**
 * Websocket连接成功
 */
public fun MilkyListener.onConnected(block: suspend (WebsocketConnectedEvent) -> Unit): Unit =
    registerHandler(block)

/**
 * Websocket断开连接
 */
public fun MilkyListener.onDisconnected(block: suspend (WebsocketDisconnectedEvent) -> Unit): Unit =
    registerHandler(block)

/**
 * 原始消息
 */
public fun MilkyListener.onRawMessage(block: suspend (RawMessageEvent) -> Unit): Unit =
    registerHandler(block)

/**
 * 私聊、群聊、临时聊天的消息
 */
public fun MilkyListener.onMessageReceive(block: suspend (MessageReceiveEvent) -> Unit): Unit =
    registerHandler(block)

/**
 * 消息撤回
 */
public fun MilkyListener.onMessageRecall(block: suspend (MessageRecallEvent) -> Unit): Unit =
    registerHandler(block)

/**
 * 加好友请求
 */
public fun MilkyListener.onFriendRequest(block: suspend (FriendRequestEvent) -> Unit): Unit =
    registerHandler(block)

/**
 * 加群请求
 */
public fun MilkyListener.onGroupJoinRequest(block: suspend (GroupJoinRequestEvent) -> Unit): Unit =
    registerHandler(block)

/**
 * 加群邀请请求
 */
public fun MilkyListener.onGroupInvitedJoinRequest(block: suspend (GroupInvitedJoinRequestEvent) -> Unit): Unit =
    registerHandler(block)

/**
 * 邀请自己进群
 */
public fun MilkyListener.onGroupInvitationRequest(block: suspend (GroupInvitationRequestEvent) -> Unit): Unit =
    registerHandler(block)

/**
 * 好友戳一戳
 */
public fun MilkyListener.onFriendNudge(block: suspend (FriendNudgeEvent) -> Unit): Unit =
    registerHandler(block)

/**
 * 好友发送文件
 */
public fun MilkyListener.onFriendFileUpload(block: suspend (FriendFileUploadEvent) -> Unit): Unit =
    registerHandler(block)

/**
 * 群聊管理员变更
 */
public fun MilkyListener.onGroupAdminChange(block: suspend (GroupAdminChangeEvent) -> Unit): Unit =
    registerHandler(block)

/**
 * 群聊精华消息变更
 */
public fun MilkyListener.onGroupEssenceMessageChange(block: suspend (GroupEssenceMessageChangeEvent) -> Unit): Unit =
    registerHandler(block)

/**
 * 群聊成员增加
 */
public fun MilkyListener.onGroupMemberIncrease(block: suspend (GroupMemberIncreaseEvent) -> Unit): Unit =
    registerHandler(block)

/**
 * 群聊成员减少
 */
public fun MilkyListener.onGroupMemberDecrease(block: suspend (GroupMemberDecreaseEvent) -> Unit): Unit =
    registerHandler(block)

/**
 * 群名称变更
 */
public fun MilkyListener.onGroupNameChange(block: suspend (GroupNameChangeEvent) -> Unit): Unit =
    registerHandler(block)

/**
 * 群消息表情回应
 */
public fun MilkyListener.onGroupMessageReaction(block: suspend (GroupMessageReactionEvent) -> Unit): Unit =
    registerHandler(block)

/**
 * 群禁言
 */
public fun MilkyListener.onGroupMute(block: suspend (GroupMuteEvent) -> Unit): Unit =
    registerHandler(block)

/**
 * 全体禁言
 */
public fun MilkyListener.onGroupWholeMute(block: suspend (GroupWholeMuteEvent) -> Unit): Unit =
    registerHandler(block)

/**
 * 群戳一戳
 */
public fun MilkyListener.onGroupNudge(block: suspend (GroupNudgeEvent) -> Unit): Unit =
    registerHandler(block)

/**
 * 群聊文件上传
 */
public fun MilkyListener.onGroupFileUpload(block: suspend (GroupFileUploadEvent) -> Unit): Unit =
    registerHandler(block)

/**
 * 群聊消息
 */
public fun MilkyListener.onGroupMessage(block: suspend (GroupMessageEvent) -> Unit): Unit =
    registerHandler(block)

/**
 * 私聊消息
 */
public fun MilkyListener.onPrivateMessage(block: suspend (PrivateMessageEvent) -> Unit): Unit =
    registerHandler(block)

/**
 * Bot离线
 */
public fun MilkyListener.onBotOffline(block: suspend (BotOfflineEvent) -> Unit): Unit = registerHandler(block)