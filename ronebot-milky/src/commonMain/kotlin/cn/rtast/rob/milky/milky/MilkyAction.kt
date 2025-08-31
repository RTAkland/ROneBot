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
import cn.rtast.rob.milky.api.friend.GetFriendRequestsAPI
import cn.rtast.rob.milky.api.friend.SendFriendPokeAPI
import cn.rtast.rob.milky.api.friend.SendProfileLikeAPI
import cn.rtast.rob.milky.api.group.*
import cn.rtast.rob.milky.api.message.*
import cn.rtast.rob.milky.api.request.AcceptFriendRequestAPI
import cn.rtast.rob.milky.api.request.RejectFriendRequestAPI
import cn.rtast.rob.milky.api.system.*
import cn.rtast.rob.milky.enums.MessageScene
import cn.rtast.rob.milky.enums.NotificationType
import cn.rtast.rob.milky.enums.internal.APIEndpoint
import cn.rtast.rob.milky.enums.internal.ApiStatus
import cn.rtast.rob.milky.event.common.*
import cn.rtast.rob.milky.event.file.*
import cn.rtast.rob.milky.event.friend.GetFriendRequests
import cn.rtast.rob.milky.event.group.GetGroupAnnouncementList
import cn.rtast.rob.milky.event.group.GetGroupEssenceMessages
import cn.rtast.rob.milky.event.group.notification.GetGroupNotifications
import cn.rtast.rob.milky.event.message.*
import cn.rtast.rob.milky.event.system.*
import cn.rtast.rob.milky.event.ws.raw.RawFriendRequestEvent
import cn.rtast.rob.milky.util.requestAPI
import cn.rtast.rob.util.toJson
import love.forte.plugin.suspendtrans.annotation.JvmAsync
import love.forte.plugin.suspendtrans.annotation.JvmBlocking
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

