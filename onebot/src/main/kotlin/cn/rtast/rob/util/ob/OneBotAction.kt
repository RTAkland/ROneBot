/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/26
 */

@file:Suppress("unused")

package cn.rtast.rob.util.ob

import cn.rtast.rob.BotInstance
import cn.rtast.rob.SendAction
import cn.rtast.rob.util.fromJson
import cn.rtast.rob.util.toJson
import cn.rtast.rob.entity.*
import cn.rtast.rob.entity.lagrange.*
import cn.rtast.rob.entity.metadata.HeartBeatEvent
import cn.rtast.rob.entity.metadata.OneBotVersionInfo
import cn.rtast.rob.entity.out.CallAPIOut
import cn.rtast.rob.entity.out.get.*
import cn.rtast.rob.entity.out.get.CanSendImageOut
import cn.rtast.rob.entity.out.get.GetFriendListOut
import cn.rtast.rob.entity.out.get.GetGroupMemberInfoOut
import cn.rtast.rob.entity.out.get.GetGroupMemberListOut
import cn.rtast.rob.entity.out.get.GetLoginInfoOut
import cn.rtast.rob.entity.out.gocq.CreateGroupFileFolderOut
import cn.rtast.rob.entity.out.gocq.DeleteFriendOut
import cn.rtast.rob.entity.out.gocq.DeleteGroupFolderOut
import cn.rtast.rob.entity.out.gocq.GetGroupAtAllRemainOut
import cn.rtast.rob.entity.out.gocq.GetGroupFileSystemInfoOut
import cn.rtast.rob.entity.out.gocq.GroupAtAllRemain
import cn.rtast.rob.entity.out.gocq.GroupFileSystemInfo
import cn.rtast.rob.entity.out.gocq.OCRImage
import cn.rtast.rob.entity.out.gocq.OCRImageOut
import cn.rtast.rob.entity.out.lagrange.get.*
import cn.rtast.rob.entity.out.lagrange.get.GetCSRFTokenOut
import cn.rtast.rob.entity.out.lagrange.get.GetGroupFileUrlOut
import cn.rtast.rob.entity.out.lagrange.get.GetGroupFilesByFolderOut
import cn.rtast.rob.entity.out.lagrange.get.GetGroupHonorInfoOut
import cn.rtast.rob.entity.out.lagrange.get.GetGroupRootFilesOut
import cn.rtast.rob.entity.out.lagrange.set.*
import cn.rtast.rob.entity.out.lagrange.set.DeleteEssenceMessageOut
import cn.rtast.rob.entity.out.lagrange.set.FriendPokeOut
import cn.rtast.rob.entity.out.lagrange.set.GetEssenceMessageListOut
import cn.rtast.rob.entity.out.lagrange.set.GroupPokeOut
import cn.rtast.rob.entity.out.lagrange.set.ReactionOut
import cn.rtast.rob.entity.out.lagrange.set.SendGroupForwardMsgOut
import cn.rtast.rob.entity.out.lagrange.set.SendPrivateForwardMsgOut
import cn.rtast.rob.entity.out.lagrange.set.SetEssenceMessageOut
import cn.rtast.rob.entity.out.lagrange.set.SetGroupMemberTitleOut
import cn.rtast.rob.entity.out.llonebot.GetFriendWithCategory
import cn.rtast.rob.entity.out.llonebot.GetFriendWithCategoryOut
import cn.rtast.rob.entity.out.llonebot.GetGroupIgnoreAddRequestOut
import cn.rtast.rob.entity.out.llonebot.GetRobotUinRangeOut
import cn.rtast.rob.entity.out.llonebot.GroupIgnoreAddRequest
import cn.rtast.rob.entity.out.llonebot.RobotUinRange
import cn.rtast.rob.entity.out.llonebot.SetOnlineStatusOut
import cn.rtast.rob.entity.out.set.*
import cn.rtast.rob.entity.out.set.KickGroupMemberOut
import cn.rtast.rob.entity.out.set.SetFriendRequestOut
import cn.rtast.rob.entity.out.set.SetGroupAdminOut
import cn.rtast.rob.entity.out.set.SetGroupBanOut
import cn.rtast.rob.entity.out.set.SetGroupLeaveOut
import cn.rtast.rob.enums.AIRecordCharacter
import cn.rtast.rob.enums.HonorType
import cn.rtast.rob.enums.OnlineStatus
import cn.rtast.rob.enums.QQFace
import cn.rtast.rob.enums.internal.ActionStatus
import cn.rtast.rob.enums.internal.InstanceType
import cn.rtast.rob.util.MessageHandler
import kotlinx.coroutines.CompletableDeferred
import java.util.UUID

/**
 * 向OneBot实现发送各种API, 在这个接口中没有返回值的接口
 * 全部为异步调用(async), 有返回值但是返回值可有可无的接口可以选择
 * 同步调用(await)或者异步调用(async), 返回值必须使用的接口
 * 全部为同步调用(await), 在发送消息类的方法中如果发送成功则返回
 * 一个长整型的消息ID, 发送失败则返回null值
 */
