/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/26
 */


package cn.rtast.rob.onebot

import cn.rtast.rob.entity.*
import cn.rtast.rob.entity.custom.*
import cn.rtast.rob.entity.lagrange.FileEvent
import cn.rtast.rob.entity.lagrange.PokeEvent
import cn.rtast.rob.entity.metadata.event.ConnectEvent
import cn.rtast.rob.entity.metadata.event.GroupNameChange
import cn.rtast.rob.entity.metadata.event.HeartBeatEvent

public interface OneBotListener {
    /**
     * 在Websocket连接出现异常时触发此事件
     */
    public suspend fun onWebsocketErrorEvent(event: IWebsocketErrorEvent) {}

    /**
     * 在Websocket连接打开时触发此事件
     * ***注意: 该事件每次打开Websocket连接都会被触发`包括重连`***
     */
    public suspend fun onWebsocketOpenEvent(action: OneBotAction) {}

    /**
     * 当Websocket连接关闭时触发此事件
     */
    public suspend fun onWebsocketCloseEvent(event: IWebsocketCloseEvent) {}

    /**
     * 如果以Websocket服务器使用ROneBot时该事件才会生效
     * 并且在Websocket服务器启动时触发一次
     * ***仅会触发一次***
     */
    public suspend fun onWebsocketServerStartEvent(action: OneBotAction) {}

    /**
     * 在Websocket连接时触发此事件
     */
    public suspend fun onConnectEvent(event: ConnectEvent) {}

    /**
     * 接收到OneBot实现下发心跳包时触发此事件
     */
    public suspend fun onHeartBeatEvent(event: HeartBeatEvent) {}

    /**
     * 接收到任何OneBot下发的数据包时触发此事件
     * [rawMessage]为未解析前的Json文本
     */
    public suspend fun onRawMessage(action: OneBotAction, rawMessage: String) {}

    /**
     * 在群聊消息被撤回时触发此事件
     */
    public suspend fun onGroupMessageRevoke(message: GroupRevokeMessage) {}

    /**
     * 在私聊中撤回消息时会触发此事件
     */
    public suspend fun onPrivateMessageRevoke(message: PrivateRevokeMessage) {}

    /**
     * 当收到群聊消息时触发此事件
     */
    public suspend fun onGroupMessage(message: GroupMessage, json: String) {}

    /**
     * 当收到私聊消息时触发此事件
     */
    public suspend fun onPrivateMessage(message: PrivateMessage, json: String) {}

    /**
     * 当机器人账号被邀请加群时触发此事件
     */
    public suspend fun onBeInviteEvent(event: IMemberBeInviteEvent) {}

    /**
     * 当加群请求被同意时触发此事件
     */
    public suspend fun onApproveEvent(event: IJoinRequestApproveEvent) {}

    /**
     * 在群员退出群聊时触发此事件
     */
    public suspend fun onLeaveEvent(event: IGroupMemberLeaveEvent) {}

    /**
     * 在成员被踢出群聊时触发此事件
     */
    public suspend fun onMemberKick(event: IMemberKickEvent) {}

    /**
     * 在被群聊踢出时触发此事件
     */
    public suspend fun onBeKicked(event: IBotBeKickEvent) {}

    /**
     * 在被设置为管理员时触发此事件
     */
    public suspend fun onSetOperator(event: ISetOperatorEvent) {}

    /**
     * 在被取消管理员权限时触发此事件
     */
    public suspend fun onUnsetOperator(event: IUnsetOperatorEvent) {}

    /**
     * 在被禁言时触发此事件
     */
    public suspend fun onBan(event: IBanEvent) {}

    /**
     * 在解除禁言时触发此事件
     */
    public suspend fun onPardon(event: IPardonBanEvent) {}

    /**
     * 收到加群请求时触发此事件, 但是仅限管理员账号
     */
    public suspend fun onJoinRequest(event: JoinGroupRequestEvent) {}

    /**
     * 收到添加好友请求时触发此事件
     */
    public suspend fun onAddFriendRequest(event: AddFriendRequestEvent) {}

    /**
     * 当有人上传文件到群文件时触发此事件
     */
    public suspend fun onGroupFileUpload(event: FileEvent) {}

    /**
     * 收到私聊发送文件时触发此事件
     */
    public suspend fun onPrivateFileUpload(event: FileEvent) {}

    /**
     * 在群聊戳一戳时会触发此事件
     */
    public suspend fun onGroupPoke(event: PokeEvent) {}

    /**
     * 在私聊戳一戳时会触发此事件
     */
    public suspend fun onPrivatePoke(event: PokeEvent) {}

    /**
     * 当群内发生了`Reaction`(回应) 事件时触发此事件
     * ***注意: 此事件只会发生在群聊中***
     */
    public suspend fun onReaction(event: ReactionEvent) {}

    /**
     * 当一个reaction的表情被移除时触发此事件
     */
    public suspend fun onReactionRemoved(event: ReactionEvent) {}

    /**
     * 当群名称更之后触发的接口
     */
    public suspend fun onGroupNameChanged(event: GroupNameChange) {}

    /**
     * Bot账号下线时触发
     */
    public suspend fun onBotOffline(event: IBotOfflineEvent) {}

    /**
     * Bot账号重新上线时触发
     */
    public suspend fun onBotOnline(event: IBotOnlineEvent) {}

    /**
     * Bot在群聊中被戳一戳触发此事件
     */
    public suspend fun onGroupPokeSelf(event: PokeEvent) {}

    /**
     * Bot在私聊中被戳一戳触发此事件
     */
    public suspend fun onPrivatePokeSelf(event: PokeEvent) {}
}