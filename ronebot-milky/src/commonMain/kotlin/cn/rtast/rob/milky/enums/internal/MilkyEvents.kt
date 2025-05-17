/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/18/25, 6:29 AM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.milky.enums.internal

import kotlinx.serialization.Serializable

/**
 * Websocket事件的枚举索引
 */
@Serializable
public enum class MilkyEvents(public val event: String) {
    /**
     * 接收消息
     */
    MessageReceive("message_receive"),

    /**
     * 撤回消息
     */
    MessageRecall("message_recall"),

    /**
     * 好友请求
     */
    FriendRequest("friend_request"),

    /**
     * 入群请求
     */
    GroupJoinRequest("group_join_request"),

    /**
     * 邀请他人入群请求
     */
    GroupInvitedJoinRequest("group_invited_join_request"),

    /**
     * 邀请自己入群请求
     */
    GroupInvitationRequest("group_invitation_request"),

    /**
     * 好友戳一戳
     */
    FriendPoke("friend_poke"),

    /**
     * 好友文件上传
     */
    FriendFileUpload("friend_file_upload"),

    /**
     * 群管理员变更
     */
    GroupAdminChange("group_admin_change"),

    /**
     * 群精华消息变更
     */
    GroupEssenceMessageChange("group_essence_message_change"),

    /**
     * 群成员增加
     */
    GroupMemberIncrease("group_member_increase"),

    /**
     * 群成员减少
     */
    GroupMemberDecrease("group_member_decrease"),

    /**
     * 群名称变更
     */
    GroupNameChange("group_name_change"),

    /**
     * 群消息表情回应
     */
    GroupMessageReaction("group_message_reaction"),

    /**
     * 群成员禁言状态变更
     */
    GroupMute("group_mute"),

    /**
     * 群全员禁言状态变更
     */
    GroupWholeMute("group_whole_mute"),

    /**
     * 群戳一戳
     */
    GroupPoke("group_poke"),

    /**
     * 群文件上传
     */
    GroupFileUpload("group_file_upload")
}