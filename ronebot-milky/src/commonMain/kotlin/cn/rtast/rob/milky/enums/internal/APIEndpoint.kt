/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/18/25, 6:29 AM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.milky.enums.internal

import kotlinx.serialization.Serializable

/**
 * 将api端点保存在枚举类中
 * 并将其分类, 只需要传入[APIEndpoint]对象
 * 即可调用对应的api端点
 */
@Serializable
public sealed interface APIEndpoint {
    /**
     * api端点
     */
    public val endpoint: String

    @Serializable
    public enum class System(override val endpoint: String) : APIEndpoint {
        /**
         * 获取登录信息
         */
        GetLoginInfo("get_login_info"),

        /**
         * 获取好友列表
         */
        GetFriendList("get_friend_list"),

        /**
         * 获取好友信息
         */
        GetFriendInfo("get_friend_info"),

        /**
         * 获取群列表
         */
        GetGroupList("get_group_list"),

        /**
         * 获取群信息
         */
        GetGroupInfo("get_group_info"),

        /**
         * 获取群成员列表
         */
        GetGroupMemberList("get_group_member_list"),

        /**
         * 获取群成员信息
         */
        GetGroupMemberInfo("get_group_member_info")
    }

    @Serializable
    public enum class Message(override val endpoint: String) : APIEndpoint {
        /**
         * 发送私聊消息
         */
        SendPrivateMessage("send_private_message"),

        /**
         * 发送群消息
         */
        SendGroupMessage("send_group_message"),

        /**
         * 获取消息
         */
        GetMessage("get_message"),

        /**
         * 获取私聊消息历史
         */
        GetHistoryPrivateMessage("get_history_private_message"),

        /**
         * 获取群消息历史
         */
        GetHistoryGroupMessage("get_history_group_message"),

        /**
         * 获取临时资源链接
         */
        GetResourceTempUrl("get_resource_temp_url"),

        /**
         * 获取合并转发消息内容
         */
        GetForwardedMessages("get_forwarded_messages"),

        /**
         * 撤回消息
         */
        RecallMessage("recall_message")
    }

    @Serializable
    public enum class Friend(override val endpoint: String) : APIEndpoint {
        /**
         * 发送好友戳一戳
         */
        SendFriendPoke("send_friend_poke"),

        /**
         * 发送名片点赞
         */
        SendProfileLike("send_profile_like")
    }

    @Serializable
    public enum class Group(override val endpoint: String) : APIEndpoint {
        /**
         * 设置群名称
         */
        SetGroupName("set_group_name"),

        /**
         * 设置群头像
         */
        SetGroupAvatar("set_group_avatar"),

        /**
         * 设置群名片
         */
        SetGroupMemberCard("set_group_member_card"),

        /**
         * 设置群成员专属头衔
         */
        SetGroupMemberSpecialTitle("set_group_member_special_title"),

        /**
         * 设置群管理员
         */
        SetGroupMemberAdmin("set_group_member_admin"),

        /**
         * 设置群成员禁言
         */
        SetGroupMemberMute("set_group_member_mute"),

        /**
         * 设置群全员禁言
         */
        SetGroupWholeMute("set_group_whole_mute"),

        /**
         * 踢出群成员
         */
        KickGroupMember("kick_group_member"),

        /**
         * 获取群公告列表
         */
        GetGroupAnnouncementList("get_group_announcement_list"),

        /**
         * 发送群公告
         */
        SendGroupAnnouncement("send_group_announcement"),

        /**
         * 删除群公告
         */
        DeleteGroupAnnouncement("delete_group_announcement"),

        /**
         * 退出群
         */
        QuitGroup("quit_group"),

        /**
         * 发送群消息表情回应
         */
        SendGroupMessageReaction("send_group_message_reaction"),

        /**
         * 发送群戳一戳
         */
        SendGroupPoke("send_group_poke")
    }

    @Serializable
    public enum class Request(override val endpoint: String) : APIEndpoint {
        /**
         * 同意请求
         */
        AcceptRequest("accept_request"),

        /**
         * 拒绝请求
         */
        RejectRequest("reject_request")
    }

    @Serializable
    public enum class File(override val endpoint: String) : APIEndpoint {
        /**
         * 上传私聊文件
         */
        UploadPrivateFile("upload_private_file"),

        /**
         * 上传群文件
         */
        UploadGroupFile("upload_group_file"),

        /**
         * 获取私聊文件下载链接
         */
        GetPrivateFileDownloadUrl("get_private_file_download_url"),

        /**
         * 获取群文件下载链接
         */
        GetGroupFileDownloadUrl("get_group_file_download_url"),

        /**
         * 获取群文件列表
         */
        GetGroupFiles("get_group_files"),

        /**
         * 移动群文件
         */
        MoveGroupFile("move_group_file"),

        /**
         * 重命名群文件
         */
        RenameGroupFile("rename_group_file"),

        /**
         * 删除群文件
         */
        DeleteGroupFile("delete_group_file"),

        /**
         * 创建群文件夹
         */
        CreateGroupFolder("create_group_folder"),

        /**
         * 重命名群文件夹
         */
        RenameGroupFolder("rename_group_folder"),

        /**
         * 删除群文件夹
         */
        DeleteGroupFolder("delete_group_folder")
    }
}