class OneBotAction internal constructor(
    private val botInstance: BotInstance,
    private val instanceType: InstanceType,
) : SendAction {
    private lateinit var messageHandler: MessageHandler

    override fun toString(): String {
        return "OneBotAction{\"Bytes not available to view\"}"
    }

    /**
     * 将延迟初始化的消息处理器初始化
     */
    internal fun setHandler(handler: MessageHandler) {
        this.messageHandler = handler
    }

    /**
     * 向服务器发送一个数据包, 数据包的类型任意
     * 但是Gson会将这个数据类使用反射来序列化成对应的json字符串
     */
    override suspend fun send(message: Any) = this.send(message.toJson())

    /**
     * 发送一段json字符串
     */
    override suspend fun send(message: String) {
        when (instanceType) {
            InstanceType.Client -> botInstance.websocket?.send(message)
            InstanceType.Server -> botInstance.websocketServer?.connections?.forEach { it.send(message) }
        }
    }

    /**
     * 创建一个CompletableDeferred<T>对象使异步操作变为同步操作
     * 如果OneBot实现和ROneBot实例在同一局域网或延迟低的情况下
     * 此操作接近于无感, 如果延迟较大则会阻塞消息处理线程, 但是
     * 每条消息处理都开了一个线程~
     */
    private fun createCompletableDeferred(echo: UUID): CompletableDeferred<String> {
        val deferred = CompletableDeferred<String>()
        messageHandler.suspendedRequests[echo] = deferred
        return deferred
    }

    /**
     * 向所有群聊中发送MessageChain消息链消息
     * 所有群聊指ROneBotFactory中设置的监听群号
     * 如果没有设置则此方法以及重载方法将毫无作用
     */
    suspend fun broadcastMessageListening(content: MessageChain) {
        botInstance.listenedGroups.forEach {
            this.sendGroupMessage(it, content)
        }
    }

    /**
     * 向所有监听的群聊发送一条纯文本消息
     */
    suspend fun broadcastMessageListening(content: String) {
        botInstance.listenedGroups.forEach {
            this.sendGroupMessage(it, content)
        }
    }

    /**
     * 向所有监听的群聊发送一条CQMessageChain消息
     */
    suspend fun broadcastMessageListening(content: CQMessageChain) {
        botInstance.listenedGroups.forEach {
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
    suspend fun sendGroupMessage(groupId: Long, content: String): Long? {
        val uuid = UUID.randomUUID()
        val deferred = this.createCompletableDeferred(uuid)
        this.send(CQCodeGroupMessageOut(params = CQCodeGroupMessageOut.Params(groupId, content), echo = uuid))
        val response = deferred.await().fromJson<SendMessageResp>()
        return if (response.status == ActionStatus.ok) response.data!!.messageId else null
    }

    /**
     * 用重载函数的方式将发送合并转发消息的接口包装成发送普通
     * 消息的接口
     */
    suspend fun sendGroupMessage(groupId: Long, content: NodeMessageChain): ForwardMessageId.ForwardMessageId {
        return this.sendGroupForwardMsg(groupId, content)
    }

    /**
     * 用重载函数的方式将发送合并转发消息的接口包装成发送普通
     * 消息的接口, 但是使用异步发送
     */
    suspend fun sendGroupMessageAsync(groupId: Long, content: NodeMessageChain) {
        this.sendGroupForwardMsgAsync(groupId, content)
    }

    /**
     * 发送纯文本消息但是异步
     */
    suspend fun sendGroupMessageAsync(groupId: Long, content: String) {
        this.send(
            CQCodeGroupMessageOut(
                params = CQCodeGroupMessageOut.Params(groupId, content),
                echo = UUID.randomUUID()
            )
        )
    }

    /**
     * 发送群组消息但是是CQ码消息链
     */
    suspend fun sendGroupMessage(groupId: Long, content: CQMessageChain): Long? {
        return this.sendGroupMessage(groupId, content.finalString)
    }

    /**
     * 发送CQ码消息链但是异步
     */
    suspend fun sendGroupMessageAsync(groupId: Long, content: CQMessageChain) {
        this.send(
            CQCodeGroupMessageOut(
                params = CQCodeGroupMessageOut.Params(groupId, content.finalString),
                echo = UUID.randomUUID()
            )
        )
    }

    /**
     * 发送群组消息但是是MessageChain消息链
     */
    suspend fun sendGroupMessage(groupId: Long, content: MessageChain): Long? {
        val uuid = UUID.randomUUID()
        val deferred = this.createCompletableDeferred(uuid)
        this.send(
            ArrayGroupMessageOut(
                params = ArrayGroupMessageOut.Params(groupId, content.finalArrayMsgList),
                echo = uuid
            )
        )
        val response = deferred.await().fromJson<SendMessageResp>()
        return if (response.status == ActionStatus.ok) response.data!!.messageId else null
    }

    /**
     * 发送MessageChain消息链但是异步
     */
    suspend fun sendGroupMessageAsync(groupId: Long, content: MessageChain) {
        this.send(
            ArrayGroupMessageOut(
                params = ArrayGroupMessageOut.Params(groupId, content.finalArrayMsgList),
                echo = UUID.randomUUID()
            )
        )
    }

    /**
     * 发送群组消息但是是服务器返回的消息类型
     */
    suspend fun sendGroupMessage(groupId: Long, content: List<ArrayMessage>): Long? {
        val uuid = UUID.randomUUID()
        val deferred = this.createCompletableDeferred(uuid)
        this.send(RawArrayGroupMessageOut(params = RawArrayGroupMessageOut.Params(groupId, content), echo = uuid))
        val response = deferred.await().fromJson<SendMessageResp>()
        return if (response.status == ActionStatus.ok) response.data!!.messageId else null
    }

    /**
     * 发送Raw List<ArrayMessage>但是异步
     */
    suspend fun sendGroupMessageAsync(groupId: Long, content: List<ArrayMessage>) {
        this.send(
            RawArrayGroupMessageOut(
                params = RawArrayGroupMessageOut.Params(groupId, content),
                echo = UUID.randomUUID()
            )
        )
    }

    /**
     * 发送私聊消息但是是纯文本
     */
    suspend fun sendPrivateMessage(userId: Long, content: String): Long? {
        val uuid = UUID.randomUUID()
        val deferred = this.createCompletableDeferred(uuid)
        this.send(CQCodePrivateMessageOut(params = CQCodePrivateMessageOut.Params(userId, content), echo = uuid))
        val response = deferred.await().fromJson<SendMessageResp>()
        return if (response.status == ActionStatus.ok) response.data!!.messageId else null
    }

    /**
     * 用重载函数的方式将发送合并转发消息的接口包装成发送普通
     * 消息的接口
     */
    suspend fun sendPrivateMessage(userId: Long, content: NodeMessageChain): ForwardMessageId.ForwardMessageId {
        return this.sendPrivateForwardMsg(userId, content)
    }

    /**
     * 用重载函数的方式将发送合并转发消息的接口包装成发送普通
     * 消息的接口, 但是使用异步发送
     */
    suspend fun sendPrivateMessageAsync(userId: Long, content: NodeMessageChain) {
        this.sendPrivateForwardMsgAsync(userId, content)
    }

    /**
     * 发送纯文本但是异步
     */
    suspend fun sendPrivateMessageAsync(userId: Long, content: String) {
        this.send(
            CQCodePrivateMessageOut(
                params = CQCodePrivateMessageOut.Params(userId, content),
                echo = UUID.randomUUID()
            )
        )
    }

    /**
     * 发送私聊消息但是是CQ码消息链
     */
    suspend fun sendPrivateMessage(userId: Long, content: CQMessageChain): Long? {
        return this.sendPrivateMessage(userId, content.finalString)
    }

    /**
     * 发送CQ消息链但是异步
     */
    suspend fun sendPrivateMessageAsync(userId: Long, content: CQMessageChain) {
        this.send(
            CQCodePrivateMessageOut(
                params = CQCodePrivateMessageOut.Params(userId, content.finalString),
                echo = UUID.randomUUID()
            )
        )
    }

    /**
     * 发送私聊消息但是是MessageChain消息链
     */
    suspend fun sendPrivateMessage(userId: Long, content: MessageChain): Long? {
        val uuid = UUID.randomUUID()
        val deferred = this.createCompletableDeferred(uuid)
        this.send(
            ArrayPrivateMessageOut(
                params = ArrayPrivateMessageOut.Params(userId, content.finalArrayMsgList),
                echo = uuid
            )
        )
        val response = deferred.await().fromJson<SendMessageResp>()
        return if (response.status == ActionStatus.ok) response.data!!.messageId else null
    }

    /**
     * 发送MessageChain但是异步发送
     */
    suspend fun sendPrivateMessageAsync(userId: Long, content: MessageChain) {
        this.send(
            ArrayPrivateMessageOut(
                params = ArrayPrivateMessageOut.Params(userId, content.finalArrayMsgList),
                echo = UUID.randomUUID()
            )
        )
    }

    /**
     * 发送私聊消息但是是服务器返回的消息类型
     */
    suspend fun sendPrivateMessage(userId: Long, content: List<ArrayMessage>): Long? {
        val uuid = UUID.randomUUID()
        val deferred = this.createCompletableDeferred(uuid)
        this.send(RawArrayPrivateMessageOut(params = RawArrayPrivateMessageOut.Params(userId, content), echo = uuid))
        val response = deferred.await().fromJson<SendMessageResp>()
        return if (response.status == ActionStatus.ok) response.data!!.messageId else null
    }

    /**
     * 发送Raw List<ArrayMessage>但是异步发送
     */
    suspend fun sendPrivateMessageAsync(userId: Long, content: List<ArrayMessage>) {
        this.send(
            RawArrayPrivateMessageOut(
                params = RawArrayPrivateMessageOut.Params(userId, content),
                echo = UUID.randomUUID()
            )
        )
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
    suspend fun setGroupName(groupId: Long, newName: String) {
        this.send(SetGroupNameOut(params = SetGroupNameOut.Params(groupId, newName)))
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
    suspend fun getMessage(messageId: Long): GetMessage.Message {
        val uuid = UUID.randomUUID()
        val deferred = this.createCompletableDeferred(uuid)
        this.send(GetMessageOut(params = GetMessageOut.Params(messageId), echo = uuid))
        val response = deferred.await()
        return response.fromJson<GetMessage>().data
    }

    /**
     * 获取账号登录信息
     */
    suspend fun getLoginInfo(): LoginInfo.LoginInfo {
        val uuid = UUID.randomUUID()
        val deferred = this.createCompletableDeferred(uuid)
        this.send(GetLoginInfoOut(echo = uuid))
        val response = deferred.await()
        return response.fromJson<LoginInfo>().data
    }

    /**
     * 获取陌生人信息
     */
    suspend fun getStrangerInfo(userId: Long, noCache: Boolean = false): StrangerInfo.StrangerInfo {
        val uuid = UUID.randomUUID()
        val deferred = this.createCompletableDeferred(uuid)
        this.send(GetStrangerInfoOut(params = GetStrangerInfoOut.Params(userId, noCache), echo = uuid))
        val response = deferred.await()
        return response.fromJson<StrangerInfo>().data
    }

    /**
     * 获取好友列表
     */
    suspend fun getFriendList(): List<FriendList.Friend> {
        val uuid = UUID.randomUUID()
        val deferred = this.createCompletableDeferred(uuid)
        this.send(GetFriendListOut(uuid))
        val response = deferred.await()
        return response.fromJson<FriendList>().data
    }

    /**
     * 获取群组信息
     */
    suspend fun getGroupInfo(groupId: Long, noCache: Boolean = false): GroupInfo.GroupInfo {
        val uuid = UUID.randomUUID()
        val deferred = this.createCompletableDeferred(uuid)
        this.send(GetGroupInfoOut(params = GetGroupInfoOut.Params(groupId, noCache), echo = uuid))
        val response = deferred.await()
        return response.fromJson<GroupInfo>().data
    }

    /**
     * 获取账号的群组列表
     */
    suspend fun getGroupList(): List<GroupList.Group> {
        val uuid = UUID.randomUUID()
        val deferred = this.createCompletableDeferred(uuid)
        this.send(GetGroupListOut(echo = uuid))
        val response = deferred.await()
        return response.fromJson<GroupList>().data
    }

    /**
     * 获取群组成员信息
     */
    suspend fun getGroupMemberInfo(groupId: Long, userId: Long, noCache: Boolean = false): GroupMemberList.MemberInfo {
        val uuid = UUID.randomUUID()
        val deferred = this.createCompletableDeferred(uuid)
        this.send(GetGroupMemberInfoOut(params = GetGroupMemberInfoOut.Params(groupId, userId, noCache), echo = uuid))
        val response = deferred.await()
        return response.fromJson<GroupMemberInfo>().data
    }

    /**
     * 获取群组成员列表
     */
    suspend fun getGroupMemberList(groupId: Long): List<GroupMemberList.MemberInfo> {
        val uuid = UUID.randomUUID()
        val deferred = this.createCompletableDeferred(uuid)
        this.send(GetGroupMemberListOut(params = GetGroupMemberListOut.Params(groupId), echo = uuid))
        val response = deferred.await()
        return response.fromJson<GroupMemberList>().data
    }

    /**
     * 获取OneBot实现的版本信息
     */
    suspend fun getVersionInfo(): OneBotVersionInfo.VersionInfo {
        val uuid = UUID.randomUUID()
        val deferred = this.createCompletableDeferred(uuid)
        this.send(GetVersionInfo(echo = uuid))
        val response = deferred.await()
        return response.fromJson<OneBotVersionInfo>().data
    }

    /**
     * 检查是否可以发送图片
     */
    suspend fun canSendImage(): Boolean {
        val uuid = UUID.randomUUID()
        val deferred = this.createCompletableDeferred(uuid)
        this.send(CanSendImageOut(uuid))
        val response = deferred.await()
        return response.fromJson<CanSend>().data.yes
    }

    /**
     * 检查是否可以发送语音
     * (感觉没什么用)
     */
    suspend fun canSendRecord(): Boolean {
        val uuid = UUID.randomUUID()
        val deferred = this.createCompletableDeferred(uuid)
        this.send(CanSendRecordOut(uuid))
        val response = deferred.await()
        return response.fromJson<CanSend>().data.yes
    }

    /**
     * 该方法是Lagrange.OneBot的拓展API
     * 用于获取收藏表情
     * 返回一个List<String> String为URL
     */
    suspend fun fetchCustomFace(): List<String> {
        val uuid = UUID.randomUUID()
        val deferred = this.createCompletableDeferred(uuid)
        this.send(FetchCustomFaceOut(echo = uuid))
        val response = deferred.await()
        return response.fromJson<CustomFace>().data
    }

    /**
     * 该方法是Lagrange.OneBot的拓展API
     * 用于发送群聊中的合并转发消息链
     * 该方法有返回值返回forwardId
     */
    suspend fun sendGroupForwardMsg(groupId: Long, message: NodeMessageChain): ForwardMessageId.ForwardMessageId {
        val uuid = UUID.randomUUID()
        val deferred = this.createCompletableDeferred(uuid)
        this.send(SendGroupForwardMsgOut(params = SendGroupForwardMsgOut.Params(groupId, message.nodes), echo = uuid))
        val response = deferred.await()
        return response.fromJson<ForwardMessageId>().data
    }

    /**
     * 该方法是Lagrange.OneBot的拓展API
     * 用于发送群聊中的合并转发消息链
     * 但是使用异步的方式发送不会有返回值
     */
    suspend fun sendGroupForwardMsgAsync(groupId: Long, message: NodeMessageChain) {
        this.send(
            SendGroupForwardMsgOut(
                params = SendGroupForwardMsgOut.Params(groupId, message.nodes),
                echo = UUID.randomUUID()
            )
        )
    }

    /**
     * 该方法是Lagrange.OneBot的拓展API
     * 用于发送私聊的合并转发消息链
     * 该方法有返回值返回forwardId
     */
    suspend fun sendPrivateForwardMsg(userId: Long, message: NodeMessageChain): ForwardMessageId.ForwardMessageId {
        val uuid = UUID.randomUUID()
        val deferred = this.createCompletableDeferred(uuid)
        this.send(
            SendPrivateForwardMsgOut(
                params = SendPrivateForwardMsgOut.Params(userId, message.nodes),
                echo = uuid
            )
        )
        val response = deferred.await()
        return response.fromJson<ForwardMessageId>().data
    }

    /**
     * 该方法是Lagrange.OneBot的拓展API
     * 用于发送私聊的合并转发消息链
     * 该方法使用异步的方式发送不会有返回值
     */
    suspend fun sendPrivateForwardMsgAsync(userId: Long, message: NodeMessageChain) {
        this.send(
            SendPrivateForwardMsgOut(
                params = SendPrivateForwardMsgOut.Params(userId, message.nodes),
                echo = UUID.randomUUID()
            )
        )
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
     * `file` -> 本地路径
     * `name` -> 上传到群文件夹显示的名字
     * `folder` -> 群内的目录
     * ***注意: 文件路径是OneBot实现的本地路径***
     */
    suspend fun uploadGroupFile(groupId: Long, file: String, name: String, folder: String = "/") {
        this.send(UploadGroupFileOut(params = UploadGroupFileOut.Params(groupId, file, name, folder)))
    }

    /**
     * 该方法是Lagrange.OneBot的拓展API
     * 用于在私聊中发送文件
     * `file` -> 本地路径
     * `name` -> 上传到文件夹显示的名字
     * ***注意: 文件路径是OneBot实现的本地路径***
     */
    suspend fun uploadPrivateFile(userId: Long, file: String, name: String) {
        this.send(UploadPrivateFileOut(params = UploadPrivateFileOut.Params(userId, file, name)))
    }

    /**
     * 该方法是Lagrange.OneBot的拓展API
     * 用于在获取群文件目录列表
     */
    suspend fun getGroupRootFiles(groupId: Long): GetGroupRootFiles.RootFiles {
        val uuid = UUID.randomUUID()
        val deferred = this.createCompletableDeferred(uuid)
        this.send(GetGroupRootFilesOut(params = GetGroupRootFilesOut.Params(groupId), echo = uuid))
        val response = deferred.await()
        return response.fromJson<GetGroupRootFiles>().data
    }

    /**
     * 该方法是Lagrange.OneBot的拓展API
     * 用于在获取群文件中的子目录中的文件列表
     */
    suspend fun getGroupFilesByFolder(groupId: Long, folderId: String): GetGroupRootFiles.RootFiles {
        val uuid = UUID.randomUUID()
        val deferred = this.createCompletableDeferred(uuid)
        this.send(GetGroupFilesByFolderOut(params = GetGroupFilesByFolderOut.Params(groupId, folderId), echo = uuid))
        val response = deferred.await()
        return response.fromJson<GetGroupRootFiles>().data
    }

    /**
     * 该方法是Lagrange.OneBot的拓展API
     * 用于在获取某个群文件的URL地址
     */
    suspend fun getGroupFileUrl(groupId: Long, fileId: String, busId: Int): GetGroupFileUrl.FileURL {
        val uuid = UUID.randomUUID()
        val deferred = this.createCompletableDeferred(uuid)
        this.send(GetGroupFileUrlOut(params = GetGroupFileUrlOut.Params(groupId, fileId, busId), echo = uuid))
        val response = deferred.await()
        return response.fromJson<GetGroupFileUrl>().data
    }

    /**
     * 该方法是Lagrange.OneBot的拓展API
     * 用于设置群组成员专属头衔
     */
    suspend fun setGroupMemberSpecialTitle(groupId: Long, userId: Long, title: String = "", duration: Int = -1) {
        this.send(SetGroupMemberTitleOut(params = SetGroupMemberTitleOut.Params(groupId, userId, title, duration)))
    }

    /**
     * 该方法是Lagrange.OneBot的拓展API
     * 该方法被Lagrange标记为`隐藏API`
     * 并且为异步发送API不会有返回值
     */
    suspend fun releaseGroupNoticeAsync(groupId: Long, content: String, image: String = "") {
        this.send(
            ReleaseGroupNoticeOut(
                params = ReleaseGroupNoticeOut.Params(groupId, content, image),
                echo = UUID.randomUUID()
            )
        )
    }

    /**
     * 该方法是Lagrange.OneBot的拓展API
     * 该方法被Lagrange标记为`隐藏API`
     * 用于设置一条群公告, 但是[image]参数并不需要传入
     * 如果传入会导致发送失败, 截至: 24/10/01: 15:11
     * 返回一个String类型的公告ID
     */
    suspend fun releaseGroupNotice(groupId: Long, content: String, image: String = ""): String {
        val uuid = UUID.randomUUID()
        val deferred = this.createCompletableDeferred(uuid)
        this.send(ReleaseGroupNoticeOut(params = ReleaseGroupNoticeOut.Params(groupId, content, image), echo = uuid))
        val response = deferred.await()
        return response.fromJson<ReleaseGroupNotice>().data
    }

    /**
     * 该方法是Lagrange.OneBot的拓展API
     * 用于获取所有的群公告
     */
    suspend fun getAllGroupNotices(groupId: Long): List<GroupNotice.GroupNotice> {
        val uuid = UUID.randomUUID()
        val deferred = this.createCompletableDeferred(uuid)
        this.send(GetGroupNoticeOut(params = GetGroupNoticeOut.Params(groupId), echo = uuid))
        val response = deferred.await()
        return response.fromJson<GroupNotice>().data
    }

    /**
     * 该方法是Lagrange.OneBot的拓展API
     * 用于获取指定的群公告ID的内容
     */
    suspend fun getGroupNoticeById(groupId: Long, noticeId: String): GroupNotice.GroupNotice? {
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
     * 用于使用一个[QQFace]对象回应某个消息
     * 需要提供message_id, isAdd参数如果为false则表示
     * 取消对这条消息的reaction
     */
    suspend fun reaction(groupId: Long, messageId: Long, code: QQFace, isAdd: Boolean = true) {
        this.send(ReactionOut(params = ReactionOut.Params(groupId, messageId, code.id.toString(), isAdd)))
    }

    /**
     * 该方法是Lagrange.OneBot的拓展API
     * 用于获取群内的精华消息
     */
    suspend fun getEssenceMessageList(groupId: Long): List<EssenceMessageList.EssenceMessage> {
        val uuid = UUID.randomUUID()
        val deferred = this.createCompletableDeferred(uuid)
        this.send(GetEssenceMessageListOut(params = GetEssenceMessageListOut.Params(groupId), echo = uuid))
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

    /**
     * 该方法是Lagrange.OneBot的拓展API
     * 用于获取群聊的Honor信息
     */
    suspend fun getGroupHonorInfo(groupId: Long, type: HonorType): HonorInfo.HonorInfo {
        val uuid = UUID.randomUUID()
        val deferred = this.createCompletableDeferred(uuid)
        this.send(GetGroupHonorInfoOut(params = GetGroupHonorInfoOut.Params(groupId, type.type), echo = uuid))
        val response = deferred.await()
        return response.fromJson<HonorInfo>().data
    }

    /**
     * 该方法是Lagrange.OneBot的拓展API
     * 用于获取CSRF Token
     */
    suspend fun getCSRFToken(): String {
        val uuid = UUID.randomUUID()
        val deferred = this.createCompletableDeferred(uuid)
        this.send(GetCSRFTokenOut(echo = uuid))
        val response = deferred.await()
        return response.fromJson<CSRFToken>().data.token
    }

    /**
     * 该方法是Lagrange.OneBot的拓展API
     * 用于获取群聊中某个消息ID之前的历史聊天记录
     * 默认只获取20条聊天记录
     */
    suspend fun getGroupMessageHistory(
        groupId: Long,
        messageId: Long,
        count: Int = 20
    ): GroupMessageHistory.MessageHistory {
        val uuid = UUID.randomUUID()
        val deferred = this.createCompletableDeferred(uuid)
        this.send(
            GetGroupMessageHistory(params = GetGroupMessageHistory.Params(groupId, messageId, count), echo = uuid)
        )
        val response = deferred.await()
        val serializedResponse = response.fromJson<GroupMessageHistory>()
        serializedResponse.data.messages.forEach {
            val oldSender = it.sender
            val newSenderWithGroupId = GroupSender(
                this, oldSender.userId, oldSender.nickname,
                oldSender.sex, oldSender.role, oldSender.card,
                oldSender.level, oldSender.age, oldSender.title, groupId
            )
            it.sender = newSenderWithGroupId
        }
        return serializedResponse.data
    }

    /**
     * 该方法是Lagrange.OneBot的拓展API
     * 用于获取私聊中某个消息ID之前的历史聊天记录
     * 默认只获取20条聊天记录
     */
    suspend fun getPrivateMessageHistory(
        userId: Long,
        messageId: Long,
        count: Int = 20
    ): PrivateMessageHistory.MessageHistory {
        val uuid = UUID.randomUUID()
        val deferred = this.createCompletableDeferred(uuid)
        this.send(
            GetPrivateMessageHistory(
                params = GetPrivateMessageHistory.Params(userId, messageId, count),
                echo = uuid
            )
        )
        val response = deferred.await()
        return response.fromJson<PrivateMessageHistory>().data
    }

    /**
     * 该方法是Lagrange.OneBot的拓展API
     * 用于获取一个合并转发消息链中的内容
     */
    suspend fun getForwardMessage(id: String): ForwardMessage.ForwardMessage {
        val uuid = UUID.randomUUID()
        val deferred = this.createCompletableDeferred(uuid)
        this.send(GetForwardMessageOut(params = GetForwardMessageOut.Params(id), uuid))
        val response = deferred.await()
        return response.fromJson<ForwardMessage>().data
    }

    /**
     * 获取OneBOt实现的状态
     * 部分额外字段由Lagrange.OneBot实现
     */
    suspend fun getStatus(): HeartBeatEvent.Status {
        val uuid = UUID.randomUUID()
        val deferred = this.createCompletableDeferred(uuid)
        this.send(GetStatusOut(echo = uuid))
        val response = deferred.await()
        return response.fromJson<Status>().data
    }

    /**
     * 该方法是Lagrange.OneBot的拓展API
     * 用于获取机器人账号对应某个域名的Cookie
     * 可以传入`vip.qq.com` `docs.qq.com`等等一系列域名
     */
    suspend fun getCookies(domain: String): String {
        val uuid = UUID.randomUUID()
        val deferred = this.createCompletableDeferred(uuid)
        this.send(GetCookiesOut(params = GetCookiesOut.Params(domain), echo = uuid))
        val response = deferred.await()
        return response.fromJson<GetCookies>().data.cookies
    }

    /**
     * 重启OneBot实现
     */
    suspend fun setRestart() {
        this.send(SetRestartOut())
    }

    /**
     * 清除OneBot缓存
     */
    suspend fun cleanCache() {
        this.send(CleanCacheOut())
    }

    /**
     * 调用框架中没有定义的api端点, 并且异步调用无返回值,
     * 传入api端点以及参数
     */
    suspend fun callApiAsync(endpoint: String, params: Map<String, Any>) {
        this.send(CallAPIOut(endpoint, params, UUID.randomUUID()))
    }

    /**
     * 调用框架中没有定义的api端点, 同步调用有返回值,
     * 返回一个JSON String,传入api端点以及参数
     */
    suspend fun callApi(endpoint: String, params: Map<String, Any>): String {
        val uuid = UUID.randomUUID()
        val deferred = this.createCompletableDeferred(uuid)
        this.callApiAsync(endpoint, params)
        val response = deferred.await()
        return response
    }

    /**
     * 该方法是Lagrange.OneBot的拓展API
     * 用于上传一个图片到QQ图床中, 可以为base64
     * 如果传入base64不能附带base64图片前缀
     * 例如`data:image/png;base64`
     */
    suspend fun uploadImage(image: String, base64: Boolean = false): String {
        val uuid = UUID.randomUUID()
        val deferred = this.createCompletableDeferred(uuid)
        val file = if (base64) "base64://$image" else image
        this.send(UploadImageOut(UploadImageOut.Params(file), echo = uuid))
        val response = deferred.await()
        return response.fromJson<UploadImage>().data
    }

    /**
     * 该方法是Lagrange.OneBot的拓展API
     * 用于设置机器人的头像, 如果传入的是base64则
     * 不能有base64前缀
     */
    suspend fun setBotAvatar(image: String, base64: Boolean = false): Boolean {
        val uuid = UUID.randomUUID()
        val deferred = this.createCompletableDeferred(uuid)
        val file = if (base64) "base64://$image" else image
        this.send(SetBotAvatarOut(SetBotAvatarOut.Params(file), echo = uuid))
        val response = deferred.await()
        return response.fromJson<SetBotAvatar>().status != "failed"
    }

    /**
     * 该方法是Lagrange.OneBot的拓展API
     * 用于获取mface的key(mface指的是商城里的表情包)
     * 传入一个字符串列表返回一个字符串列表
     */
    suspend fun fetchMFaceKey(emojiIds: List<String>): List<String> {
        val uuid = UUID.randomUUID()
        val deferred = this.createCompletableDeferred(uuid)
        this.send(FetchMFaceKeyOut(FetchMFaceKeyOut.Params(emojiIds), echo = uuid))
        val response = deferred.await()
        return response.fromJson<FetchMFaceKey>().data
    }

    /**
     * 该方法是Lagrange.OneBot的拓展API
     * 用于设置群聊的头像不能以base64的方式传入
     */
    suspend fun setGroupAvatar(groupId: Long, image: String): Boolean {
        val uuid = UUID.randomUUID()
        val deferred = this.createCompletableDeferred(uuid)
        this.send(SetGroupAvatarOut(SetGroupAvatarOut.Params(image), echo = uuid))
        val response = deferred.await()
        return response.fromJson<SetGroupAvatar>().status != "failed"
    }

    /**
     * 该方法是Go-CQHTTP的API
     * 用于OCR一个图片获取文字所在的坐标位置
     */
    suspend fun ocrImage(image: String, base64: Boolean = false): OCRImage.ORCResult? {
        val uuid = UUID.randomUUID()
        val deferred = this.createCompletableDeferred(uuid)
        this.send(OCRImageOut(OCRImageOut.Params(if (base64) "base64://$image" else image), echo = uuid))
        val response = deferred.await()
        return response.fromJson<OCRImage>().data
    }

    /**
     * 该方法是LLOneBot的拓展API
     * 用于设置Bot自身的在线状态
     */
    suspend fun setOnlineStatus(status: OnlineStatus) {
        this.send(SetOnlineStatusOut(SetOnlineStatusOut.Params(status.statusCode)))
    }

    /**
     * 该方法是LLOneBot的拓展API
     * 用于获取带分组的好友列表
     */
    suspend fun getFriendsWithCategory(): List<GetFriendWithCategory.FriendCategory> {
        val uuid = UUID.randomUUID()
        val deferred = this.createCompletableDeferred(uuid)
        this.send(GetFriendWithCategoryOut(echo = uuid))
        val response = deferred.await()
        return response.fromJson<GetFriendWithCategory>().data
    }

    /**
     * 该方法是LLOneBot的拓展API
     * 用于获取已过滤的加群请求通知
     */
    suspend fun getGroupIgnoreAddRequest(): List<GroupIgnoreAddRequest.Request> {
        val uuid = UUID.randomUUID()
        val deferred = this.createCompletableDeferred(uuid)
        this.send(GetGroupIgnoreAddRequestOut(echo = uuid))
        val response = deferred.await()
        return response.fromJson<GroupIgnoreAddRequest>().data
    }

    /**
     * 该方法是Go-CQHTTP的API
     * 用于获取Bot是否可以@全体以及@全体剩余的次数
     */
    suspend fun getGroupAtAllRemain(groupId: Long): GroupAtAllRemain.AtAllRemain {
        val uuid = UUID.randomUUID()
        val deferred = this.createCompletableDeferred(uuid)
        this.send(GetGroupAtAllRemainOut(GetGroupAtAllRemainOut.Params(groupId), echo = uuid))
        val response = deferred.await()
        return response.fromJson<GroupAtAllRemain>().data
    }

    /**
     * 该方法是Go-CQHTTP的API
     * 用于删除好友操作
     */
    suspend fun deleteFriend(userId: Long) {
        this.send(DeleteFriendOut(DeleteFriendOut.Params(userId)))
    }

    /**
     * 该方法是Go-CQHTTP的API
     * 用于获取群文件系统信息
     * 例如当前使用了多少空间以及总共有多少空间可以使用
     * 还可以获取总共有几个文件和总共能放下多少个文件
     */
    suspend fun getGroupFileSystemInfo(groupId: Long): GroupFileSystemInfo.FileSystemInfo {
        val uuid = UUID.randomUUID()
        val deferred = this.createCompletableDeferred(uuid)
        this.send(GetGroupFileSystemInfoOut(GetGroupFileSystemInfoOut.Params(groupId), echo = uuid))
        val response = deferred.await()
        return response.fromJson<GroupFileSystemInfo>().data
    }

    /**
     * 该方法是Go-CQHTTP的API
     * 用于创建群文件中的文件夹
     */
    suspend fun createGroupFileFolder(groupId: Long, name: String, parentId: String = "/") {
        this.send(CreateGroupFileFolderOut(CreateGroupFileFolderOut.Params(groupId, name, parentId)))
    }

    /**
     * 该方法是Go-CQHTTP的API
     * 用于删除群文件中的文件夹
     */
    suspend fun deleteGroupFileFolder(groupId: Long, folderId: String) {
        this.send(DeleteGroupFolderOut(DeleteGroupFolderOut.Params(groupId, folderId)))
    }

    /**
     * 该方法是LLOneBot的拓展API
     * 用于获取官方机器人的UIN范围
     */
    suspend fun getRobotUinRange(): List<RobotUinRange.UinRange> {
        val uuid = UUID.randomUUID()
        val deferred = this.createCompletableDeferred(uuid)
        this.send(GetRobotUinRangeOut(echo = uuid))
        val response = deferred.await()
        return response.fromJson<RobotUinRange>().data
    }

    /**
     * 该方法是Lagrange.OneBot的拓展API
     * 用于获取AI声聊的语音类型
     * [chatType]只能传1u
     */
    suspend fun getAIRecordCharacters(groupId: Long, chatType: UInt = 1u): AIRecordCharacters {
        val uuid = UUID.randomUUID()
        val deferred = this.createCompletableDeferred(uuid)
        this.send(GetAIRecordCharactersOut(params = GetAIRecordCharactersOut.Params(groupId, chatType), echo = uuid))
        val response = deferred.await()
        return response.fromJson<AIRecordCharacters>()
    }

    /**
     * 该方法是Lagrange.OneBot的拓展API
     * 用于生成指定音色的AI声音, 传入[text], [groupId], [character]后可以生成
     * [character]是[getAIRecordCharacters]返回的[AIRecordCharacters.Character.characterId]
     * [chatType]只能传1u
     * 如果生成失败则返回null
     */
    suspend fun getAIRecord(groupId: Long, character: String, text: String, chatType: UInt = 1u): String? {
        val uuid = UUID.randomUUID()
        val deferred = this.createCompletableDeferred(uuid)
        this.send(
            GetAIRecordAndSendRecordOut(
                params = GetAIRecordAndSendRecordOut.Params(
                    chatType, text, groupId, character
                ), echo = uuid, action = "get_ai_record"
            )
        )
        val response = deferred.await().fromJson<AIRecord>()
        return if (response.status == ActionStatus.failed) null else response.data
    }

    /**
     * 该方法是Lagrange.OneBot的拓展API
     * 用于生成指定音色的AI声音
     * 但是使用了已知的[character] ([AIRecordCharacter])枚举类来发送
     */
    suspend fun getAIRecord(groupId: Long, character: AIRecordCharacter, text: String, chatType: UInt = 1u): String? {
        return this.getAIRecord(groupId, character.characterId, text, chatType)
    }

    /**
     * 该方法是Lagrange.OneBot的拓展API
     * 用于直接向群内发送指定音色的AI声音, 传入[text], [groupId], [character]后可以生成
     * [character]是[getAIRecordCharacters]返回的[AIRecordCharacters.Character.characterId]
     * [chatType]只能传1u
     */
    suspend fun sendGroupAIRecord(groupId: Long, character: String, text: String, chatType: UInt = 1u) {
        this.send(
            GetAIRecordAndSendRecordOut(
                params = GetAIRecordAndSendRecordOut.Params(
                    chatType, text, groupId, character
                ), echo = UUID.randomUUID(), action = "send_group_ai_record"
            )
        )
    }

    /**
     * 该方法是Lagrange.OneBot的拓展API
     * 用于生成指定音色的AI声音
     * 但是使用了已知的[character] ([AIRecordCharacter])枚举类来发送
     */
    suspend fun sendGroupAIRecord(groupId: Long, character: AIRecordCharacter, text: String, chatType: UInt = 1u) {
        this.sendGroupAIRecord(groupId, character.characterId, text, chatType)
    }
}