/**
 * 这个类暴露出了所有可以对Bot实例作出的`行为`
 * 被[JvmBlocking]注解的函数表示可以在Java中阻塞调用
 * 被[JvmAsync]注解的函数表示可以在Java中异步调用
 * 所有有返回值的函数都使用了`arrow-kt`来表示, 当调用成功时
 * 右侧的值为返回值, 左侧没有值, 调用失败时左侧的值为Milky实现
 * 返回的错误消息, 右侧没有值
 *
 * 这个类不能被手动创建，只能通过创建一个[BotInstance]来创建此类
 */
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
    public suspend fun getLoginInfo(): Either<String, GetLoginInfo.LoginInfo> {
        val result = this._hasResult<GetLoginInfo>(APIEndpoint.System.GetLoginInfo, null)
        return if (result.status == ApiStatus.OK) result.data!!.right() else result.message!!.left()
    }

    /**
     * 获取好友列表
     */
    @JvmBlocking
    public suspend fun getFriendList(nocache: Boolean = false): Either<String, List<Friend>> {
        val result = this._hasResult<GetFriendList>(
            APIEndpoint.System.GetFriendList,
            GetFriendListAPI(nocache).toJson()
        )
        return if (result.status == ApiStatus.OK) result.data!!.friends.right() else result.message!!.left()
    }

    /**
     * 获取好友信息
     */
    @JvmBlocking
    public suspend fun getFriendInfo(userId: Long, noCache: Boolean = false): Either<String, Friend> {
        val result = this._hasResult<GetFriendInfo>(
            APIEndpoint.System.GetFriendInfo,
            GetFriendInfoAPI(userId, noCache).toJson()
        )
        return if (result.status == ApiStatus.OK) result.data!!.right() else result.message!!.left()
    }

    /**
     * 获取群聊信息
     */
    @JvmBlocking
    public suspend fun getGroupInfo(groupId: Long, noCache: Boolean = false): Either<String, Group> {
        val result = this._hasResult<GetGroupInfo>(
            APIEndpoint.System.GetGroupInfo,
            GetGroupInfoAPI(groupId, noCache).toJson()
        )
        return if (result.status == ApiStatus.OK) result.data!!.right() else result.message!!.left()
    }

    /**
     * 获取群聊列表
     */
    @JvmBlocking
    public suspend fun getGroupList(noCache: Boolean = false): Either<String, List<Group>> {
        val result = this._hasResult<GetGroupList>(
            APIEndpoint.System.GetGroupList,
            GetGroupListAPI(noCache).toJson()
        )
        return if (result.status == ApiStatus.OK) result.data!!.right() else result.message!!.left()
    }

    /**
     * 获取群成员信息
     */
    @JvmBlocking
    public suspend fun getGroupMemberInfo(
        groupId: Long,
        userId: Long,
        noCache: Boolean = false,
    ): Either<String, GroupMember> {
        val result = this._hasResult<GetGroupMemberInfo>(
            APIEndpoint.System.GetGroupMemberInfo,
            GetGroupMemberInfoAPI(groupId, userId, noCache).toJson()
        )
        return if (result.status == ApiStatus.OK) result.data!!.right() else result.message!!.left()
    }

    /**
     * 获取群成员列表
     */
    @JvmBlocking
    public suspend fun getGroupMemberList(groupId: Long, noCache: Boolean = false): Either<String, List<GroupMember>> {
        val result = this._hasResult<GetGroupMemberList>(
            APIEndpoint.System.GetGroupMemberList,
            GetGroupMemberListAPI(groupId, noCache).toJson()
        )
        return if (result.status == ApiStatus.OK) result.data!!.right() else result.message!!.left()
    }

    /**
     * 发送好友戳一戳
     * @param isSelf 是否戳自己
     */
    @JvmAsync
    @JvmBlocking
    @Deprecated(
        level = DeprecationLevel.WARNING,
        message = "New function name: sendFriendNudge",
        replaceWith = ReplaceWith("cn.rtast.rob.milky.milky.MilkyAction.sendFriendNudge")
    )
    public suspend fun sendFriendPoke(userId: Long, isSelf: Boolean = false) {
        this.sendFriendNudge(userId, isSelf)
    }

    // New friend api

    /**
     * 发送好友戳一戳
     * @param isSelf 是否戳自己
     */
    @JvmAsync
    @JvmBlocking
    public suspend fun sendFriendNudge(userId: Long, isSelf: Boolean = false) {
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
    public suspend fun getGroupAnnouncementList(groupId: Long): Either<String, List<Announcement>> {
        val result = this._hasResult<GetGroupAnnouncementList>(
            APIEndpoint.Group.GetGroupAnnouncementList,
            GetGroupAnnouncementListAPI(groupId).toJson()
        )
        return if (result.status == ApiStatus.OK) result.data!!.announcements.right() else result.message!!.left()
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
        isAdd: Boolean = true,
    ) {
        this._noResult(
            APIEndpoint.Group.SendGroupMessageReaction,
            SendGroupMessageReactionAPI(groupId, messageSeq, reactionId, isAdd).toJson()
        )
    }

    /**
     * 发送群消息表情回应的简化别名
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
    @Deprecated(level = DeprecationLevel.WARNING, message = "Renamed", replaceWith = ReplaceWith(""))
    public suspend fun sendGroupPoke(groupId: Long, userId: Long) {
        this.sendGroupNudge(groupId, userId)
    }

    /**
     * 发送群戳一戳
     */
    @JvmAsync
    @JvmBlocking
    public suspend fun sendGroupNudge(groupId: Long, userId: Long) {
        this._noResult(APIEndpoint.Group.SendGroupNudge, SendGroupNudgeAPI(groupId, userId).toJson())
    }

    /**
     * 创建群文件夹
     */
    @JvmAsync
    @JvmBlocking
    public suspend fun createGroupFolder(
        groupId: Long,
        folderName: String,
    ): Either<String, CreateGroupFolder.CreateGroupFolder> {
        val result = this._hasResult<CreateGroupFolder>(
            APIEndpoint.File.CreateGroupFolder,
            CreateGroupFolderAPI(groupId, folderName).toJson()
        )
        return if (result.status == ApiStatus.OK) result.data!!.right() else result.message!!.left()
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
        fileId: String,
    ): Either<String, GetGroupFileDownloadUrl.FileDownloadUrl> {
        val result = this._hasResult<GetGroupFileDownloadUrl>(
            APIEndpoint.File.GetGroupFileDownloadUrl,
            GetGroupFileDownloadUrlAPI(groupId, fileId).toJson()
        )
        return if (result.status == ApiStatus.OK) result.data!!.right() else result.message!!.left()
    }

    /**
     * 获取群聊文件/文件夹列表
     */
    @JvmBlocking
    public suspend fun getGroupFiles(
        groupId: Long,
        parentFolderId: String = "/",
    ): Either<String, GetGroupFiles.GroupFiles> {
        val result = this._hasResult<GetGroupFiles>(
            APIEndpoint.File.GetGroupFiles,
            GetGroupFilesAPI(groupId, parentFolderId).toJson()
        )
        return if (result.status == ApiStatus.OK) result.data!!.right() else result.message!!.left()
    }

    /**
     * 获取私聊文件下载链接
     */
    @JvmBlocking
    public suspend fun getPrivateFileDownloadUrl(
        userId: Long,
        fileId: String,
        fileHash: String,
    ): Either<String, GetPrivateFileDownloadUrl.FileDownloadUrl> {
        val result = this._hasResult<GetPrivateFileDownloadUrl>(
            APIEndpoint.File.GetPrivateFileDownloadUrl,
            GetPrivateFileDownloadUrlAPI(userId, fileId, fileHash).toJson()
        )
        return if (result.status == ApiStatus.OK) result.data!!.right() else result.message!!.left()
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
    public suspend fun uploadGroupFile(
        groupId: Long,
        fileUri: String,
        fileName: String,
        parentFolderId: String,
    ): Either<String, UploadGroupFile.GroupFile> {
        val result = this._hasResult<UploadGroupFile>(
            APIEndpoint.File.UploadGroupFile,
            UploadGroupFileAPI(groupId, fileUri, fileName, parentFolderId).toJson()
        )
        return if (result.status == ApiStatus.OK) result.data!!.right() else result.message!!.left()
    }

    /**
     * 上传群聊文件
     * 但是使用[Resource]对象
     */
    @JvmBlocking
    public suspend fun uploadGroupFile(
        groupId: Long,
        resource: Resource,
        fileName: String,
        parentFolderId: String,
    ): Either<String, UploadGroupFile.GroupFile> =
        this.uploadGroupFile(groupId, resource.toString(), fileName, parentFolderId)

    /**
     * 上传私聊文件
     */
    @JvmBlocking
    public suspend fun uploadPrivateFile(
        userId: Long,
        fileUri: String,
        fileName: String,
    ): Either<String, UploadPrivateFile.PrivateFile> {
        val result = this._hasResult<UploadPrivateFile>(
            APIEndpoint.File.UploadPrivateFile,
            UploadPrivateFileAPI(userId, fileUri, fileName).toJson()
        )
        return if (result.status == ApiStatus.OK) result.data!!.right() else result.message!!.left()
    }

    /**
     * 上传私聊文件
     * 但是使用[Resource]对象
     */
    @JvmBlocking
    public suspend fun uploadPrivateFile(
        userId: Long,
        resource: Resource,
        fileName: String,
    ): Either<String, UploadPrivateFile.PrivateFile> =
        this.uploadPrivateFile(userId, resource.toString(), fileName)

    /**
     * 获取合并转发消息
     */
    @JvmBlocking
    public suspend fun getForwardedMessages(forwardId: String): Either<String, GetForwardedMessages.ForwardedMessages> {
        val result = this._hasResult<GetForwardedMessages>(
            APIEndpoint.Message.GetForwardedMessages,
            GetForwardedMessagesAPI(forwardId).toJson()
        )
        return if (result.status == ApiStatus.OK) result.data!!.right() else result.message!!.left()
    }

    /**
     * 获取群历史消息
     */
    @JvmBlocking
    public suspend fun getHistoryGroupMessage(
        groupId: Long,
        startMessageSeq: Long? = null,
        limit: Int = 20,
    ): Either<String, GetHistoryGroupMessage.GroupHistoryMessage> {
        val result = this._hasResult<GetHistoryGroupMessage>(
            APIEndpoint.Message.GetHistoryGroupMessage,
            GetHistoryGroupMessageAPI(groupId, startMessageSeq, limit).toJson()
        )
        return if (result.status == ApiStatus.OK) result.data!!.right() else result.message!!.left()
    }

    /**
     * 获取私聊历史消息
     */
    @JvmBlocking
    public suspend fun getHistoryPrivateMessage(
        userId: Long,
        startMessageSeq: Long? = null,
        limit: Int = 20,
    ): Either<String, GetHistoryPrivateMessage.PrivateHistoryMessage> {
        val result = this._hasResult<GetHistoryPrivateMessage>(
            APIEndpoint.Message.GetHistoryPrivateMessage,
            GetHistoryPrivateMessageAPI(userId, startMessageSeq, limit).toJson()
        )
        return if (result.status == ApiStatus.OK) result.data!!.right() else result.message!!.left()
    }

    /**
     * 获取消息
     */
    @JvmBlocking
    public suspend fun getMessage(scene: MessageScene, peerId: Long, messageSeq: Long): Either<String, Message> {
        val result = this._hasResult<GetMessage>(
            APIEndpoint.Message.GetMessage,
            GetMessageAPI(scene, peerId, messageSeq).toJson()
        )
        return if (result.status == ApiStatus.OK) result.data!!.message.right() else result.message!!.left()
    }

    /**
     * 获取资源临时下载链接
     */
    @JvmBlocking
    public suspend fun getResourceTempUrl(resourceId: String): Either<String, GetResourceTempUrl.TempResourceUrl> {
        val result = this._hasResult<GetResourceTempUrl>(
            APIEndpoint.Message.GetResourceTempUrl,
            GetResourceTempUrlAPI(resourceId).toJson()
        )
        return if (result.status == ApiStatus.OK) result.data!!.right() else result.message!!.left()
    }

    /**
     * 撤回消息
     */
    @Suppress("Deprecation")
    @Deprecated(
        level = DeprecationLevel.HIDDEN,
        message = "Implementation has seperated group and private message recall apis"
    )
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
        message: MessageChain,
    ): Either<String, SendMessageResponse.SendMessage> {
        val result = this._hasResult<SendMessageResponse>(
            APIEndpoint.Message.SendGroupMessage,
            SendGroupMessageAPI(groupId, message.messageList).toJson()
        )
        return if (result.status == ApiStatus.OK) result.data!!.right() else result.message!!.left()
    }

    /**
     * 发送群聊消息
     * 但是发送纯文本
     */
    @JvmBlocking
    public suspend fun sendGroupMessage(groupId: Long, message: Any): Either<String, SendMessageResponse.SendMessage> {
        val chain = message { text(message) }
        return this.sendGroupMessage(groupId, chain)
    }

    /**
     * 发送私聊消息
     */
    @JvmBlocking
    public suspend fun sendPrivateMessage(
        userId: Long,
        message: MessageChain,
    ): Either<String, SendMessageResponse.SendMessage> {
        val result = this._hasResult<SendMessageResponse>(
            APIEndpoint.Message.SendPrivateMessage,
            SendPrivateMessageAPI(userId, message.messageList).toJson()
        )
        return if (result.status == ApiStatus.OK) result.data!!.right() else result.message!!.left()
    }

    /**
     * 发送私聊消息
     * 但是发送纯文本
     */
    @JvmBlocking
    public suspend fun sendPrivateMessage(userId: Long, message: Any): Either<String, SendMessageResponse.SendMessage> {
        val chain = message { text(message) }
        return this.sendPrivateMessage(userId, chain)
    }

    // New system api

    /**
     * 获取协议端信息
     */
    @JvmBlocking
    public suspend fun getImplInfo(): Either<String, GetImplInfo.ImplInfo> {
        val result = this._hasResult<GetImplInfo>(APIEndpoint.System.GetImplInfo)
        return if (result.status == ApiStatus.OK) result.data!!.right() else result.message!!.left()
    }

    /**
     * 获取某个域名的Cookie
     */
    @JvmBlocking
    public suspend fun getCookies(domain: String): Either<String, GetCookies.Cookies> {
        val result = this._hasResult<GetCookies>(APIEndpoint.System.GetCookies)
        return if (result.status == ApiStatus.OK) result.data!!.right() else result.message!!.left()
    }

    /**
     * 获取用户个人信息
     */
    @JvmBlocking
    public suspend fun getUserProfile(userId: Long): Either<String, GetUserProfile.UserProfile> {
        val result = this._hasResult<GetUserProfile>(
            APIEndpoint.System.GetUserProfile,
            GetUserProfileAPI(userId).toJson()
        )
        return if (result.status == ApiStatus.OK) result.data!!.right() else result.message!!.left()
    }

    /**
     * 获取csrf token
     */
    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Implementation is not implemented this api")
    @JvmBlocking
    public suspend fun getCSRFToken(): Either<String, GetCSRFToken.CSRFToken> {
        val result = this._hasResult<GetCSRFToken>(APIEndpoint.System.GetCSRFToken)
        return if (result.status == ApiStatus.OK) result.data!!.right() else result.message!!.left()
    }
    // Done new system api

    // New message api

    /**
     * 撤回群聊消息
     */
    @JvmAsync
    @JvmBlocking
    public suspend fun recallGroupMessage(groupId: Long, messageSeq: Long) {
        this._noResult(
            APIEndpoint.Message.RecallGroupMessage,
            RecallGroupMessageAPI(groupId, messageSeq).toJson()
        )
    }

    /**
     * 撤回私聊消息
     */
    @JvmAsync
    @JvmBlocking
    public suspend fun recallPrivateMessage(userId: Long, messageSeq: Long) {
        this._noResult(
            APIEndpoint.Message.RecallPrivateMessage,
            RecallPrivateMessageAPI(userId, messageSeq).toJson()
        )
    }

    /**
     * 将消息标记为已读
     */
    @JvmAsync
    @JvmBlocking
    public suspend fun markMessageAsRead(messageScene: MessageScene, peerId: Long, messageSeq: Long) {
        this._noResult(
            APIEndpoint.Message.MarkMessageAsRead,
            MarkMessageAsReadAPI(messageScene, peerId, messageSeq).toJson()
        )
    }

    // Done new message api

    // New request api

    /**
     * 获取好友请求列表
     * @param limit 获取的最大请求数量
     * @param isFiltered `true` 表示只获取被过滤（由风险账号发起）的通知，`false` 表示只获取未被过滤的通知
     */
    @JvmBlocking
    public suspend fun getFriendRequests(
        limit: Int = 20,
        isFiltered: Boolean = false,
    ): Either<String, List<RawFriendRequestEvent.FriendRequest>> {
        val result = this._hasResult<GetFriendRequests>(
            APIEndpoint.Friend.GetFriendRequests,
            GetFriendRequestsAPI(limit, isFiltered).toJson()
        )
        return if (result.status == ApiStatus.OK) result.data!!.right() else result.message!!.left()
    }

    /**
     * 同意好友请求
     */
    @JvmAsync
    @JvmBlocking
    public suspend fun acceptFriendRequest(initiatorUID: String, isFiltered: Boolean = false) {
        this._noResult(
            APIEndpoint.Request.AcceptFriendRequest,
            AcceptFriendRequestAPI(initiatorUID, isFiltered).toJson()
        )
    }

    /**
     * 拒绝好友请求
     */
    @JvmAsync
    @JvmBlocking
    public suspend fun rejectFriendRequest(initiatorUID: String, isFiltered: Boolean = false, reason: String? = null) {
        this._noResult(
            APIEndpoint.Request.RejectedFriendRequest,
            RejectFriendRequestAPI(initiatorUID, isFiltered, reason).toJson()
        )
    }

    // Done request api

    // New group api

    /**
     * 获取群精华消息列表
     * @param pageSize 每页包含的精华消息数量
     * @param pageIndex 页码索引，从 0 开始
     */
    @JvmBlocking
    public suspend fun getGroupEssenceMessages(
        groupId: Long,
        pageSize: Int,
        pageIndex: Int = 0,
    ): Either<String, GetGroupEssenceMessages.GroupEssenceMessages> {
        val result = this._hasResult<GetGroupEssenceMessages>(
            APIEndpoint.Group.GetGroupEssenceMessages,
            GetGroupEssenceMessagesAPI(groupId, pageIndex, pageSize).toJson()
        )
        return if (result.status == ApiStatus.OK) result.data!!.right() else result.message!!.left()
    }

    /**
     * 设置群精华消息
     * @param messageSeq 消息序列号
     * @param isSet 是否设置为精华消息，`false` 表示取消精华
     */
    @JvmAsync
    @JvmBlocking
    public suspend fun setGroupEssenceMessage(groupId: Long, messageSeq: Long, isSet: Boolean = true) {
        this._noResult(
            APIEndpoint.Group.SetGroupEssenceMessage,
            SetGroupEssenceMessageAPI(groupId, messageSeq, isSet).toJson()
        )
    }

    /**
     * 获取群通知列表
     * @param startNotificationSeq 起始通知序列号
     * @param isFiltered `true` 表示只获取被过滤（由风险账号发起）的通知，`false` 表示只获取未被过滤的通知
     * @param limit 获取的最大通知数量
     */
    @JvmBlocking
    public suspend fun getGroupNotifications(
        startNotificationSeq: Long? = null,
        isFiltered: Boolean = false,
        limit: Int = 20,
    ): Either<String, GetGroupNotifications.GroupNotifications> {
        val result = this._hasResult<GetGroupNotifications>(
            APIEndpoint.Group.GetGroupNotifications,
            GetGroupNotificationsAPI(startNotificationSeq, isFiltered, limit).toJson()
        )
        return if (result.status == ApiStatus.OK) result.data!!.right() else result.message!!.left()
    }

    /**
     * 同意入群/邀请他人入群请求
     * @param groupId 群号
     * @param notificationSeq 请求对应的通知序列号
     * @param notificationType 请求对应的通知类型
     * @param isFiltered 是否是被过滤的请求
     */
    @JvmAsync
    @JvmBlocking
    public suspend fun acceptGroupRequest(
        groupId: Long,
        notificationSeq: Long,
        notificationType: NotificationType,
        isFiltered: Boolean = false,
    ) {
        this._noResult(
            APIEndpoint.Group.AcceptGroupRequest,
            AcceptGroupRequestAPI(notificationSeq, notificationType, groupId, isFiltered).toJson()
        )
    }

    /**
     * 拒绝入群/邀请他人入群请求
     * @param groupId 群号
     * @param notificationSeq 请求对应的通知序列号
     * @param notificationType 请求对应的通知类型
     * @param isFiltered 是否是被过滤的请求
     * @param reason 拒绝理由
     */
    @JvmAsync
    @JvmBlocking
    public suspend fun rejectGroupRequest(
        groupId: Long,
        notificationSeq: Long,
        notificationType: NotificationType,
        isFiltered: Boolean = false,
        reason: String? = null,
    ) {
        this._noResult(
            APIEndpoint.Group.RejectGroupRequest,
            RejectGroupRequestAPI(notificationSeq, notificationType, groupId, isFiltered, reason).toJson()
        )
    }

    /**
     * 同意他人邀请自身入群
     * @param groupId 群号
     * @param invitationSeq 邀请序列号
     */
    @JvmAsync
    @JvmBlocking
    public suspend fun acceptGroupInvitation(groupId: Long, invitationSeq: Long) {
        this._noResult(
            APIEndpoint.Group.AcceptGroupInvitation,
            AcceptGroupInvitationAPI(groupId, invitationSeq).toJson()
        )
    }

    /**
     * 拒绝他人邀请自身入群
     * @param groupId 群号
     * @param invitationSeq 邀请序列号
     */
    @JvmAsync
    @JvmBlocking
    public suspend fun rejectGroupInvitation(groupId: Long, invitationSeq: Long, reason: String? = null) {
        this._noResult(
            APIEndpoint.Group.RejectGroupInvitation,
            RejectGroupInvitationAPI(groupId, invitationSeq, reason).toJson()
        )
    }

    // Done group api
}