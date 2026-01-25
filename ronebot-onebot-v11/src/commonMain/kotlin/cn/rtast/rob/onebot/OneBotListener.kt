/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/26
 */


package cn.rtast.rob.onebot

import cn.rtast.rob.event.packed.MessageTimeoutEvent
import cn.rtast.rob.event.raw.file.RawFileEvent
import cn.rtast.rob.event.raw.group.*
import cn.rtast.rob.event.raw.internal.RawWebsocketCloseEvent
import cn.rtast.rob.event.raw.internal.RawWebsocketErrorEvent
import cn.rtast.rob.event.raw.message.GroupMessage
import cn.rtast.rob.event.raw.message.PrivateMessage
import cn.rtast.rob.event.raw.message.RawGroupRevokeMessage
import cn.rtast.rob.event.raw.message.RawPrivateRevokeMessage
import cn.rtast.rob.event.raw.onebot.RawBotOfflineEvent
import cn.rtast.rob.event.raw.onebot.RawBotOnlineEvent
import cn.rtast.rob.event.raw.onebot.RawConnectEvent
import cn.rtast.rob.event.raw.onebot.RawHeartBeatEvent
import cn.rtast.rob.event.raw.request.AddFriendRequestEvent
import cn.rtast.rob.event.raw.request.BeInviteGroupRequestEvent
import cn.rtast.rob.event.raw.request.JoinGroupRequestEvent
import kotlin.jvm.JvmSynthetic

public interface OneBotListener {

    /**
     * 消息执行超时
     */
    @JvmSynthetic
    public suspend fun onMessageTimeout(event: MessageTimeoutEvent) {
    }

    /**
     * 在Websocket连接出现异常时触发此事件
     */
    @JvmSynthetic
    public suspend fun onWebsocketErrorEvent(event: RawWebsocketErrorEvent) {
    }

    /**
     * 在Websocket连接打开时触发此事件
     * ***注意: 该事件每次打开Websocket连接都会被触发`包括重连`***
     */
    @JvmSynthetic
    public suspend fun onWebsocketOpenEvent(action: OneBotAction) {
    }

    /**
     * 当Websocket连接关闭时触发此事件
     */
    @JvmSynthetic
    public suspend fun onWebsocketClosedEvent(event: RawWebsocketCloseEvent) {
    }

    /**
     * 如果以Websocket服务器使用ROneBot时该事件才会生效
     * 并且在Websocket服务器启动时触发一次
     * ***仅会触发一次***
     */
    @JvmSynthetic
    public suspend fun onWebsocketServerStartedEvent(action: OneBotAction) {
    }

    /**
     * 在Websocket连接时触发此事件
     */
    @JvmSynthetic
    public suspend fun onConnectEvent(event: RawConnectEvent) {
    }

    /**
     * 接收到OneBot实现下发心跳包时触发此事件
     */
    @JvmSynthetic
    public suspend fun onHeartBeatEvent(event: RawHeartBeatEvent) {
    }

    /**
     * 接收到任何OneBot下发的数据包时触发此事件
     * [rawMessage]为未解析前的Json文本
     */
    @JvmSynthetic
    public suspend fun onRawMessage(action: OneBotAction, rawMessage: String) {
    }

    /**
     * 在群聊消息被撤回时触发此事件
     */
    @JvmSynthetic
    public suspend fun onGroupMessageRevoke(message: RawGroupRevokeMessage) {
    }

    /**
     * 在私聊中撤回消息时会触发此事件
     */
    @JvmSynthetic
    public suspend fun onPrivateMessageRevoke(message: RawPrivateRevokeMessage) {
    }

    /**
     * 当收到群聊消息时触发此事件
     */
    @JvmSynthetic
    public suspend fun onGroupMessage(message: GroupMessage) {
    }

    /**
     * 当收到私聊消息时触发此事件
     */
    @JvmSynthetic
    public suspend fun onPrivateMessage(message: PrivateMessage) {
    }

    /**
     * 当任意账号被邀请加群时触发此事件
     */
    @JvmSynthetic
    public suspend fun onBeInviteEvent(event: RawMemberBeInviteEvent) {
    }

    /**
     * 当加群请求被同意时触发此事件
     */
    @JvmSynthetic
    public suspend fun onApproveEvent(event: RawJoinRequestApproveEvent) {
    }

    /**
     * 在群员退出群聊时触发此事件
     */
    @JvmSynthetic
    public suspend fun onLeaveEvent(event: RawGroupMemberLeaveEvent) {
    }

    /**
     * 在成员被踢出群聊时触发此事件
     */
    @JvmSynthetic
    public suspend fun onMemberKick(event: RawMemberKickEvent) {
    }

    /**
     * 在被群聊踢出时触发此事件
     */
    @JvmSynthetic
    public suspend fun onBeKicked(event: RawBotBeKickEvent) {
    }

    /**
     * 在被设置为管理员时触发此事件
     */
    @JvmSynthetic
    public suspend fun onSetOperator(event: RawSetOperatorEvent) {
    }

