/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/18/25, 10:45 PM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.milky.milky

import cn.rtast.rob.milky.event.ws.packed.*
import kotlin.jvm.JvmSynthetic

public interface MilkyListener {
    /**
     * websocket连接上服务器时
     */
    @JvmSynthetic
    public suspend fun onConnected(event: WebsocketConnectedEvent) {
    }

    public fun onConnectedJvm(event: WebsocketConnectedEvent) {}

    /**
     * websocket连接从服务器断开时
     */
    @JvmSynthetic
    public suspend fun onDisconnected(event: WebsocketDisconnectedEvent) {
    }

    public fun onDisconnectedJvm(event: WebsocketDisconnectedEvent) {}

    /**
     * websocket服务器下发原始(raw)消息时
     */
    @JvmSynthetic
    public suspend fun onRawMessage(event: RawMessageEvent) {
    }

    public fun onRawMessageJvm(event: RawMessageEvent) {}

    /**
     * 接收消息
     */
    @JvmSynthetic
    public suspend fun onMessageReceive(event: MessageReceiveEvent) {
    }

    public fun onMessageReceiveJvm(event: MessageReceiveEvent) {}

    /**
     * 消息撤回
     */
    @JvmSynthetic
    public suspend fun onMessageRecall(event: MessageRecallEvent) {
    }

    public fun onMessageRecallJvm(event: MessageRecallEvent) {}

    /**
     * 好友请求
     */
    @JvmSynthetic
    public suspend fun onFriendRequest(event: FriendRequestEvent) {
    }

    public fun onFriendRequestJvm(event: FriendRequestEvent) {}

    /**
     * 加群请求
     */
    @JvmSynthetic
    public suspend fun onGroupJoinRequestEvent(event: GroupJoinRequestEvent) {
    }

    public fun onGroupJoinRequestEventJvm(event: GroupJoinRequestEvent) {}

    /**
     * 群聊邀请
     */
    @JvmSynthetic
    public suspend fun onGroupInvitedJoinRequestEvent(event: GroupInvitedJoinRequestEvent) {
    }

    public suspend fun onGroupInvitedJoinRequestEventJvm(event: GroupInvitedJoinRequestEvent) {}

    /**
     * 邀请自己入群请求
     */
    @JvmSynthetic
    public suspend fun onGroupInvitationRequestEvent(event: GroupInvitationRequestEvent) {
    }

    public fun onGroupInvitationRequestEventJvm(event: GroupInvitationRequestEvent) {}

    /**
     * 好友戳一戳
     */
    @JvmSynthetic
    public suspend fun onFriendNudgeEvent(event: FriendNudgeEvent) {
    }

    public fun onFriendNudgeEventJvm(event: FriendNudgeEvent) {}

    /**
     * 好友文件上传
     */
    @JvmSynthetic
    public suspend fun onFriendFileUploadEvent(event: FriendFileUploadEvent) {
    }

    public fun onFriendFileUploadEventJvm(event: FriendFileUploadEvent) {}

    /**
     * 群管理员变更
     */
    @JvmSynthetic
    public suspend fun onGroupAdminChangeEvent(event: GroupAdminChangeEvent) {
    }

    public fun onGroupAdminChangeEventJvm(event: GroupAdminChangeEvent) {}

    /**
     * 群精华消息变更
     */
    @JvmSynthetic
    public suspend fun onGroupEssenceMessageChangeEvent(event: GroupEssenceMessageChangeEvent) {
    }

    public fun onGroupEssenceMessageChangeEventJvm(event: GroupEssenceMessageChangeEvent) {}

    /**
     * 群成员增加
     */
    @JvmSynthetic
    public suspend fun onGroupMemberIncreaseEvent(event: GroupMemberIncreaseEvent) {
    }

    public fun onGroupMemberIncreaseEventJvm(event: GroupMemberIncreaseEvent) {}

    /**
     * 群成员减少
     */
    @JvmSynthetic
    public suspend fun onGroupMemberDecreaseEvent(event: GroupMemberDecreaseEvent) {
    }

    public fun onGroupMemberDecreaseEventJvm(event: GroupMemberDecreaseEvent) {}

    /**
     * 群名称变更
     */
    @JvmSynthetic
    public suspend fun onGroupNameChangeEvent(event: GroupNameChangeEvent) {
    }

    public fun onGroupNameChangeEventJvm(event: GroupNameChangeEvent) {}

    /**
     * 群消息表情回应
     */
    @JvmSynthetic
    public suspend fun onGroupMessageReactionEvent(event: GroupMessageReactionEvent) {
    }

    public fun onGroupMessageReactionEventJvm(event: GroupMessageReactionEvent) {}

    /**
     * 群成员禁言状态变更
     */
    @JvmSynthetic
    public suspend fun onGroupMuteEvent(event: GroupMuteEvent) {
    }

    public fun onGroupMuteEventJvm(event: GroupMuteEvent) {}

    /**
     * 群全员禁言状态变更
     */
    @JvmSynthetic
    public suspend fun onGroupWholeMuteEvent(event: GroupWholeMuteEvent) {
    }

    public fun onGroupWholeMuteEventJvm(event: GroupWholeMuteEvent) {}

    /**
     * 群戳一戳
     */
    @JvmSynthetic
    public suspend fun onGroupNudgeEvent(event: GroupNudgeEvent) {
    }

    public fun onGroupNudgeEventJvm(event: GroupNudgeEvent) {}

    /**
     * 群文件上传
     */
    @JvmSynthetic
    public suspend fun onGroupFileUploadEvent(event: GroupFileUploadEvent) {
    }

    public fun onGroupFileUploadEventJvm(event: GroupFileUploadEvent) {}
}