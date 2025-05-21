/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/18/25, 6:29 AM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.milky.enums.internal

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Websocket事件的枚举索引
 */
@Serializable
public enum class MilkyEvents {
    /**
     * 接收消息
     */
    @SerialName("message_receive")
    MessageReceive,

    /**
     * 撤回消息
     */
    @SerialName("message_recall")
    MessageRecall,

    /**
     * 好友请求
     */
    @SerialName("friend_request")
    FriendRequest,

    /**
     * 入群请求
     */
    @SerialName("group_join_request")
    GroupJoinRequest,

    /**
     * 邀请他人入群请求
     */
    @SerialName("group_invited_join_request")
    GroupInvitedJoinRequest,

    /**
     * 邀请自己入群请求
     */
    @SerialName("group_invitation_request")
    GroupInvitationRequest,

    /**
     * 好友戳一戳
     */
    @SerialName("friend_poke")
    FriendPoke,

    /**
     * 好友文件上传
     */
    @SerialName("friend_file_upload")
    FriendFileUpload,

    /**
     * 群管理员变更
     */
    @SerialName("group_admin_change")
    GroupAdminChange,

    /**
     * 群精华消息变更
     */
    @SerialName("group_essence_message_change")
    GroupEssenceMessageChange,

    /**
     * 群成员增加
     */
    @SerialName("group_member_increase")
    GroupMemberIncrease,

    /**
     * 群成员减少
     */
    @SerialName("group_member_decrease")
    GroupMemberDecrease,

    /**
     * 群名称变更
     */
    @SerialName("group_name_change")
    GroupNameChange,

    /**
     * 群消息表情回应
     */
    @SerialName("group_message_reaction")
    GroupMessageReaction,

    /**
     * 群成员禁言状态变更
     */
    @SerialName("group_mute")
    GroupMute,

    /**
     * 群全员禁言状态变更
     */
    @SerialName("group_whole_mute")
    GroupWholeMute,

    /**
     * 群戳一戳
     */
    @SerialName("group_poke")
    GroupPoke,

    /**
     * 群文件上传
     */
    @SerialName("group_file_upload")
    GroupFileUpload
}