    /**
     * 在被取消管理员权限时触发此事件
     */
    @JvmSynthetic
    public suspend fun onUnsetOperator(event: RawUnsetOperatorEvent) {
    }

    /**
     * 在被禁言时触发此事件
     */
    @JvmSynthetic
    public suspend fun onBan(event: RawBanEvent) {
    }

    /**
     * 在解除禁言时触发此事件
     */
    @JvmSynthetic
    public suspend fun onPardon(event: RawPardonBanEvent) {
    }

    /**
     * 收到加群请求时触发此事件, 但是仅限管理员账号
     */
    @JvmSynthetic
    public suspend fun onJoinRequest(event: JoinGroupRequestEvent) {
    }

    /**
     * 收到被邀请进群请求时触发此事件
     */
    @JvmSynthetic
    public suspend fun onBeInviteRequest(event: BeInviteGroupRequestEvent) {
    }

    /**
     * 收到添加好友请求时触发此事件
     */
    @JvmSynthetic
    public suspend fun onAddFriendRequest(event: AddFriendRequestEvent) {
    }

    /**
     * 当有人上传文件到群文件时触发此事件
     */
    @JvmSynthetic
    public suspend fun onGroupFileUpload(event: RawFileEvent) {
    }

    /**
     * 收到私聊发送文件时触发此事件
     */
    @JvmSynthetic
    public suspend fun onPrivateFileUpload(event: RawFileEvent) {
    }

    /**
     * 在群聊戳一戳时会触发此事件
     */
    @JvmSynthetic
    public suspend fun onGroupPoke(event: RawPokeEvent) {
    }

    /**
     * 在私聊戳一戳时会触发此事件
     */
    @JvmSynthetic
    public suspend fun onPrivatePoke(event: RawPokeEvent) {
    }

    /**
     * 当群内发生了`Reaction`(回应) 事件时触发此事件
     * ***注意: 此事件只会发生在群聊中***
     */
    @JvmSynthetic
    public suspend fun onReaction(event: ReactionEvent) {
    }

    /**
     * 当一个reaction的表情被移除时触发此事件
     */
    @JvmSynthetic
    public suspend fun onReactionRemoved(event: ReactionEvent) {
    }

    /**
     * 当群名称更之后触发的接口
     */
    @JvmSynthetic
    public suspend fun onGroupNameChanged(event: RawGroupNameChangeEvent) {
    }

    /**
     * Bot账号下线时触发
     */
    @JvmSynthetic
    public suspend fun onBotOffline(event: RawBotOfflineEvent) {
    }

    /**
     * Bot账号重新上线时触发
     */
    @JvmSynthetic
    public suspend fun onBotOnline(event: RawBotOnlineEvent) {
    }

    /**
     * Bot在群聊中被戳一戳触发此事件
     */
    @JvmSynthetic
    public suspend fun onGroupPokeSelf(event: RawPokeEvent) {
    }

    /**
     * Bot在私聊中被戳一戳触发此事件
     */
    @JvmSynthetic
    public suspend fun onPrivatePokeSelf(event: RawPokeEvent) {
    }
}

public interface BlockingOneBotListener : OneBotListener {

    /**
     * 消息执行超时
     * 为Java使用者设计
     */
    public fun onMessageTimeoutBlocking(event: MessageTimeoutEvent) {}

    /**
     * 在Websocket连接出现异常时触发此事件
     * 为Java使用者设计
     */
    public fun onWebsocketErrorEventBlocking(event: RawWebsocketErrorEvent) {}

    /**
     * 在Websocket连接打开时触发此事件
     * ***注意: 该事件每次打开Websocket连接都会被触发`包括重连`***
     * 为Java使用者设计
     */
    public fun onWebsocketOpenEventBlocking(action: OneBotAction) {}

    /**
     * 当Websocket连接关闭时触发此事件
     * 为Java使用者设计
     */
    public fun onWebsocketClosedEventBlocking(event: RawWebsocketCloseEvent) {}

    /**
     * 如果以Websocket服务器使用ROneBot时该事件才会生效
     * 并且在Websocket服务器启动时触发一次
     * ***仅会触发一次***
     * 为Java使用者设计
     */
    public fun onWebsocketServerStartedEventBlocking(action: OneBotAction) {}

    /**
     * 在Websocket连接时触发此事件
     * 为Java使用者设计
     */
    public fun onConnectEventBlocking(event: RawConnectEvent) {}

    /**
     * 接收到OneBot实现下发心跳包时触发此事件
     * 为Java使用者设计
     */
    public fun onHeartBeatEventBlocking(event: RawHeartBeatEvent) {}

    /**
     * 接收到任何OneBot下发的数据包时触发此事件
     * [rawMessage]为未解析前的Json文本
     * 为Java使用者设计
     */
    public fun onRawMessageBlocking(action: OneBotAction, rawMessage: String) {}

    /**
     * 在群聊消息被撤回时触发此事件
     * 为Java使用者设计
     */
    public fun onGroupMessageRevokeBlocking(message: RawGroupRevokeMessage) {}

