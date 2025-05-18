/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/18/25, 2:57 AM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.milky.milky

import cn.rtast.rob.entity.Resource
import cn.rtast.rob.milky.BotInstance
import cn.rtast.rob.milky.api.file.*
import cn.rtast.rob.milky.api.friend.SendFriendPokeAPI
import cn.rtast.rob.milky.api.friend.SendProfileLikeAPI
import cn.rtast.rob.milky.api.group.*
import cn.rtast.rob.milky.api.message.*
import cn.rtast.rob.milky.api.request.AcceptRequestAPI
import cn.rtast.rob.milky.api.request.RejectRequestAPI
import cn.rtast.rob.milky.api.system.*
import cn.rtast.rob.milky.enums.MessageScene
import cn.rtast.rob.milky.enums.internal.APIEndpoint
import cn.rtast.rob.milky.event.common.Announcement
import cn.rtast.rob.milky.event.common.Friend
import cn.rtast.rob.milky.event.common.Group
import cn.rtast.rob.milky.event.common.GroupMember
import cn.rtast.rob.milky.event.file.*
import cn.rtast.rob.milky.event.group.GetGroupAnnouncementList
import cn.rtast.rob.milky.event.message.*
import cn.rtast.rob.milky.event.system.*
import cn.rtast.rob.milky.util.requestAPI
import love.forte.plugin.suspendtrans.annotation.JvmAsync
import love.forte.plugin.suspendtrans.annotation.JvmBlocking
import kotlin.jvm.JvmOverloads
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

