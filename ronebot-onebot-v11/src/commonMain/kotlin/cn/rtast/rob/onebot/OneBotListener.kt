/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/26
 */


package cn.rtast.rob.onebot

import cn.rtast.rob.annotations.JvmOnly
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
import cn.rtast.rob.event.raw.request.JoinGroupRequestEvent
import kotlin.jvm.JvmSynthetic

/**
 * 这里所有的方法名后如果没有Jvm字样则表示是
 * 为Kotlin使用者使用, 有Jvm字样表示为Java使用者使用
 */
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
     * 当收到群聊消息时触发此事件
     * 已弃用
     */
    @Deprecated(
        "该接口已弃用, 请使用onGroupMessage(message: GroupMessage)",
        replaceWith = ReplaceWith("onGroupMessage(message: GroupMessage)"),
    )
    @JvmSynthetic
    public suspend fun onGroupMessage(message: GroupMessage, json: String) {
    }

    /**
     * 当收到私聊消息时触发此事件
     */
    @JvmSynthetic
    public suspend fun onPrivateMessage(message: PrivateMessage) {
    }

    /**
     * 当收到私聊消息时触发此事件
     * 已弃用
     */
    @Deprecated(
        "该接口已弃用, onPrivateMessage(message: PrivateMessage)",
        replaceWith = ReplaceWith("onPrivateMessage(message: PrivateMessage)")
    )
    @JvmSynthetic
    public suspend fun onPrivateMessage(message: PrivateMessage, json: String) {
    }

    /**
     * 当机器人账号被邀请加群时触发此事件
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

    /**
     * 消息执行超时
     * JvmOnly
     */
    @JvmOnly
    public fun onMessageTimeoutJvm(event: MessageTimeoutEvent) {
    }

    /**
     * 在Websocket连接出现异常时触发此事件
     * JvmOnly
     */
    @JvmOnly
    public fun onWebsocketErrorEventJvm(event: RawWebsocketErrorEvent) {
    }

    /**
     * 在Websocket连接打开时触发此事件
     * ***注意: 该事件每次打开Websocket连接都会被触发`包括重连`***
     * JvmOnly
     */
    @JvmOnly
    public fun onWebsocketOpenEventJvm(action: OneBotAction) {
    }

    /**
     * 当Websocket连接关闭时触发此事件
     * JvmOnly
     */
    @JvmOnly
    public fun onWebsocketClosedEventJvm(event: RawWebsocketCloseEvent) {
    }

    /**
     * 如果以Websocket服务器使用ROneBot时该事件才会生效
     * 并且在Websocket服务器启动时触发一次
     * ***仅会触发一次***
     * JvmOnly
     */
    @JvmOnly
    public fun onWebsocketServerStartedEventJvm(action: OneBotAction) {
    }

    /**
     * 在Websocket连接时触发此事件
     * JvmOnly
     */
    @JvmOnly
    public fun onConnectEventJvm(event: RawConnectEvent) {
    }

    /**
     * 接收到OneBot实现下发心跳包时触发此事件
     * JvmOnly
     */
    @JvmOnly
    public fun onHeartBeatEventJvm(event: RawHeartBeatEvent) {
    }

    /**
     * 接收到任何OneBot下发的数据包时触发此事件
     * [rawMessage]为未解析前的Json文本
     * JvmOnly
     */
    @JvmOnly
    public fun onRawMessageJvm(action: OneBotAction, rawMessage: String) {
    }

    /**
     * 在群聊消息被撤回时触发此事件
     * JvmOnly
     */
    @JvmOnly
    public fun onGroupMessageRevokeJvm(message: RawGroupRevokeMessage) {
    }

    /**
     * 在私聊中撤回消息时会触发此事件
     * JvmOnly
     */
    @JvmOnly
    public fun onPrivateMessageRevokeJvm(message: RawPrivateRevokeMessage) {
    }

    /**
     * 当收到群聊消息时触发此事件
     * JvmOnly
     */
    @JvmOnly
    public fun onGroupMessageJvm(message: GroupMessage) {
    }

    /**
     * 当收到私聊消息时触发此事件
     * JvmOnly
     */
    @JvmOnly
    public fun onPrivateMessageJvm(message: PrivateMessage) {
    }

    /**
     * 当机器人账号被邀请加群时触发此事件
     * JvmOnly
     */
    @JvmOnly
    public fun onBeInviteEventJvm(event: RawMemberBeInviteEvent) {
    }

    /**
     * 当加群请求被同意时触发此事件
     * JvmOnly
     */
    @JvmOnly
    public fun onApproveEventJvm(event: RawJoinRequestApproveEvent) {
    }

    /**
     * 在群员退出群聊时触发此事件
     * JvmOnly
     */
    @JvmOnly
    public fun onLeaveEventJvm(event: RawGroupMemberLeaveEvent) {
    }

    /**
     * 在成员被踢出群聊时触发此事件
     * JvmOnly
     */
    @JvmOnly
    public fun onMemberKickJvm(event: RawMemberKickEvent) {
    }

    /**
     * 在被群聊踢出时触发此事件
     * JvmOnly
     */
    @JvmOnly
    public fun onBeKickedJvm(event: RawBotBeKickEvent) {
    }

    /**
     * 在被设置为管理员时触发此事件
     * JvmOnly
     */
    @JvmOnly
    public fun onSetOperatorJvm(event: RawSetOperatorEvent) {
    }

    /**
     * 在被取消管理员权限时触发此事件
     * JvmOnly
     */
    @JvmOnly
    public fun onUnsetOperatorJvm(event: RawUnsetOperatorEvent) {
    }

    /**
     * 在被禁言时触发此事件
     * JvmOnly
     */
    @JvmOnly
    public fun onBanJvm(event: RawBanEvent) {
    }

    /**
     * 在解除禁言时触发此事件
     * JvmOnly
     */
    @JvmOnly
    public fun onPardonJvm(event: RawPardonBanEvent) {
    }

    /**
     * 收到加群请求时触发此事件, 但是仅限管理员账号
     * JvmOnly
     */
    @JvmOnly
    public fun onJoinRequestJvm(event: JoinGroupRequestEvent) {
    }

    /**
     * 收到添加好友请求时触发此事件
     * JvmOnly
     */
    @JvmOnly
    public fun onAddFriendRequestJvm(event: AddFriendRequestEvent) {
    }

    /**
     * 当有人上传文件到群文件时触发此事件
     * JvmOnly
     */
    @JvmOnly
    public fun onGroupFileUploadJvm(event: RawFileEvent) {
    }

    /**
     * 收到私聊发送文件时触发此事件
     * JvmOnly
     */
    @JvmOnly
    public fun onPrivateFileUploadJvm(event: RawFileEvent) {
    }

    /**
     * 在群聊戳一戳时会触发此事件
     * JvmOnly
     */
    @JvmOnly
    public fun onGroupPokeJvm(event: RawPokeEvent) {
    }

    /**
     * 在私聊戳一戳时会触发此事件
     * JvmOnly
     */
    @JvmOnly
    public fun onPrivatePokeJvm(event: RawPokeEvent) {
    }

    /**
     * 当群内发生了`Reaction`(回应) 事件时触发此事件
     * ***注意: 此事件只会发生在群聊中***
     * JvmOnly
     */
    @JvmOnly
    public fun onReactionJvm(event: ReactionEvent) {
    }

    /**
     * 当一个reaction的表情被移除时触发此事件
     * JvmOnly
     */
    @JvmOnly
    public fun onReactionRemovedJvm(event: ReactionEvent) {
    }

    /**
     * 当群名称更之后触发的接口
     * JvmOnly
     */
    @JvmOnly
    public fun onGroupNameChangedJvm(event: RawGroupNameChangeEvent) {
    }

    /**
     * Bot账号下线时触发
     * JvmOnly
     */
    @JvmOnly
    public fun onBotOfflineJvm(event: RawBotOfflineEvent) {
    }

    /**
     * Bot账号重新上线时触发
     * JvmOnly
     */
    @JvmOnly
    public fun onBotOnlineJvm(event: RawBotOnlineEvent) {
    }

    /**
     * Bot在群聊中被戳一戳触发此事件
     * JvmOnly
     */
    @JvmOnly
    public fun onGroupPokeSelfJvm(event: RawPokeEvent) {
    }

    /**
     * Bot在私聊中被戳一戳触发此事件
     * JvmOnly
     */
    @JvmOnly
    public fun onPrivatePokeSelfJvm(event: RawPokeEvent) {
    }
}