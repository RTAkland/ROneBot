/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/26
 */


package cn.rtast.rob.util.ob

import cn.rtast.rob.entity.*
import cn.rtast.rob.entity.custom.ApproveEvent
import cn.rtast.rob.entity.custom.BanEvent
import cn.rtast.rob.entity.custom.BeInviteEvent
import cn.rtast.rob.entity.custom.BeKickEvent
import cn.rtast.rob.entity.custom.BotOfflineEvent
import cn.rtast.rob.entity.custom.BotOnlineEvent
import cn.rtast.rob.entity.custom.CloseEvent
import cn.rtast.rob.entity.custom.ErrorEvent
import cn.rtast.rob.entity.custom.MemberKickEvent
import cn.rtast.rob.entity.custom.MemberLeaveEvent
import cn.rtast.rob.entity.custom.PardonEvent
import cn.rtast.rob.entity.custom.SetOperatorEvent
import cn.rtast.rob.entity.custom.UnsetOperatorEvent
import cn.rtast.rob.entity.lagrange.FileEvent
import cn.rtast.rob.entity.lagrange.PokeEvent
import cn.rtast.rob.entity.metadata.ConnectEvent
import cn.rtast.rob.entity.metadata.GroupNameChangeEvent
import cn.rtast.rob.entity.metadata.HeartBeatEvent

interface OneBotListener {
    /**
     * 在Websocket连接出现异常时触发此事件
     */
    suspend fun onWebsocketErrorEvent(event: ErrorEvent) {}

    /**
     * 在Websocket连接打开时触发此事件
     * ***注意: 该事件每次打开Websocket连接都会被触发`包括重连`***
     */
    suspend fun onWebsocketOpenEvent(action: OneBotAction) {}

    /**
     * 当Websocket连接关闭时触发此事件
     */
    suspend fun onWebsocketCloseEvent(event: CloseEvent) {}

    /**
     * 如果以Websocket服务器使用ROneBot时该事件才会生效
     * 并且在Websocket服务器启动时触发一次
     * ***仅会触发一次***
     */
    suspend fun onWebsocketServerStartEvent(action: OneBotAction) {}

    /**
     * 在Websocket连接时触发此事件
     */
    suspend fun onConnectEvent(event: ConnectEvent) {}

    /**
     * 接收到OneBot实现下发心跳包时触发此事件
     */
    suspend fun onHeartBeatEvent(event: HeartBeatEvent) {}

    /**
     * 接收到任何OneBot下发的数据包时触发此事件
     * [rawMessage]为未解析前的Json文本
     */
    suspend fun onMessage(action: OneBotAction, rawMessage: String) {}

    /**
     * 在群聊消息被撤回时触发此事件
     */
    suspend fun onGroupMessageRevoke(message: GroupRevokeMessage) {}

    /**
     * 在私聊中撤回消息时会触发此事件
     */
    suspend fun onPrivateMessageRevoke(message: PrivateRevokeMessage) {}

    /**
     * 当被At时触发此事件
     */
    suspend fun onBeAt(message: GroupMessage) {}

    /**
     * 当在群聊中被回复触发此事件
     */
    suspend fun onBeRepliedInGroup(message: GroupMessage) {}

    /**
     * 当在私聊中被回复触发此事件
     */
    suspend fun onBeRepliedInPrivate(message: PrivateMessage) {}

    /**
     * 当收到群聊消息时触发此事件
     */
    suspend fun onGroupMessage(message: GroupMessage, json: String) {}

    /**
     * 当收到私聊消息时触发此事件
     */
    suspend fun onPrivateMessage(message: PrivateMessage, json: String) {}

    /**
     * 当机器人账号被邀请加群时触发此事件
     */
    suspend fun onBeInviteEvent(event: BeInviteEvent) {}

    /**
     * 当加群请求被同意时触发此事件
     */
    suspend fun onApproveEvent(event: ApproveEvent) {}

    /**
     * 在群员退出群聊时触发此事件
     */
    suspend fun onLeaveEvent(event: MemberLeaveEvent) {}

    /**
     * 在成员被踢出群聊时触发此事件
     */
    suspend fun onMemberKick(event: MemberKickEvent) {}

    /**
     * 在被群聊踢出时触发此事件
     */
    suspend fun onBeKicked(event: BeKickEvent) {}

    /**
     * 在被设置为管理员时触发此事件
     */
    suspend fun onSetOperator(event: SetOperatorEvent) {}

    /**
     * 在被取消管理员权限时触发此事件
     */
    suspend fun onUnsetOperator(event: UnsetOperatorEvent) {}

    /**
     * 在被禁言时触发此事件
     */
    suspend fun onBan(event: BanEvent) {}

    /**
     * 在解除禁言时触发此事件
     */
    suspend fun onPardon(event: PardonEvent) {}

    /**
     * 收到加群请求时触发此事件, 但是仅限管理员账号
     */
    suspend fun onJoinRequest(event: JoinGroupRequestEvent) {}

    /**
     * 收到添加好友请求时触发此事件
     */
    suspend fun onAddFriendRequest(event: AddFriendRequestEvent) {}

    /**
     * 当有人上传文件到群文件时触发此事件
     */
    suspend fun onGroupFileUpload(event: FileEvent) {}

    /**
     * 收到私聊发送文件时触发此事件
     */
    suspend fun onPrivateFileUpload(event: FileEvent) {}

    /**
     * 在群聊戳一戳时会触发此事件
     */
    suspend fun onGroupPoke(event: PokeEvent) {}

    /**
     * 在私聊戳一戳时会触发此事件
     */
    suspend fun onPrivatePoke(event: PokeEvent) {}

    /**
     * 当群内发生了`Reaction`(回应) 事件时触发此事件
     * ***注意: 此事件只会发生在群聊中***
     */
    suspend fun onReaction(event: ReactionEvent) {}

    /**
     * 当一个reaction的表情被移除时触发此事件
     */
    suspend fun onReactionRemoved(event: ReactionEvent) {}

    /**
     * 当群名称更之后触发的接口
     */
    suspend fun onGroupNameChanged(event: GroupNameChangeEvent) {}

    /**
     * Bot账号下线时触发
     */
    suspend fun onBotOffline(event: BotOfflineEvent) {}

    /**
     * Bot账号重新上线时触发
     */
    suspend fun onBotOnline(event: BotOnlineEvent) {}
}