public class MilkyAction internal constructor(
    public val botInstance: BotInstance,
) {
    private suspend inline fun <reified T, K> api(endpoint: APIEndpoint, payload: K? = null): T =
        botInstance.requestAPI<T, K>(endpoint, payload)

    private suspend fun <K> api(endpoint: APIEndpoint, payload: K? = null) =
        botInstance.requestAPI<K>(endpoint, payload)

    /**
     * 获取好友列表
     */
    @JvmOverloads
    @JvmBlocking
    public suspend fun getFriendList(nocache: Boolean = false): List<Friend> {
        return this.api<GetFriendList, GetFriendListAPI>(
            APIEndpoint.System.GetFriendList,
            GetFriendListAPI(nocache)
        ).data
    }

    /**
     * 获取好友信息
     */
    @JvmOverloads
    @JvmBlocking
    public suspend fun getFriendInfo(userId: Long, noCache: Boolean = false): Friend {
        return this.api<GetFriendInfo, GetFriendInfoAPI>(
            APIEndpoint.System.GetFriendInfo,
            GetFriendInfoAPI(userId, noCache)
        ).data
    }

    /**
     * 获取群聊信息
     */
    @JvmOverloads
    @JvmBlocking
    public suspend fun getGroupInfo(groupId: Long, noCache: Boolean = false): Group {
        return this.api<GetGroupInfo, GetGroupInfoAPI>(
            APIEndpoint.System.GetGroupInfo,
            GetGroupInfoAPI(groupId, noCache)
        ).data
    }

    /**
     * 获取群聊列表
     */
    @JvmOverloads
    @JvmBlocking
    public suspend fun getGroupList(noCache: Boolean = false): List<Group> {
        return this.api<GetGroupList, GetGroupListAPI>(
            APIEndpoint.System.GetGroupList,
            GetGroupListAPI(noCache)
        ).data
    }

    /**
     * 获取群成员信息
     */
    @JvmOverloads
    @JvmBlocking
    public suspend fun getGroupMemberInfo(groupId: Long, userId: Long, noCache: Boolean = false): GroupMember {
        return this.api<GetGroupMemberInfo, GetGroupMemberInfoAPI>(
            APIEndpoint.System.GetGroupMemberInfo,
            GetGroupMemberInfoAPI(groupId, userId, noCache)
        ).data
    }

    /**
     * 获取群成员列表
     */
    @JvmOverloads
    @JvmBlocking
    public suspend fun getGroupMemberList(groupId: Long, noCache: Boolean = false): List<GroupMember> {
        return this.api<GetGroupMemberList, GetGroupMemberListAPI>(
            APIEndpoint.System.GetGroupMemberList,
            GetGroupMemberListAPI(groupId, noCache)
        ).data
    }

    /**
     * 同意请求
     */
    @JvmAsync
    @JvmBlocking
    public suspend fun acceptRequest(requestId: String) {
        this.api<AcceptRequestAPI>(APIEndpoint.Request.AcceptRequest, AcceptRequestAPI(requestId))
    }

    /**
     * 拒绝请求
     */
    @JvmOverloads
    @JvmAsync
    @JvmBlocking
    public suspend fun rejectRequest(requestId: String, reason: String = "") {
        this.api<RejectRequestAPI>(APIEndpoint.Request.RejectRequest, RejectRequestAPI(requestId, reason))
    }

    /**
     * 发送好友戳一戳
     */
    @JvmOverloads
    @JvmAsync
    @JvmBlocking
    public suspend fun sendFriendPoke(userId: Long, isSelf: Boolean = false) {
        this.api<SendFriendPokeAPI>(APIEndpoint.Friend.SendFriendPoke, SendFriendPokeAPI(userId, isSelf))
    }

    /**
     * 点赞对方资料卡
     */
    @JvmOverloads
    @JvmAsync
    @JvmBlocking
    public suspend fun sendProfileLike(userId: Long, count: Int = 1) {
        this.api<SendProfileLikeAPI>(APIEndpoint.Friend.SendProfileLike, SendProfileLikeAPI(userId, count))
    }

    /**
     * 设置群名称
     */
    @JvmAsync
    @JvmBlocking
    public suspend fun setGroupName(groupId: Long, name: String) {
        this.api<SetGroupNameAPI>(APIEndpoint.Group.SetGroupName, SetGroupNameAPI(groupId, name))
    }

    /**
     * 设置群头像
     */
    @JvmAsync
    @JvmBlocking
    public suspend fun setGroupAvatar(groupId: Long, imageResource: Resource) {
        this.api<SetGroupAvatarAPI>(
            APIEndpoint.Group.SetGroupAvatar,
            SetGroupAvatarAPI(groupId, imageResource.toString())
        )
    }

    /**
     * 设置群成员昵称
     */
    @JvmAsync
    @JvmBlocking
    public suspend fun setGroupMemberCard(groupId: Long, userId: Long, card: String) {
        this.api<SetGroupMemberCardAPI>(
            APIEndpoint.Group.SetGroupMemberCard,
            SetGroupMemberCardAPI(groupId, userId, card)
        )
    }

    /**
     * 设置群成员头衔
     */
    @JvmAsync
    @JvmBlocking
    public suspend fun setGroupMemberSpecialTitle(groupId: Long, userId: Long, specialTitle: String) {
        this.api<SetGroupMemberSpecialTitleAPI>(
            APIEndpoint.Group.SetGroupMemberSpecialTitle,
            SetGroupMemberSpecialTitleAPI(groupId, userId, specialTitle)
        )
    }

    /**
     * 将群成员设置/取消管理员
     */
    @JvmOverloads
    @JvmAsync
    @JvmBlocking
    public suspend fun setGroupMemberAdmin(groupId: Long, userId: Long, isSet: Boolean = true) {
        this.api<SetGroupMemberAdminAPI>(
            APIEndpoint.Group.SetGroupMemberAdmin,
            SetGroupMemberAdminAPI(groupId, userId, isSet)
        )
    }

    /**
     * 禁言/解除禁言群成员
     * 0秒表示解除禁言
     */
    @JvmOverloads
    @JvmAsync
    @JvmBlocking
    public suspend fun setGroupMemberMute(groupId: Long, userId: Long, duration: Duration = 0.seconds) {
        this.api<SetGroupMemberMuteAPI>(
            APIEndpoint.Group.SetGroupMemberMute,
            SetGroupMemberMuteAPI(groupId, userId, duration.inWholeSeconds.toInt())
        )
    }

    /**
     * 禁言/解除禁言群成员
     * 使用整形来表示
     * 0秒表示解除禁言
     */
    @JvmOverloads
    @JvmAsync
    @JvmBlocking
    public suspend fun setGroupMemberMute(groupId: Long, userId: Long, duration: Int = 0): Unit =
        setGroupMemberMute(groupId, userId, duration.seconds)


    /**
     * 开启全体禁言
     */
    @JvmOverloads
    @JvmAsync
    @JvmBlocking
    public suspend fun setGroupWholeMute(groupId: Long, isMute: Boolean = true) {
        this.api<SetGroupWholeMuteAPI>(APIEndpoint.Group.SetGroupWholeMute, SetGroupWholeMuteAPI(groupId, isMute))
    }

    /**
     * 踢出群成员
     */
    @JvmOverloads
    @JvmAsync
    @JvmBlocking
    public suspend fun kickGroupMember(groupId: Long, userId: Long, rejectAddRequest: Boolean = true) {
        this.api<KickGroupMemberAPI>(
            APIEndpoint.Group.KickGroupMember,
            KickGroupMemberAPI(groupId, userId, rejectAddRequest)
        )
    }

    /**
     * 获取公告列表
     */
    @JvmAsync
    @JvmBlocking
    public suspend fun getGroupAnnouncementList(groupId: Long): List<Announcement> {
        return this.api<GetGroupAnnouncementList, GetGroupAnnouncementListAPI>(
            APIEndpoint.Group.GetGroupAnnouncementList,
            GetGroupAnnouncementListAPI(groupId)
        ).data.announcements
    }

    /**
     * 发布群公告
     */
    @JvmAsync
    @JvmBlocking
    public suspend fun sendGroupAnnouncement(groupId: Long, content: String, imageResource: Resource) {
        this.api<SendGroupAnnouncementAPI>(
            APIEndpoint.Group.SendGroupAnnouncement,
            SendGroupAnnouncementAPI(groupId, content, imageResource.toString())
        )
    }

    /**
     * 删除群公告
     */
    @JvmAsync
    @JvmBlocking
    public suspend fun deleteGroupAnnouncement(groupId: Long, announcementId: Long) {
        this.api<DeleteGroupAnnouncementAPI>(
            APIEndpoint.Group.DeleteGroupAnnouncement,
            DeleteGroupAnnouncementAPI(groupId, announcementId)
        )
    }

    /**
     * 退出群聊
     */
    @JvmAsync
    @JvmBlocking
    public suspend fun quitGroup(groupId: Long) {
        this.api<QuitGroupAPI>(APIEndpoint.Group.QuitGroup, QuitGroupAPI(groupId))
    }

    /**
     * 发送群消息表情回应
     */
    @JvmOverloads
    @JvmAsync
    @JvmBlocking
    public suspend fun sendGroupMessageReaction(
        groupId: Long,
        messageSeq: Long,
        reactionId: String,
        isAdd: Boolean = true
    ) {
        this.api<SendGroupMessageReactionAPI>(
            APIEndpoint.Group.SendGroupMessageReaction,
            SendGroupMessageReactionAPI(groupId, messageSeq, reactionId, isAdd)
        )
    }

    /**
     * 发送群消息表情回应的简化别名etGroupAnnouncementList.Announcements
     */
    @JvmOverloads
    @JvmAsync
    @JvmBlocking
    public suspend fun reaction(groupId: Long, messageSeq: Long, reactionId: String, isAdd: Boolean = true): Unit =
        sendGroupMessageReaction(groupId, messageSeq, reactionId, isAdd)

    /**
     * 发送群戳一戳
     */
    @JvmAsync
    @JvmBlocking
    public suspend fun sendGroupPoke(groupId: Long, userId: Long) {
        this.api<SendGroupPokeAPI>(APIEndpoint.Group.SendGroupPoke, SendGroupPokeAPI(groupId, userId))
    }

    /**
     * 创建群文件夹
     */
    @JvmAsync
    @JvmBlocking
    public suspend fun createGroupFolder(groupId: Long, folderName: String): CreateGroupFolder.CreateGroupFolder {
        return this.api<CreateGroupFolder, CreateGroupFolderAPI>(
            APIEndpoint.File.CreateGroupFolder,
            CreateGroupFolderAPI(groupId, folderName)
        ).data
    }

    /**
     * 删除群文件
     */
    @JvmAsync
    @JvmBlocking
    public suspend fun deleteGroupFile(groupId: Long, fileId: String) {
        this.api<DeleteGroupFileAPI>(APIEndpoint.File.DeleteGroupFile, DeleteGroupFileAPI(groupId, fileId))
    }

    /**
     * 删除群文件夹
     */
    @JvmAsync
    @JvmBlocking
    public suspend fun deleteGroupFolder(groupId: Long, folderId: String) {
        this.api<DeleteGroupFolderAPI>(APIEndpoint.File.DeleteGroupFolder, DeleteGroupFolderAPI(groupId, folderId))
    }

    /**
     * 获取群聊文件下载链接
     */
    @JvmBlocking
    public suspend fun getGroupFileDownloadUrl(groupId: Long, fileId: String): GetGroupFileDownloadUrl.FileDownloadUrl {
        return this.api<GetGroupFileDownloadUrl, GetGroupFileDownloadUrlAPI>(
            APIEndpoint.File.GetGroupFileDownloadUrl,
            GetGroupFileDownloadUrlAPI(groupId, fileId)
        ).data
    }

    /**
     * 获取群聊文件/文件夹列表
     */
    @JvmOverloads
    @JvmBlocking
    public suspend fun getGroupFiles(groupId: Long, parentFolderId: String = "/"): GetGroupFiles.GroupFiles {
        return this.api<GetGroupFiles, GetGroupFilesAPI>(
            APIEndpoint.File.GetGroupFiles,
            GetGroupFilesAPI(groupId, parentFolderId)
        ).data
    }

    /**
     * 获取私聊文件下载链接
     */
    @JvmBlocking
    public suspend fun getPrivateFileDownloadUrl(
        userId: Long,
        fileId: String
    ): GetPrivateFileDownloadUrl.FileDownloadUrl {
        return this.api<GetPrivateFileDownloadUrl, GetPrivateFileDownloadUrlAPI>(
            APIEndpoint.File.GetPrivateFileDownloadUrl,
            GetPrivateFileDownloadUrlAPI(userId, fileId)
        ).data
    }

    /**
     * 移动群聊文件
     */
    @JvmOverloads
    @JvmBlocking
    public suspend fun moveGroupFile(groupId: Long, fileId: String, targetFolderId: String = "/") {
        this.api<MoveGroupFileAPI>(APIEndpoint.File.MoveGroupFile, MoveGroupFileAPI(groupId, fileId, targetFolderId))
    }

    /**
     * 重命名群聊文件
     */
    @JvmAsync
    @JvmBlocking
    public suspend fun renameGroupFile(groupId: Long, fileId: String, newName: String) {
        this.api<RenameGroupFileAPI>(APIEndpoint.File.MoveGroupFile, RenameGroupFileAPI(groupId, fileId, newName))
    }

    /**
     * 重命名群聊文件夹
     */
    @JvmAsync
    @JvmBlocking
    public suspend fun renameGroupFolder(groupId: Long, folderId: String, newName: String) {
        this.api<RenameGroupFolderAPI>(
            APIEndpoint.File.RenameGroupFolder,
            RenameGroupFolderAPI(groupId, folderId, newName)
        )
    }

    /**
     * 上传群聊文件
     */
    @JvmBlocking
    public suspend fun uploadGroupFile(groupId: Long, fileUri: String): UploadGroupFile.GroupFile {
        return this.api<UploadGroupFile, UploadGroupFileAPI>(
            APIEndpoint.File.UploadGroupFile,
            UploadGroupFileAPI(groupId, fileUri)
        ).data
    }

    /**
     * 上传群聊文件
     * 但是使用[Resource]对象
     */
    @JvmBlocking
    public suspend fun uploadGroupFile(groupId: Long, resource: Resource): UploadGroupFile.GroupFile =
        this.uploadGroupFile(groupId, resource.toString())

    /**
     * 上传私聊文件
     */
    @JvmBlocking
    public suspend fun uploadPrivateFile(userId: Long, fileUri: String): UploadPrivateFile.PrivateFile {
        return this.api<UploadPrivateFile, UploadPrivateFileAPI>(
            APIEndpoint.File.UploadPrivateFile,
            UploadPrivateFileAPI(userId, fileUri)
        ).data
    }

    /**
     * 上传私聊文件
     * 但是使用[Resource]对象
     */
    @JvmBlocking
    public suspend fun uploadPrivateFile(userId: Long, resource: Resource): UploadPrivateFile.PrivateFile =
        this.uploadPrivateFile(userId, resource.toString())

    /**
     * 获取合并转发消息
     */
    @JvmBlocking
    public suspend fun getForwardedMessages(forwardId: String): GetForwardedMessages.ForwardedMessages {
        return this.api<GetForwardedMessages, GetForwardedMessagesAPI>(
            APIEndpoint.Message.GetForwardedMessages,
            GetForwardedMessagesAPI(forwardId)
        ).data
    }

    /**
     * 获取群历史消息
     */
    @JvmOverloads
    @JvmBlocking
    public suspend fun getHistoryGroupMessage(
        groupId: Long,
        startMessageSeq: Long? = null,
        limit: Int = 20
    ): GetHistoryGroupMessage.GroupHistoryMessage {
        return this.api<GetHistoryGroupMessage, GetHistoryGroupMessageAPI>(
            APIEndpoint.Message.GetHistoryGroupMessage,
            GetHistoryGroupMessageAPI(groupId, startMessageSeq, limit)
        ).data
    }

    /**
     * 获取私聊历史消息
     */
    @JvmOverloads
    @JvmBlocking
    public suspend fun getHistoryPrivateMessage(
        userId: Long,
        startMessageSeq: Long? = null,
        limit: Int = 20
    ): GetHistoryPrivateMessage.PrivateHistoryMessage {
        return this.api<GetHistoryPrivateMessage, GetHistoryPrivateMessageAPI>(
            APIEndpoint.Message.GetHistoryPrivateMessage,
            GetHistoryPrivateMessageAPI(userId, startMessageSeq, limit)
        ).data
    }

    /**
     * 获取消息
     */
    @JvmBlocking
    public suspend fun getMessage(scene: MessageScene, peerId: Long, messageSeq: Long): Message {
        return this.api<GetMessage, GetMessageAPI>(
            APIEndpoint.Message.GetMessage,
            GetMessageAPI(scene, peerId, messageSeq)
        ).data.message
    }

    /**
     * 获取资源临时下载链接
     */
    @JvmBlocking
    public suspend fun getResourceTempUrl(resourceId: String): GetResourceTempUrl.TempResourceUrl {
        return this.api<GetResourceTempUrl, GetResourceTempUrlAPI>(
            APIEndpoint.Message.GetResourceTempUrl,
            GetResourceTempUrlAPI(resourceId)
        ).data
    }

    /**
     * 撤回消息
     */
    @JvmBlocking
    public suspend fun recallMessage(scene: MessageScene, peerId: Long, messageSeq: Long) {
        this.api<RecallMessageAPI>(APIEndpoint.Message.RecallMessage, RecallMessageAPI(scene, peerId, messageSeq))
    }

    /**
     * 发送群消息
     */
    @JvmBlocking
    public suspend fun sendGroupMessage(groupId: Long, message: MessageChain): SendMessageResponse.SendMessage {
        return this.api<SendMessageResponse, SendGroupMessageAPI>(
            APIEndpoint.Message.SendGroupMessage,
            SendGroupMessageAPI(groupId, message.messageList)
        ).data
    }

    /**
     * 发送群聊消息
     * 但是发送纯文本
     */
    @JvmBlocking
    public suspend fun sendGroupMessage(groupId: Long, message: Any): SendMessageResponse.SendMessage {
        val chain = message { text(message) }
        return this.sendGroupMessage(groupId, chain)
    }

    /**
     * 发送私聊消息
     */
    @JvmBlocking
    public suspend fun sendPrivateMessage(userId: Long, message: MessageChain): SendMessageResponse.SendMessage {
        return this.api<SendMessageResponse, SendPrivateMessageAPI>(
            APIEndpoint.Message.SendPrivateMessage,
            SendPrivateMessageAPI(userId, message.messageList)
        ).data
    }

    /**
     * 发送私聊消息
     * 但是发送纯文本
     */
    @JvmBlocking
    public suspend fun sendPrivateMessage(userId: Long, message: Any): SendMessageResponse.SendMessage {
        val chain = message { text(message) }
        return this.sendPrivateMessage(userId, chain)
    }
    // Done system, friend, request, group, file, message
}