/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/26
 */


package cn.rtast.rob.util.ob

import cn.rtast.rob.ROneBotFactory
import cn.rtast.rob.ROneBotFactory.isServer
import cn.rtast.rob.ROneBotFactory.websocket
import cn.rtast.rob.ROneBotFactory.websocketServer
import cn.rtast.rob.entity.*
import cn.rtast.rob.entity.lagrange.*
import cn.rtast.rob.entity.metadata.OneBotVersionInfo
import cn.rtast.rob.entity.out.*
import cn.rtast.rob.entity.out.lagrange.*
import cn.rtast.rob.entity.out.lagrange.FriendPokeOut
import cn.rtast.rob.entity.out.lagrange.GroupPokeOut
import cn.rtast.rob.entity.out.lagrange.SendPrivateForwardMsgOut
import cn.rtast.rob.enums.internal.MessageEchoType
import cn.rtast.rob.util.fromJson
import cn.rtast.rob.util.toJson
import kotlinx.coroutines.CompletableDeferred


/**
 * 向OneBot实现发送各种API, 在这个接口中没有返回值的接口
 * 全部为异步调用(async), 有返回值但是返回值可有可无的接口可以选择
 * 同步调用(await)或者异步调用(async), 返回值必须使用的接口
 * 全部为同步调用(await)
 */
interface OneBotAction {

    /**
     * 向服务器发送一个数据包, 数据包的类型任意
     * 但是Gson会将这个数据类使用反射来序列化成对应的json字符串
     */
    private fun send(message: Any) {
        if (isServer) {
            websocketServer?.connections?.forEach { it.send(message.toJson()) }
            return
        }
        websocket?.send(message.toJson())
    }

    /**
     * 创建一个CompletableDeferred<T>对象使异步操作变为同步操作
     * 如果OneBot实现和ROneBot实例在同一局域网或延迟低的情况下
     * 此操作接近于无感, 如果延迟较大则会阻塞消息处理线程, 但是
     * 每条消息处理都开了一个线程~
     */
    private fun <T : MessageEchoType> createCompletableDeferred(echo: T): CompletableDeferred<String> {
        val deferred = CompletableDeferred<String>()
        MessageHandler.suspendedRequests[echo] = deferred
        return deferred
    }

    /**
     * 向所有群聊中发送MessageChain消息链消息
     * 所有群聊指ROneBotFactory中设置的监听群号
     * 如果没有设置则此方法以及重载方法将毫无作用
     */
    suspend fun broadcastMessageListening(content: MessageChain) {
        ROneBotFactory.getListeningGroups().forEach {
            this.sendGroupMessage(it, content)
        }
    }

    /**
     * 向所有监听的群聊发送一条纯文本消息
     */
    suspend fun broadcastMessageListening(content: String) {
        ROneBotFactory.getListeningGroups().forEach {
            this.sendGroupMessage(it, content)
        }
    }

    /**
     * 向所有监听的群聊发送一条CQMessageChain消息
     */
    suspend fun broadcastMessageListening(content: CQMessageChain) {
        ROneBotFactory.getListeningGroups().forEach {
            this.sendGroupMessage(it, content)
        }
    }

    /**
     * 向所有群发送一条数组消息链消息
     * 该方法会向`所有群(所有已加入的群聊)`发送消息
     * 使用之前请慎重考虑
     */
    suspend fun broadcastMessage(content: MessageChain) {
        this.getGroupList().map { it.groupId }.forEach {
            this.sendGroupMessage(it, content)
        }
    }

    /**
     * 向所有群发送一条纯文本消息
     * 该方法会向`所有群(所有已加入的群聊)`发送消息
     * 使用之前请慎重考虑
     */
    suspend fun broadcastMessage(content: String) {
        this.getGroupList().map { it.groupId }.forEach {
            this.sendGroupMessage(it, content)
        }
    }