    /**
     * 在私聊中撤回消息时会触发此事件
     * 为Java使用者设计
     */
    public fun onPrivateMessageRevokeBlocking(message: RawPrivateRevokeMessage) {}

    /**
     * 当收到群聊消息时触发此事件
     * 为Java使用者设计
     */
    public fun onGroupMessageBlocking(message: GroupMessage) {}

    /**
     * 当收到私聊消息时触发此事件
     * 为Java使用者设计
     */
    public fun onPrivateMessageBlocking(message: PrivateMessage) {}

    /**
     * 当任意账号被邀请加群时触发此事件
     * 为Java使用者设计
     */
    public fun onBeInviteEventBlocking(event: RawMemberBeInviteEvent) {}

    /**
     * 当加群请求被同意时触发此事件
     * 为Java使用者设计
     */
    public fun onApproveEventBlocking(event: RawJoinRequestApproveEvent) {}

    /**
     * 在群员退出群聊时触发此事件
     * 为Java使用者设计
     */
    public fun onLeaveEventBlocking(event: RawGroupMemberLeaveEvent) {}

    /**
     * 在成员被踢出群聊时触发此事件
     * 为Java使用者设计
     */
    public fun onMemberKickBlocking(event: RawMemberKickEvent) {
    }

    /**
     * 在被群聊踢出时触发此事件
     * 为Java使用者设计
     */
    public fun onBeKickedBlocking(event: RawBotBeKickEvent) {}

    /**
     * 在被设置为管理员时触发此事件
     * 为Java使用者设计
     */
    public fun onSetOperatorBlocking(event: RawSetOperatorEvent) {}

    /**
     * 在被取消管理员权限时触发此事件
     * 为Java使用者设计
     */
    public fun onUnsetOperatorBlocking(event: RawUnsetOperatorEvent) {}

    /**
     * 在被禁言时触发此事件
     * 为Java使用者设计
     */
    public fun onBanBlocking(event: RawBanEvent) {}

    /**
     * 在解除禁言时触发此事件
     * 为Java使用者设计
     */
    public fun onPardonBlocking(event: RawPardonBanEvent) {}

    /**
     * 收到加群请求时触发此事件, 但是仅限管理员账号
     * 为Java使用者设计
     */
    public fun onJoinRequestBlocking(event: JoinGroupRequestEvent) {}

    /**
     * 收到被邀请进群请求时触发此事件
     * 为Java使用者设计
     */
    public fun onBeInviteRequestBlocking(event: BeInviteGroupRequestEvent) {}

    /**
     * 收到添加好友请求时触发此事件
     * 为Java使用者设计
     */
    public fun onAddFriendRequestBlocking(event: AddFriendRequestEvent) {}

    /**
     * 当有人上传文件到群文件时触发此事件
     * 为Java使用者设计
     */
    public fun onGroupFileUploadBlocking(event: RawFileEvent) {}

    /**
     * 收到私聊发送文件时触发此事件
     * 为Java使用者设计
     */
    public fun onPrivateFileUploadBlocking(event: RawFileEvent) {}

    /**
     * 在群聊戳一戳时会触发此事件
     * 为Java使用者设计
     */
    public fun onGroupPokeBlocking(event: RawPokeEvent) {}

    /**
     * 在私聊戳一戳时会触发此事件
     * 为Java使用者设计
     */
    public fun onPrivatePokeBlocking(event: RawPokeEvent) {}

    /**
     * 当群内发生了`Reaction`(回应) 事件时触发此事件
     * ***注意: 此事件只会发生在群聊中***
     * 为Java使用者设计
     */
    public fun onReactionBlocking(event: ReactionEvent) {}

    /**
     * 当一个reaction的表情被移除时触发此事件
     * 为Java使用者设计
     */
    public fun onReactionRemovedBlocking(event: ReactionEvent) {}

    /**
     * 当群名称更之后触发的接口
     * 为Java使用者设计
     */
    public fun onGroupNameChangedBlocking(event: RawGroupNameChangeEvent) {}

    /**
     * Bot账号下线时触发
     * 为Java使用者设计
     */
    public fun onBotOfflineBlocking(event: RawBotOfflineEvent) {}

    /**
     * Bot账号重新上线时触发
     * 为Java使用者设计
     */
    public fun onBotOnlineBlocking(event: RawBotOnlineEvent) {}

    /**
     * Bot在群聊中被戳一戳触发此事件
     * 为Java使用者设计
     */
    public fun onGroupPokeSelfBlocking(event: RawPokeEvent) {}

    /**
     * Bot在私聊中被戳一戳触发此事件
     * 为Java使用者设计
     */
    public fun onPrivatePokeSelfBlocking(event: RawPokeEvent) {}
}

internal suspend inline fun <T : OneBotListener> T.callEvent(
    suspendCall: suspend T.() -> Unit,
    blockingCall: BlockingOneBotListener.() -> Unit,
): Unit = if (this is BlockingOneBotListener) blockingCall() else suspendCall()