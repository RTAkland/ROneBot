/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/18/25, 2:57 AM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

@file:OptIn(InternalROneBotApi::class)

package cn.rtast.rob.milky.milky

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import cn.rtast.rob.SendAction
import cn.rtast.rob.annotations.InternalROneBotApi
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
import cn.rtast.rob.milky.enums.internal.ApiStatus
import cn.rtast.rob.milky.event.common.*
import cn.rtast.rob.milky.event.file.*
import cn.rtast.rob.milky.event.group.GetGroupAnnouncementList
import cn.rtast.rob.milky.event.message.*
import cn.rtast.rob.milky.event.system.*
import cn.rtast.rob.milky.util.requestAPI
import cn.rtast.rob.util.toJson
import love.forte.plugin.suspendtrans.annotation.JvmAsync
import love.forte.plugin.suspendtrans.annotation.JvmBlocking
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

public class MilkyAction internal constructor(
    public val botInstance: BotInstance,
) : SendAction {
    /**
     * 仅仅是为了让Action实现[SendAction]接口
     */
    override suspend fun send(message: String) {
        throw IllegalStateException()
    }

    @Suppress("FunctionName")
    private suspend inline fun <reified T> _hasResult(endpoint: APIEndpoint, payload: String? = null): T =
        botInstance.requestAPI<T>(endpoint, payload ?: "{}")

    @Suppress("FunctionName")
    private suspend fun _noResult(endpoint: APIEndpoint, payload: String? = null) =
        botInstance.requestAPI(endpoint, payload ?: "{}", Unit)

    /**
     * 获取登陆信息
     */
    @JvmBlocking
    public suspend fun getLoginInfo(): Either<GetLoginInfo.LoginInfo, String> {
        val result = this._hasResult<GetLoginInfo>(APIEndpoint.System.GetLoginInfo, null)
        return if (result.status == ApiStatus.ok) result.data!!.left() else result.message!!.right()
    }

    /**
     * 获取好友列表
     */
    @JvmBlocking
    public suspend fun getFriendList(nocache: Boolean = false): Either<List<Friend>, String> {
        val result = this._hasResult<GetFriendList>(
            APIEndpoint.System.GetFriendList,
            GetFriendListAPI(nocache).toJson()
        )
        return if (result.status == ApiStatus.ok) result.data!!.left() else result.message!!.right()
    }

    /**
     * 获取好友信息
     */
    @JvmBlocking
    public suspend fun getFriendInfo(userId: Long, noCache: Boolean = false): Either<Friend, String> {
        val result = this._hasResult<GetFriendInfo>(
            APIEndpoint.System.GetFriendInfo,
            GetFriendInfoAPI(userId, noCache).toJson()
        )
        return if (result.status == ApiStatus.ok) result.data!!.left() else result.message!!.right()
    }

    /**
     * 获取群聊信息
     */
    @JvmBlocking
    public suspend fun getGroupInfo(groupId: Long, noCache: Boolean = false): Either<Group, String> {
        val result = this._hasResult<GetGroupInfo>(
            APIEndpoint.System.GetGroupInfo,
            GetGroupInfoAPI(groupId, noCache).toJson()
        )
        return if (result.status == ApiStatus.ok) result.data!!.left() else result.message!!.right()
    }

    /**
     * 获取群聊列表
     */
    @JvmBlocking
    public suspend fun getGroupList(noCache: Boolean = false): Either<List<Group>, String> {
        val result = this._hasResult<GetGroupList>(
            APIEndpoint.System.GetGroupList,
            GetGroupListAPI(noCache).toJson()
        )
        return if (result.status == ApiStatus.ok) result.data!!.left() else result.message!!.right()
    }

    /**
     * 获取群成员信息
     */
    @JvmBlocking
    public suspend fun getGroupMemberInfo(
        groupId: Long,
        userId: Long,
        noCache: Boolean = false
    ): Either<GroupMember, String> {
        val result = this._hasResult<GetGroupMemberInfo>(
            APIEndpoint.System.GetGroupMemberInfo,
            GetGroupMemberInfoAPI(groupId, userId, noCache).toJson()
        )
        return if (result.status == ApiStatus.ok) result.data!!.left() else result.message!!.right()
    }

    /**
     * 获取群成员列表
     */
    @JvmBlocking
    public suspend fun getGroupMemberList(groupId: Long, noCache: Boolean = false): Either<List<GroupMember>, String> {
        val result = this._hasResult<GetGroupMemberList>(
            APIEndpoint.System.GetGroupMemberList,
            GetGroupMemberListAPI(groupId, noCache).toJson()
        )
        return if (result.status == ApiStatus.ok) result.data!!.left() else result.message!!.right()
    }

    /**
     * 同意请求
     */
    @JvmAsync
    @JvmBlocking
    public suspend fun acceptRequest(requestId: String) {
        this._noResult(APIEndpoint.Request.AcceptRequest, AcceptRequestAPI(requestId).toJson())
    }

    /**
     * 拒绝请求
     */
    @JvmAsync
    @JvmBlocking
    public suspend fun rejectRequest(requestId: String, reason: String = "") {
        this._noResult(APIEndpoint.Request.RejectRequest, RejectRequestAPI(requestId, reason).toJson())
    }

    /**
     * 发送好友戳一戳
     */
    @JvmAsync
    @JvmBlocking
    public suspend fun sendFriendPoke(userId: Long, isSelf: Boolean = false) {
        this._noResult(APIEndpoint.Friend.SendFriendPoke, SendFriendPokeAPI(userId, isSelf).toJson())
    }

    /**
     * 点赞对方资料卡
     */
    @JvmAsync
    @JvmBlocking
    public suspend fun sendProfileLike(userId: Long, count: Int = 1) {
        this._noResult(APIEndpoint.Friend.SendProfileLike, SendProfileLikeAPI(userId, count).toJson())
    }

    /**
     * 设置群名称
     */
    @JvmAsync
    @JvmBlocking
    public suspend fun setGroupName(groupId: Long, name: String) {
        this._noResult(APIEndpoint.Group.SetGroupName, SetGroupNameAPI(groupId, name).toJson())
    }

    /**
     * 设置群头像
     */
    @JvmAsync
    @JvmBlocking
    public suspend fun setGroupAvatar(groupId: Long, imageResource: Resource) {
        this._noResult(
            APIEndpoint.Group.SetGroupAvatar,
            SetGroupAvatarAPI(groupId, imageResource.toString()).toJson()
        )
    }

    /**
     * 设置群成员昵称
     */
    @JvmAsync
    @JvmBlocking
    public suspend fun setGroupMemberCard(groupId: Long, userId: Long, card: String) {
        this._noResult(
            APIEndpoint.Group.SetGroupMemberCard,
            SetGroupMemberCardAPI(groupId, userId, card).toJson()
        )
    }

    /**
     * 设置群成员头衔
     */
    @JvmAsync
    @JvmBlocking
    public suspend fun setGroupMemberSpecialTitle(groupId: Long, userId: Long, specialTitle: String) {
        this._noResult(
            APIEndpoint.Group.SetGroupMemberSpecialTitle,
            SetGroupMemberSpecialTitleAPI(groupId, userId, specialTitle).toJson()
        )
    }

    /**
     * 将群成员设置/取消管理员
     */
    @JvmAsync
    @JvmBlocking
    public suspend fun setGroupMemberAdmin(groupId: Long, userId: Long, isSet: Boolean = true) {
        this._noResult(
            APIEndpoint.Group.SetGroupMemberAdmin,
            SetGroupMemberAdminAPI(groupId, userId, isSet).toJson()
        )
    }

    /**
     * 禁言/解除禁言群成员
     * 0秒表示解除禁言
     */
    @JvmAsync
    @JvmBlocking
    public suspend fun setGroupMemberMute(groupId: Long, userId: Long, duration: Duration = 0.seconds) {
        this._noResult(
            APIEndpoint.Group.SetGroupMemberMute,
            SetGroupMemberMuteAPI(groupId, userId, duration.inWholeSeconds.toInt()).toJson()
        )
    }

    /**
     * 禁言/解除禁言群成员
     * 使用整形来表示
     * 0秒表示解除禁言
     */
    @JvmAsync
    @JvmBlocking
    public suspend fun setGroupMemberMute(groupId: Long, userId: Long, duration: Int = 0): Unit =
        setGroupMemberMute(groupId, userId, duration.seconds)


    /**
     * 开启全体禁言
     */
    @JvmAsync
    @JvmBlocking
    public suspend fun setGroupWholeMute(groupId: Long, isMute: Boolean = true) {
        this._noResult(APIEndpoint.Group.SetGroupWholeMute, SetGroupWholeMuteAPI(groupId, isMute).toJson())
    }

    /**
     * 踢出群成员
     */
    @JvmAsync
    @JvmBlocking
    public suspend fun kickGroupMember(groupId: Long, userId: Long, rejectAddRequest: Boolean = true) {
        this._noResult(
            APIEndpoint.Group.KickGroupMember,
            KickGroupMemberAPI(groupId, userId, rejectAddRequest).toJson()
        )
    }

    /**
     * 获取公告列表
     */
    @JvmAsync
    @JvmBlocking
    public suspend fun getGroupAnnouncementList(groupId: Long): Either<List<Announcement>, String> {
        val result = this._hasResult<GetGroupAnnouncementList>(
            APIEndpoint.Group.GetGroupAnnouncementList,
            GetGroupAnnouncementListAPI(groupId).toJson()
        )
        return if (result.status == ApiStatus.ok) result.data!!.announcements.left() else result.message!!.right()
    }

    /**
     * 发布群公告
     */
    @JvmAsync
    @JvmBlocking
    public suspend fun sendGroupAnnouncement(groupId: Long, content: String, imageResource: Resource) {
        this._noResult(
            APIEndpoint.Group.SendGroupAnnouncement,
            SendGroupAnnouncementAPI(groupId, content, imageResource.toString()).toJson()
        )
    }

    /**
     * 删除群公告
     */
    @JvmAsync
    @JvmBlocking
    public suspend fun deleteGroupAnnouncement(groupId: Long, announcementId: Long) {
        this._noResult(
            APIEndpoint.Group.DeleteGroupAnnouncement,
            DeleteGroupAnnouncementAPI(groupId, announcementId).toJson()
        )
    }

    /**
     * 退出群聊
     */
    @JvmAsync
    @JvmBlocking
    public suspend fun quitGroup(groupId: Long) {
        this._noResult(APIEndpoint.Group.QuitGroup, QuitGroupAPI(groupId).toJson())
    }

    /**
     * 发送群消息表情回应
     */
    @JvmAsync
    @JvmBlocking
    public suspend fun sendGroupMessageReaction(
        groupId: Long,
        messageSeq: Long,
        reactionId: String,
        isAdd: Boolean = true
    ) {
        this._noResult(
            APIEndpoint.Group.SendGroupMessageReaction,
            SendGroupMessageReactionAPI(groupId, messageSeq, reactionId, isAdd).toJson()
        )
    }

    /**
     * 发送群消息表情回应的简化别名etGroupAnnouncementList.Announcements
     */
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
        this._noResult(APIEndpoint.Group.SendGroupPoke, SendGroupPokeAPI(groupId, userId).toJson())
    }

    /**
     * 创建群文件夹
     */
    @JvmAsync
    @JvmBlocking
    public suspend fun createGroupFolder(
        groupId: Long,
        folderName: String
    ): Either<CreateGroupFolder.CreateGroupFolder, String> {
        val result = this._hasResult<CreateGroupFolder>(
            APIEndpoint.File.CreateGroupFolder,
            CreateGroupFolderAPI(groupId, folderName).toJson()
        )
        return if (result.status == ApiStatus.ok) result.data!!.left() else result.message!!.right()
    }

    /**
     * 删除群文件
     */
    @JvmAsync
    @JvmBlocking
    public suspend fun deleteGroupFile(groupId: Long, fileId: String) {
        this._noResult(APIEndpoint.File.DeleteGroupFile, DeleteGroupFileAPI(groupId, fileId).toJson())
    }

    /**
     * 删除群文件夹
     */
    @JvmAsync
    @JvmBlocking
    public suspend fun deleteGroupFolder(groupId: Long, folderId: String) {
        this._noResult(APIEndpoint.File.DeleteGroupFolder, DeleteGroupFolderAPI(groupId, folderId).toJson())
    }

    /**
     * 获取群聊文件下载链接
     */
    @JvmBlocking
    public suspend fun getGroupFileDownloadUrl(
        groupId: Long,
        fileId: String
    ): Either<GetGroupFileDownloadUrl.FileDownloadUrl, String> {
        val result = this._hasResult<GetGroupFileDownloadUrl>(
            APIEndpoint.File.GetGroupFileDownloadUrl,
            GetGroupFileDownloadUrlAPI(groupId, fileId).toJson()
        )
        return if (result.status == ApiStatus.ok) result.data!!.left() else result.message!!.right()
    }

    /**
     * 获取群聊文件/文件夹列表
     */
    @JvmBlocking
    public suspend fun getGroupFiles(
        groupId: Long,
        parentFolderId: String = "/"
    ): Either<GetGroupFiles.GroupFiles, String> {
        val result = this._hasResult<GetGroupFiles>(
            APIEndpoint.File.GetGroupFiles,
            GetGroupFilesAPI(groupId, parentFolderId).toJson()
        )
        return if (result.status == ApiStatus.ok) result.data!!.left() else result.message!!.right()
    }

    /**
     * 获取私聊文件下载链接
     */
    @JvmBlocking
    public suspend fun getPrivateFileDownloadUrl(
        userId: Long,
        fileId: String
    ): Either<GetPrivateFileDownloadUrl.FileDownloadUrl, String> {
        val result = this._hasResult<GetPrivateFileDownloadUrl>(
            APIEndpoint.File.GetPrivateFileDownloadUrl,
            GetPrivateFileDownloadUrlAPI(userId, fileId).toJson()
        )
        return if (result.status == ApiStatus.ok) result.data!!.left() else result.message!!.right()
    }

    /**
     * 移动群聊文件
     */
    @JvmBlocking
    public suspend fun moveGroupFile(groupId: Long, fileId: String, targetFolderId: String = "/") {
        this._noResult(APIEndpoint.File.MoveGroupFile, MoveGroupFileAPI(groupId, fileId, targetFolderId).toJson())
    }

    /**
     * 重命名群聊文件
     */
    @JvmAsync
    @JvmBlocking
    public suspend fun renameGroupFile(groupId: Long, fileId: String, newName: String) {
        this._noResult(APIEndpoint.File.MoveGroupFile, RenameGroupFileAPI(groupId, fileId, newName).toJson())
    }

    /**
     * 重命名群聊文件夹
     */
    @JvmAsync
    @JvmBlocking
    public suspend fun renameGroupFolder(groupId: Long, folderId: String, newName: String) {
        this._noResult(
            APIEndpoint.File.RenameGroupFolder,
            RenameGroupFolderAPI(groupId, folderId, newName).toJson()
        )
    }

    /**
     * 上传群聊文件
     */
    @JvmBlocking
    public suspend fun uploadGroupFile(groupId: Long, fileUri: String): Either<UploadGroupFile.GroupFile, String> {
        val result = this._hasResult<UploadGroupFile>(
            APIEndpoint.File.UploadGroupFile,
            UploadGroupFileAPI(groupId, fileUri).toJson()
        )
        return if (result.status == ApiStatus.ok) result.data!!.left() else result.message!!.right()
    }

    /**
     * 上传群聊文件
     * 但是使用[Resource]对象
     */
    @JvmBlocking
    public suspend fun uploadGroupFile(groupId: Long, resource: Resource): Either<UploadGroupFile.GroupFile, String> =
        this.uploadGroupFile(groupId, resource.toString())

    /**
     * 上传私聊文件
     */
    @JvmBlocking
    public suspend fun uploadPrivateFile(userId: Long, fileUri: String): Either<UploadPrivateFile.PrivateFile, String> {
        val result = this._hasResult<UploadPrivateFile>(
            APIEndpoint.File.UploadPrivateFile,
            UploadPrivateFileAPI(userId, fileUri).toJson()
        )
        return if (result.status == ApiStatus.ok) result.data!!.left() else result.message!!.right()
    }

    /**
     * 上传私聊文件
     * 但是使用[Resource]对象
     */
    @JvmBlocking
    public suspend fun uploadPrivateFile(
        userId: Long,
        resource: Resource
    ): Either<UploadPrivateFile.PrivateFile, String> =
        this.uploadPrivateFile(userId, resource.toString())

    /**
     * 获取合并转发消息
     */
    @JvmBlocking
    public suspend fun getForwardedMessages(forwardId: String): Either<GetForwardedMessages.ForwardedMessages, String> {
        val result = this._hasResult<GetForwardedMessages>(
            APIEndpoint.Message.GetForwardedMessages,
            GetForwardedMessagesAPI(forwardId).toJson()
        )
        return if (result.status == ApiStatus.ok) result.data!!.left() else result.message!!.right()
    }

    /**
     * 获取群历史消息
     */
    @JvmBlocking
    public suspend fun getHistoryGroupMessage(
        groupId: Long,
        startMessageSeq: Long? = null,
        limit: Int = 20
    ): Either<GetHistoryGroupMessage.GroupHistoryMessage, String> {
        val result = this._hasResult<GetHistoryGroupMessage>(
            APIEndpoint.Message.GetHistoryGroupMessage,
            GetHistoryGroupMessageAPI(groupId, startMessageSeq, limit).toJson()
        )
        return if (result.status == ApiStatus.ok) result.data!!.left() else result.message!!.right()
    }

    /**
     * 获取私聊历史消息
     */
    @JvmBlocking
    public suspend fun getHistoryPrivateMessage(
        userId: Long,
        startMessageSeq: Long? = null,
        limit: Int = 20
    ): Either<GetHistoryPrivateMessage.PrivateHistoryMessage, String> {
        val result = this._hasResult<GetHistoryPrivateMessage>(
            APIEndpoint.Message.GetHistoryPrivateMessage,
            GetHistoryPrivateMessageAPI(userId, startMessageSeq, limit).toJson()
        )
        return if (result.status == ApiStatus.ok) result.data!!.left() else result.message!!.right()
    }

    /**
     * 获取消息
     */
    @JvmBlocking
    public suspend fun getMessage(scene: MessageScene, peerId: Long, messageSeq: Long): Either<Message, String> {
        val result = this._hasResult<GetMessage>(
            APIEndpoint.Message.GetMessage,
            GetMessageAPI(scene, peerId, messageSeq).toJson()
        )
        return if (result.status == ApiStatus.ok) result.data!!.message.left() else result.message!!.right()
    }

    /**
     * 获取资源临时下载链接
     */
    @JvmBlocking
    public suspend fun getResourceTempUrl(resourceId: String): Either<GetResourceTempUrl.TempResourceUrl, String> {
        val result = this._hasResult<GetResourceTempUrl>(
            APIEndpoint.Message.GetResourceTempUrl,
            GetResourceTempUrlAPI(resourceId).toJson()
        )
        return if (result.status == ApiStatus.ok) result.data!!.left() else result.message!!.right()
    }

    /**
     * 撤回消息
     */
    @JvmBlocking
    public suspend fun recallMessage(scene: MessageScene, peerId: Long, messageSeq: Long) {
        this._noResult(APIEndpoint.Message.RecallMessage, RecallMessageAPI(scene, peerId, messageSeq).toJson())
    }

    /**
     * 发送群消息
     */
    @JvmBlocking
    public suspend fun sendGroupMessage(
        groupId: Long,
        message: MessageChain
    ): Either<SendMessageResponse.SendMessage, String> {
        val result = this._hasResult<SendMessageResponse>(
            APIEndpoint.Message.SendGroupMessage,
            SendGroupMessageAPI(groupId, message.messageList).toJson()
        )
        return if (result.status == ApiStatus.ok) result.data!!.left() else result.message!!.right()
    }

    /**
     * 发送群聊消息
     * 但是发送纯文本
     */
    @JvmBlocking
    public suspend fun sendGroupMessage(groupId: Long, message: Any): Either<SendMessageResponse.SendMessage, String> {
        val chain = message { text(message) }
        return this.sendGroupMessage(groupId, chain)
    }

    /**
     * 发送私聊消息
     */
    @JvmBlocking
    public suspend fun sendPrivateMessage(
        userId: Long,
        message: MessageChain
    ): Either<SendMessageResponse.SendMessage, String> {
        val result = this._hasResult<SendMessageResponse>(
            APIEndpoint.Message.SendPrivateMessage,
            SendPrivateMessageAPI(userId, message.messageList).toJson()
        )
        return if (result.status == ApiStatus.ok) result.data!!.left() else result.message!!.right()
    }

    /**
     * 发送私聊消息
     * 但是发送纯文本
     */
    @JvmBlocking
    public suspend fun sendPrivateMessage(userId: Long, message: Any): Either<SendMessageResponse.SendMessage, String> {
        val chain = message { text(message) }
        return this.sendPrivateMessage(userId, chain)
    }
    // Done system, friend, request, group, file, message
}