    /**
     * 向所有群发送一条CQ码消息链消息
     * 该方法会向`所有群(所有已加入的群聊)`发送消息
     * 使用之前请慎重考虑
     */
    suspend fun broadcastMessage(content: CQMessageChain) {
        this.getGroupList().map { it.groupId }.forEach {
            this.sendGroupMessage(it, content)
        }
    }

    /**
     * 向一个群聊中发送一段纯文本消息
     */
    suspend fun sendGroupMessage(groupId: Long, content: String) {
        this.send(CQCodeGroupMessageOut(params = CQCodeGroupMessageOut.Params(groupId, content)))
    }

    /**
     * 发送群组消息但是是CQ码消息链
     */
    suspend fun sendGroupMessage(groupId: Long, content: CQMessageChain) {
        this.sendGroupMessage(groupId, content.finalString)
    }

    /**
     * 发送群组消息但是是MessageChain消息链
     */
    suspend fun sendGroupMessage(groupId: Long, content: MessageChain) {
        this.send(ArrayGroupMessageOut(params = ArrayGroupMessageOut.Params(groupId, content.finalArrayMsgList)))
    }

    /**
     * 发送群组消息但是是服务器返回的消息类型
     */
    suspend fun sendGroupMessage(groupId: Long, content: List<ArrayMessage>) {
        this.send(RawArrayGroupMessageOut(params = RawArrayGroupMessageOut.Params(groupId, content)))
    }

    /**
     * 发送私聊消息但是是纯文本
     */
    suspend fun sendPrivateMessage(userId: Long, content: String) {
        this.send(CQCodePrivateMessageOut(params = CQCodePrivateMessageOut.Params(userId, content)))
    }

    /**
     * 发送私聊消息但是是CQ码消息链
     */
    suspend fun sendPrivateMessage(userId: Long, content: CQMessageChain) {
        this.sendPrivateMessage(userId, content.finalString)
    }

    /**
     * 发送私聊消息但是是MessageChain消息链
     */
    suspend fun sendPrivateMessage(userId: Long, content: MessageChain) {
        this.send(ArrayPrivateMessageOut(params = ArrayPrivateMessageOut.Params(userId, content.finalArrayMsgList)))
    }

    /**
     * 发送私聊消息但是是服务器返回的消息类型
     */
    suspend fun sendPrivateMessage(userId: Long, content: List<ArrayMessage>) {
        this.send(RawArrayPrivateMessageOut(params = RawArrayPrivateMessageOut.Params(userId, content)))
    }

    /**
     * 撤回消息(recall/revoke)
     */
    suspend fun revokeMessage(messageId: Long) {
        this.send(RevokeMessageOut(params = RevokeMessageOut.Params(messageId)))
    }

    /**
     * 为某人的卡片点赞
     */
    suspend fun sendLike(userId: Long, times: Int = 1) {
        this.send(SendLikeOut(params = SendLikeOut.Params(userId, times)))
    }

    /**
     * 将成员踢出群聊
     */
    suspend fun kickGroupMember(groupId: Long, userId: Long, rejectJoinRequest: Boolean = false) {
        this.send(KickGroupMemberOut(params = KickGroupMemberOut.Params(groupId, userId, rejectJoinRequest)))
    }

    /**
     * 设置单个成员的禁言
     */
    suspend fun setGroupBan(groupId: Long, userId: Long, duration: Int = 1800) {
        this.send(SetGroupBanOut(params = SetGroupBanOut.Params(groupId, userId, duration)))
    }

    /**
     * 设置全员禁言
     */
    suspend fun setGroupWholeBan(groupId: Long, enable: Boolean = true) {
        this.send(SetGroupWholeBanOut(params = SetGroupWholeBanOut.Params(groupId, enable)))
    }

    /**
     * 设置群组管理员
     */
    suspend fun setGroupAdmin(groupId: Long, userId: Long, enable: Boolean = true) {
        this.send(SetGroupAdminOut(params = SetGroupAdminOut.Params(groupId, userId, enable)))
    }

