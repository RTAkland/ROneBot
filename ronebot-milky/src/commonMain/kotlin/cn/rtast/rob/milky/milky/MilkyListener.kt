/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/18/25, 10:45 PM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

@file:Suppress("unused")

package cn.rtast.rob.milky.milky

import cn.rtast.rob.milky.event.MilkyEvent
import cn.rtast.rob.milky.event.ws.packed.*
import kotlin.reflect.KClass

public open class MilkyListener {
    public val handlers: MutableMap<KClass<out MilkyEvent>, suspend (MilkyEvent) -> Unit> = mutableMapOf()

    /**
     * websocket连接上服务器时
     */
    public open fun onConnected(event: WebsocketConnectedEvent) {}

    /**
     * websocket连接从服务器断开时
     */
    public open fun onDisconnected(event: WebsocketDisconnectedEvent) {}

    /**
     * websocket服务器下发原始(raw)消息时
     */
    public open fun onRawMessage(event: RawMessageEvent) {}

    /**
     * 接收消息
     */
    public open fun onMessageReceive(event: MessageReceiveEvent) {}

    /**
     * 消息撤回
     */
    public open fun onMessageRecall(event: MessageRecallEvent) {}

    /**
     * 好友请求
     */
    public open fun onFriendRequest(event: FriendRequestEvent) {}

    /**
     * 加群请求
     */
    public open fun onGroupJoinRequest(event: GroupJoinRequestEvent) {}

    /**
     * 群聊邀请
     */
    public open fun onGroupInvitedJoinRequest(event: GroupInvitedJoinRequestEvent) {}

    /**
     * 邀请自己入群请求
     */
    public open fun onGroupInvitationRequest(event: GroupInvitationRequestEvent) {}

    /**
     * 好友戳一戳
     */
    public open fun onFriendNudge(event: FriendNudgeEvent) {}

    /**
     * 好友文件上传
     */
    public open fun onFriendFileUpload(event: FriendFileUploadEvent) {}

    /**
     * 群管理员变更
     */
    public open fun onGroupAdminChange(event: GroupAdminChangeEvent) {}

    /**
     * 群精华消息变更
     */
    public open fun onGroupEssenceMessageChange(event: GroupEssenceMessageChangeEvent) {}

    /**
     * 群成员增加
     */
    public open fun onGroupMemberIncrease(event: GroupMemberIncreaseEvent) {}

    /**
     * 群成员减少
     */
    public open fun onGroupMemberDecrease(event: GroupMemberDecreaseEvent) {}

    /**
     * 群名称变更
     */
    public open fun onGroupNameChange(event: GroupNameChangeEvent) {}

    /**
     * 群消息表情回应
     */
    public open fun onGroupMessageReaction(event: GroupMessageReactionEvent) {}

    /**
     * 群成员禁言状态变更
     */
    public open fun onGroupMute(event: GroupMuteEvent) {}

    /**
     * 群全员禁言状态变更
     */
    public open fun onGroupWholeMute(event: GroupWholeMuteEvent) {}

    /**
     * 群戳一戳
     */
    public open fun onGroupNudge(event: GroupNudgeEvent) {}

    /**
     * 群文件上传
     */
    public open fun onGroupFileUpload(event: GroupFileUploadEvent) {}

    /**
     * 群聊消息
     */
    public open fun onGroupMessage(event: GroupMessageEvent) {}

    /**
     * 私聊消息
     */
    public open fun onPrivateMessage(event: PrivateMessageEvent) {}

    /**
     * Bot离线
     */
    public open fun onBotOffline(event: BotOfflineEvent) {}
}