    /**
     * 设置是否可以匿名聊天
     */
    suspend fun setGroupAnonymous(groupId: Long, enable: Boolean = true) {
        this.send(SetGroupAnonymousOut(params = SetGroupAnonymousOut.Params(groupId, enable)))
    }

    /**
     * 设置成群员的群昵称
     */
    suspend fun setGroupMemberCard(groupId: Long, userId: Long, card: String = "") {
        this.send(SetGroupMemberCardOut(params = SetGroupMemberCardOut.Params(groupId, userId, card)))
    }

    /**
     * 设置群组名称
     */
    suspend fun setGroupName(groupId: Long, groupName: String) {
        this.send(SetGroupNameOut(params = SetGroupNameOut.Params(groupId, groupName)))
    }

    /**
     * 退出群聊,如果是群主并且dismiss为true则解散群聊
     */
    suspend fun setGroupLeaveOrDismiss(groupId: Long, dismiss: Boolean = false) {
        this.send(SetGroupLeaveOut(params = SetGroupLeaveOut.Params(groupId, dismiss)))
    }

    /**
     * 处理加好友请求
     */
    suspend fun setFriendRequest(flag: String, approve: Boolean = true, remark: String = "") {
        this.send(SetFriendRequestOut(params = SetFriendRequestOut.Params(flag, approve, remark)))
    }

    /**
     * 处理加群请求
     */
    suspend fun setGroupRequest(
        flag: String,
        type: String,
        approve: Boolean = true,
        reason: String = ""  // only reject user to join group need to provide this param
    ) {
        this.send(SetGroupRequestOut(params = SetGroupRequestOut.Params(flag, type, approve, reason)))
    }

    /**
     * 根据消息ID获取一条消息
     */
    suspend fun getMessage(messageId: Long): GetMessage.Data {
        val deferred = this.createCompletableDeferred(MessageEchoType.GetMessage)
        this.send(GetMessageOut(params = GetMessageOut.Params(messageId)))
        val response = deferred.await()
        return response.fromJson<GetMessage>().data
    }

    /**
     * 获取账号登录信息
     */
    suspend fun getLoginInfo(): LoginInfo.Data {
        val deferred = this.createCompletableDeferred(MessageEchoType.GetLoginInfo)
        this.send(GetLoginInfoOut())
        val response = deferred.await()
        return response.fromJson<LoginInfo>().data
    }

    /**
     * 获取陌生人信息
     */
    suspend fun getStrangerInfo(userId: Long, noCache: Boolean = false): StrangerInfo.Data {
        val deferred = this.createCompletableDeferred(MessageEchoType.GetStrangerInfo)
        this.send(GetStrangerInfoOut(params = GetStrangerInfoOut.Params(userId, noCache)))
        val response = deferred.await()
        return response.fromJson<StrangerInfo>().data
    }

    /**
     * 获取好友列表
     */
    suspend fun getFriendList(): List<FriendList.Data> {
        val deferred = this.createCompletableDeferred(MessageEchoType.GetFriendList)
        this.send(GetFriendListOut())
        val response = deferred.await()
        return response.fromJson<FriendList>().data
    }

    /**
     * 获取群组信息
     */
    suspend fun getGroupInfo(groupId: Long, noCache: Boolean = false): GroupInfo.Data {
        val deferred = this.createCompletableDeferred(MessageEchoType.GetGroupInfo)
        this.send(GetGroupInfoOut(params = GetGroupInfoOut.Params(groupId, noCache)))
        val response = deferred.await()
        return response.fromJson<GroupInfo>().data
    }

    /**
     * 获取账号的群组列表
     */
    suspend fun getGroupList(): List<GroupList.Data> {
        val deferred = this.createCompletableDeferred(MessageEchoType.GetGroupList)
        this.send(GetGroupListOut())
        val response = deferred.await()
        return response.fromJson<GroupList>().data
    }

    /**
     * 获取群组成员信息
     */
    suspend fun getGroupMemberInfo(groupId: Long, userId: Long, noCache: Boolean = false): GroupMemberList.Data {
        val deferred = this.createCompletableDeferred(MessageEchoType.GetGroupMemberInfo)
        this.send(GetGroupMemberInfoOut(params = GetGroupMemberInfoOut.Params(groupId, userId, noCache)))
        val response = deferred.await()
        return response.fromJson<GroupMemberInfo>().data
    }

    /**
     * 获取群组成员列表
     */
    suspend fun getGroupMemberList(groupId: Long): List<GroupMemberList.Data> {
        val deferred = this.createCompletableDeferred(MessageEchoType.GetGroupMemberList)
        this.send(GetGroupMemberListOut(params = GetGroupMemberListOut.Params(groupId)))
        val response = deferred.await()
        return response.fromJson<GroupMemberList>().data
    }

    /**
     * 获取OneBot实现的版本信息
     */
    suspend fun getVersionInfo(): OneBotVersionInfo.Data {
        val deferred = this.createCompletableDeferred(MessageEchoType.GetVersionInfo)
        this.send(GetVersionInfo())
        val response = deferred.await()
        return response.fromJson<OneBotVersionInfo>().data
    }

    /**
     * 检查是否可以发送图片
     */
    suspend fun canSendImage(): Boolean {
        val deferred = this.createCompletableDeferred(MessageEchoType.CanSendImage)
        this.send(CanSendImageOut())
        val response = deferred.await()
        return response.fromJson<CanSend>().data.yes
    }

    /**
     * 检查是否可以发送语音
     * (感觉没什么用)
     */
    suspend fun canSendRecord(): Boolean {
        val deferred = this.createCompletableDeferred(MessageEchoType.CanSendRecord)
        this.send(CanSendRecordOut())
        val response = deferred.await()
        return response.fromJson<CanSend>().data.yes
    }

    /**
     * 该方法是Lagrange.OneBot的拓展API
     * 用于获取收藏表情
     * 返回一个List<String> String为URL
     */
    suspend fun fetchCustomFace(): List<String> {
        val deferred = this.createCompletableDeferred(MessageEchoType.FetchCustomFace)
        this.send(FetchCustomFaceOut())
        val response = deferred.await()
        return response.fromJson<CustomFace>().data
    }

    /**
     * 该方法是Lagrange.OneBot的拓展API
     * 用于发送群聊中的合并转发消息链
     * 该方法有返回值返回forwardId
     */
    suspend fun sendGroupForwardMsg(groupId: Long, message: NodeMessageChain): ForwardMessageId.Data {
        val deferred = this.createCompletableDeferred(MessageEchoType.SendForwardMsg)
        this.send(SendGroupForwardMsgOut(params = SendGroupForwardMsgOut.Params(groupId, message.nodes)))
        val response = deferred.await()
        return response.fromJson<ForwardMessageId>().data
    }

    /**
     * 该方法是Lagrange.OneBot的拓展API
     * 用于发送群聊中的合并转发消息链
     * 但是使用异步的方式发送不会有返回值
     */
    suspend fun sendGroupForwardMsgAsync(groupId: Long, message: NodeMessageChain) {
        this.send(SendGroupForwardMsgOut(params = SendGroupForwardMsgOut.Params(groupId, message.nodes)))
    }

    /**
     * 该方法是Lagrange.OneBot的拓展API
     * 用于发送私聊的合并转发消息链
     * 该方法有返回值返回forwardId
     */
    suspend fun sendPrivateForwardMsg(userId: Long, message: NodeMessageChain): ForwardMessageId.Data {
        val deferred = this.createCompletableDeferred(MessageEchoType.SendForwardMsg)
        this.send(SendPrivateForwardMsgOut(params = SendPrivateForwardMsgOut.Params(userId, message.nodes)))
        val response = deferred.await()
        return response.fromJson<ForwardMessageId>().data
    }

    /**
     * 该方法是Lagrange.OneBot的拓展API
     * 用于发送私聊的合并转发消息链
     * 该方法使用异步的方式发送不会有返回值
     */
    suspend fun sendPrivateForwardMsgAsync(userId: Long, message: NodeMessageChain) {
        this.send(SendPrivateForwardMsgOut(params = SendPrivateForwardMsgOut.Params(userId, message.nodes)))
    }

    /**
     * 该方法是Lagrange.OneBot的拓展API
     * 用于发送私聊的戳一戳行为
     */
    suspend fun sendFriendPoke(userId: Long) {
        this.send(FriendPokeOut(params = FriendPokeOut.Params(userId)))
    }

    /**
     * 该方法是Lagrange.OneBot的拓展API
     * 用于发送群聊的戳一戳行为
     */
    suspend fun sendGroupPoke(groupId: Long, userId: Long) {
        this.send(GroupPokeOut(params = GroupPokeOut.Params(groupId, userId)))
    }

    /**
     * 该方法是Lagrange.OneBot的拓展API
     * 用于上传群文件
     */
    suspend fun uploadGroupFile(groupId: Long, path: String, name: String, folder: String = "/") {
        this.send(UploadGroupFileOut(params = UploadGroupFileOut.Params(groupId, path, name, folder)))
    }

    /**
     * 该方法是Lagrange.OneBot的拓展API
     * 用于在私聊中发送文件
     */
    suspend fun uploadPrivateFile(userId: Long, path: String, name: String) {
        this.send(UploadPrivateFileOut(params = UploadPrivateFileOut.Params(userId, path, name)))
    }

    /**
     * 该方法是Lagrange.OneBot的拓展API
     * 用于在获取群文件目录列表
     */
    suspend fun getGroupRootFiles(groupId: Long): GetGroupRootFiles.Data {
        val deferred = this.createCompletableDeferred(MessageEchoType.GetGroupRootFiles)
        this.send(GetGroupRootFilesOut(params = GetGroupRootFilesOut.Params(groupId)))
        val response = deferred.await()
        return response.fromJson<GetGroupRootFiles>().data
    }

    /**
     * 该方法是Lagrange.OneBot的拓展API
     * 用于在获取群文件中的子目录中的文件列表
     */
    suspend fun getGroupFilesByFolder(groupId: Long, folderId: String): GetGroupRootFiles.Data {
        val deferred = this.createCompletableDeferred(MessageEchoType.GetGroupFilesByFolder)
        this.send(GetGroupFilesByFolderOut(params = GetGroupFilesByFolderOut.Params(groupId, folderId)))
        val response = deferred.await()
        return response.fromJson<GetGroupRootFiles>().data
    }

    /**
     * 该方法是Lagrange.OneBot的拓展API
     * 用于在获取某个群文件的URL地址
     */
    suspend fun getGroupFileUrl(groupId: Long, fileId: String, busid: Int): GetGroupFileUrl.Data {
        val deferred = this.createCompletableDeferred(MessageEchoType.GetGroupFileUrl)
        this.send(GetGroupFileUrlOut(params = GetGroupFileUrlOut.Params(groupId, fileId, busid)))
        val response = deferred.await()
        return response.fromJson<GetGroupFileUrl>().data
    }

    /**
     * 该方法是Lagrange.OneBot的拓展API
     * 用于设置群组成员专属头衔
     */
    suspend fun setGroupMemberTitle(groupId: Long, userId: Long, title: String = "", duration: Int = -1) {
        this.send(SetGroupMemberTitleOut(params = SetGroupMemberTitleOut.Params(groupId, userId, title, duration)))
    }

    /**
     * 该方法是Lagrange.OneBot的拓展API
     * 该方法被Lagrange标记为`隐藏API`
     * 并且为异步发送API不会有返回值
     */
    suspend fun releaseGroupNoticeAsync(groupId: Long, content: String, image: String = "") {
        this.send(ReleaseGroupNoticeOut(params = ReleaseGroupNoticeOut.Params(groupId, content, image)))
    }

    /**
     * 该方法是Lagrange.OneBot的拓展API
     * 该方法被Lagrange标记为`隐藏API`
     * 用于设置一条群公告, 但是[image]参数并不需要传入
     * 如果传入会导致发送失败, 截至: 24/10/01: 15:11
     * 返回一个String类型的公告ID
     */
    suspend fun releaseGroupNotice(groupId: Long, content: String, image: String = ""): String {
        val deferred = this.createCompletableDeferred(MessageEchoType.ReleaseGroupNotice)
        this.send(ReleaseGroupNoticeOut(params = ReleaseGroupNoticeOut.Params(groupId, content, image)))
        val response = deferred.await()
        return response.fromJson<ReleaseGroupNotice>().data
    }

    /**
     * 该方法是Lagrange.OneBot的拓展API
     * 用于获取所有的群公告
     */
    suspend fun getAllGroupNotices(groupId: Long): List<GroupNotice.Data> {
        val deferred = this.createCompletableDeferred(MessageEchoType.GetGroupNotice)
        this.send(GetGroupNoticeOut(params = GetGroupNoticeOut.Params(groupId)))
        val response = deferred.await()
        return response.fromJson<GroupNotice>().data
    }

    /**
     * 该方法是Lagrange.OneBot的拓展API
     * 用于获取指定的群公告ID的内容
     */
    suspend fun getGroupNoticeById(groupId: Long, noticeId: String): GroupNotice.Data? {
        return this.getAllGroupNotices(groupId).find { it.noticeId == noticeId }
    }

    /**
     * 该方法是Lagrange.OneBot的拓展API
     * 用于删除指定ID的群公告, 无返回值
     */
    suspend fun deleteGroupNotice(groupId: Long, noticeId: String) {
        val msg = DeleteGroupNoticeOut(params = DeleteGroupNoticeOut.Params(groupId, noticeId))
        this.send(msg)
    }

    /**
     * 该方法是Lagrange.OneBot的拓展API
     * 用于使用一个表情(提供一个表情ID)回应某个消息
     * 需要提供message_id, isAdd参数如果为false则表示
     * 取消对这条消息的reaction
     */
    suspend fun reaction(groupId: Long, messageId: Long, code: String, isAdd: Boolean = true) {
        this.send(ReactionOut(params = ReactionOut.Params(groupId, messageId, code, isAdd)))
    }

    /**
     * 该方法是Lagrange.OneBot的拓展API
     * 用于获取群内的精华消息
     */
    suspend fun getEssenceMessageList(groupId: Long): List<EssenceMessageList.Data> {
        val deferred = this.createCompletableDeferred(MessageEchoType.GetEssenceMessageList)
        this.send(GetEssenceMessageListOut(params = GetEssenceMessageListOut.Params(groupId)))
        val response = deferred.await()
        return response.fromJson<EssenceMessageList>().data
    }

    /**
     * 该方法是Lagrange.OneBot的拓展API
     * 用于设置群精华消息
     */
    suspend fun setEssenceMessage(messageId: Long) {
        this.send(SetEssenceMessageOut(params = SetEssenceMessageOut.Params(messageId)))
    }

    /**
     * 该方法是Lagrange.OneBot的拓展API
     * 用于删除群精华消息
     */
    suspend fun deleteEssenceMessage(messageId: Long) {
        this.send(DeleteEssenceMessageOut(params = DeleteEssenceMessageOut.Params(messageId)))
    }

    /**
     * 该方法是Lagrange.OneBot的拓展API
     * 用于设置某个消息为已读, 就是让消息列表的红点消失
     */
    suspend fun markAsRead(messageId: Long) {
        this.send(MarkAsReadOut(params = MarkAsReadOut.Params(messageId)))
    }
}