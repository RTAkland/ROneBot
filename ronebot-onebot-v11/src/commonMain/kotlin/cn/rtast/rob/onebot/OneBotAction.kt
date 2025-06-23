/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/26
 */

@file:Suppress("unused", "Deprecation")
@file:OptIn(ExperimentalUuidApi::class, InternalROneBotApi::class)

package cn.rtast.rob.onebot

import cn.rtast.rob.BotInstance
import cn.rtast.rob.SendAction
import cn.rtast.rob.annotations.InternalOneBot11Api
import cn.rtast.rob.annotations.InternalROneBotApi
import cn.rtast.rob.annotations.OneBot11CompatibilityApi
import cn.rtast.rob.api.CallAPIApi
import cn.rtast.rob.api.get.*
import cn.rtast.rob.api.set.*
import cn.rtast.rob.api.set.group.*
import cn.rtast.rob.api.set.internal._SendPacketApi
import cn.rtast.rob.api.set.message.*
import cn.rtast.rob.enums.*
import cn.rtast.rob.enums.internal.ActionStatus
import cn.rtast.rob.enums.internal.InstanceType
import cn.rtast.rob.event.raw.GroupSender
import cn.rtast.rob.event.raw.file.*
import cn.rtast.rob.event.raw.friend.ArkSharePeerFriendResponse
import cn.rtast.rob.event.raw.friend.ArkSharePeerResponse
import cn.rtast.rob.event.raw.friend.FriendList
import cn.rtast.rob.event.raw.friend.GetFriendWithCategory
import cn.rtast.rob.event.raw.group.*
import cn.rtast.rob.event.raw.info.*
import cn.rtast.rob.event.raw.internal.SendPacketResponse
import cn.rtast.rob.event.raw.message.*
import cn.rtast.rob.event.raw.onebot.*
import cn.rtast.rob.segment.Segment
import cn.rtast.rob.segment.toMessageChain
import cn.rtast.rob.util.fromJson
import cn.rtast.rob.util.toJson
import kotlinx.coroutines.CompletableDeferred
import love.forte.plugin.suspendtrans.annotation.JvmAsync
import love.forte.plugin.suspendtrans.annotation.JvmBlocking
import kotlin.jvm.JvmOverloads
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 * 向OneBot实现发送各种API, 在这个接口中没有返回值的接口
 * 全部为异步调用(async), 有返回值但是返回值可有可无的接口可以选择
 * 同步调用(await)或者异步调用(async), 返回值必须使用的接口
 * 全部为同步调用(await), 在发送消息类的方法中如果发送成功则返回
 * 一个长整型的消息ID, 发送失败则返回null值
 */
public class OneBotAction internal constructor(
    internal val botInstance: BotInstance,
    private val instanceType: InstanceType,
) : SendAction {
    override fun toString(): String {
        return "OneBotAction{\"Bytes not available to view\"}"
    }

    /**
     * 发送一段json字符串
     */
    override suspend fun send(message: String) {
        when (instanceType) {
            InstanceType.Client -> botInstance.websocket?.sendToServer(message)
            InstanceType.Server -> botInstance.websocketServer?.sendToClient(message)
        }
    }

    /**
     * 创建一个CompletableDeferred<T>对象使异步操作变为同步操作
     * 如果OneBot实现和ROneBot实例在同一局域网或延迟低的情况下
     * 此操作接近于无感, 如果延迟较大则会阻塞消息处理线程, 但是
     * 每条消息处理都开了一个线程~
     */
    private fun createCompletableDeferred(echo: Uuid): CompletableDeferred<String> {
        val deferred = CompletableDeferred<String>()
        botInstance.messageHandler.suspendedRequests[echo] = deferred
        return deferred
    }

    /**
     * 向所有群聊中发送MessageChain消息链消息
     * 所有群聊指ROneBotFactory中设置的监听群号
     * 如果没有设置则此方法以及重载方法将毫无作用
     */
    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    public suspend fun broadcastMessageListening(content: MessageChain) {
        botInstance.listenedGroups.forEach {
            this.sendGroupMessage(it, content)
        }
    }

    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    public suspend fun broadcastMessageListening(content: Segment) {
        botInstance.listenedGroups.forEach {
            this.sendGroupMessage(it, content.toMessageChain())
        }
    }

    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    public suspend fun broadcastMessageListening(content: List<Segment>) {
        botInstance.listenedGroups.forEach {
            this.sendGroupMessage(it, content.toMessageChain())
        }
    }

    /**
     * 向所有监听的群聊发送一条纯文本消息
     */
    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    public suspend fun broadcastMessageListening(content: String) {
        botInstance.listenedGroups.forEach {
            this.sendGroupMessage(it, content)
        }
    }

    /**
     * 向所有群发送一条数组消息链消息
     * 该方法会向`所有群(所有已加入的群聊)`发送消息
     * 使用之前请慎重考虑
     */
    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    public suspend fun broadcastMessage(content: MessageChain) {
        this.getGroupList().map { it.groupId }.forEach {
            this.sendGroupMessage(it, content)
        }
    }

    /**
     * 向所有群发送一条纯文本消息
     * 该方法会向`所有群(所有已加入的群聊)`发送消息
     * 使用之前请慎重考虑
     */
    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    public suspend fun broadcastMessage(content: String) {
        this.getGroupList().map { it.groupId }.forEach {
            this.sendGroupMessage(it, content)
        }
    }

    /**
     * 向群聊中发送[Segment]
     */
    @JvmBlocking(suffix = "JvmBlocking")
    public suspend fun sendGroupMessage(groupId: Long, content: Segment): Long? {
        return this.sendGroupMessage(groupId, content.toMessageChain())
    }

    /**
     * 向一个群聊中发送一段纯文本消息
     */
    @JvmBlocking(suffix = "JvmBlocking")
    public suspend fun sendGroupMessage(groupId: Long, content: String): Long? {
        val uuid = Uuid.random()
        val deferred = this.createCompletableDeferred(uuid)
        this.send(CQCodeGroupMessageApi(params = CQCodeGroupMessageApi.Params(groupId, content), echo = uuid).toJson())
        val response = deferred.await().fromJson<SendMessageResponse>()
        return if (response.status == ActionStatus.ok) response.data!!.messageId else null
    }

    /**
     * 用重载函数的方式将发送合并转发消息的接口包装成发送普通
     * 消息的接口
     */
    @JvmBlocking(suffix = "JvmBlocking")
    public suspend fun sendGroupMessage(groupId: Long, content: NodeMessageChain): ForwardMessageId.ForwardMessageId {
        return this.sendGroupForwardMsg(groupId, content)
    }

    /**
     * 异步的向群聊中发送[Segment]
     */
    @JvmAsync(suffix = "JvmAsync")
    public suspend fun sendGroupMessageAsync(groupId: Long, content: Segment) {
        this.sendGroupMessageAsync(groupId, content.toMessageChain())
    }

    /**
     * 用重载函数的方式将发送合并转发消息的接口包装成发送普通
     * 消息的接口, 但是使用异步发送
     */
    @JvmAsync(suffix = "JvmAsync")
    public suspend fun sendGroupMessageAsync(groupId: Long, content: NodeMessageChain) {
        this.sendGroupForwardMsgAsync(groupId, content)
    }

    /**
     * 发送纯文本消息但是异步
     */
    @JvmAsync(suffix = "JvmAsync")
    public suspend fun sendGroupMessageAsync(groupId: Long, content: String) {
        this.send(
            CQCodeGroupMessageApi(
                params = CQCodeGroupMessageApi.Params(groupId, content),
                echo = Uuid.random()
            ).toJson()
        )
    }

    /**
     * 发送群组消息但是是MessageChain消息链
     */
    @JvmBlocking(suffix = "JvmBlocking")
    public suspend fun sendGroupMessage(groupId: Long, content: MessageChain): Long? {
        val uuid = Uuid.random()
        val deferred = this.createCompletableDeferred(uuid)
        this.send(
            ArrayGroupMessageApi(
                params = ArrayGroupMessageApi.Params(groupId, content.finalArrayMsgList),
                echo = uuid
            ).toJson()
        )
        val response = deferred.await().fromJson<SendMessageResponse>()
        return if (response.status == ActionStatus.ok) response.data!!.messageId else null
    }

    /**
     * 发送MessageChain消息链但是异步
     */
    @JvmAsync(suffix = "JvmAsync")
    public suspend fun sendGroupMessageAsync(groupId: Long, content: MessageChain) {
        this.send(
            ArrayGroupMessageApi(
                params = ArrayGroupMessageApi.Params(groupId, content.finalArrayMsgList),
                echo = Uuid.random()
            ).toJson()
        )
    }

    /**
     * 发送群组消息但是是服务器返回的消息类型
     */
    @JvmBlocking(suffix = "JvmBlocking")
    public suspend fun sendGroupMessage(groupId: Long, content: List<ArrayMessage>): Long? {
        val uuid = Uuid.random()
        val deferred = this.createCompletableDeferred(uuid)
        this.send(
            RawArrayGroupMessageApi(
                params = RawArrayGroupMessageApi.Params(groupId, content),
                echo = uuid
            ).toJson()
        )
        val response = deferred.await().fromJson<SendMessageResponse>()
        return if (response.status == ActionStatus.ok) response.data!!.messageId else null
    }

    /**
     * 发送Raw List<ArrayMessage>但是异步
     */
    @JvmAsync(suffix = "JvmAsync")
    public suspend fun sendGroupMessageAsync(groupId: Long, content: List<ArrayMessage>) {
        this.send(
            RawArrayGroupMessageApi(
                params = RawArrayGroupMessageApi.Params(groupId, content),
                echo = Uuid.random()
            ).toJson()
        )
    }

    /**
     * 向好友发送[Segment]
     */
    @JvmBlocking(suffix = "JvmBlocking")
    public suspend fun sendPrivateMessage(userId: Long, content: Segment): Long? {
        return this.sendPrivateMessage(userId, content.toMessageChain())
    }

    /**
     * 发送私聊消息但是是纯文本
     */
    @JvmBlocking(suffix = "JvmBlocking")
    public suspend fun sendPrivateMessage(userId: Long, content: String): Long? {
        val uuid = Uuid.random()
        val deferred = this.createCompletableDeferred(uuid)
        this.send(
            CQCodePrivateMessageApi(
                params = CQCodePrivateMessageApi.Params(userId, content),
                echo = uuid
            ).toJson()
        )
        val response = deferred.await().fromJson<SendMessageResponse>()
        return if (response.status == ActionStatus.ok) response.data!!.messageId else null
    }

    /**
     * 用重载函数的方式将发送合并转发消息的接口包装成发送普通
     * 消息的接口
     */
    @JvmBlocking(suffix = "JvmBlocking")
    public suspend fun sendPrivateMessage(userId: Long, content: NodeMessageChain): ForwardMessageId.ForwardMessageId {
        return this.sendPrivateForwardMsg(userId, content)
    }

    /**
     * 用重载函数的方式将发送合并转发消息的接口包装成发送普通
     * 消息的接口, 但是使用异步发送
     */
    @JvmAsync(suffix = "JvmAsync")
    public suspend fun sendPrivateMessageAsync(userId: Long, content: NodeMessageChain) {
        this.sendPrivateForwardMsgAsync(userId, content)
    }

    /**
     * 发送纯文本但是异步
     */
    @JvmAsync(suffix = "JvmAsync")
    public suspend fun sendPrivateMessageAsync(userId: Long, content: String) {
        this.send(
            CQCodePrivateMessageApi(
                params = CQCodePrivateMessageApi.Params(userId, content),
                echo = Uuid.random()
            ).toJson()
        )
    }

    /**
     * 发送私聊消息但是是MessageChain消息链
     */
    @JvmBlocking(suffix = "JvmBlocking")
    public suspend fun sendPrivateMessage(userId: Long, content: MessageChain): Long? {
        val uuid = Uuid.random()
        val deferred = this.createCompletableDeferred(uuid)
        this.send(
            ArrayPrivateMessageApi(
                params = ArrayPrivateMessageApi.Params(userId, content.finalArrayMsgList),
                echo = uuid
            ).toJson()
        )
        val response = deferred.await().fromJson<SendMessageResponse>()
        return if (response.status == ActionStatus.ok) response.data!!.messageId else null
    }

    /**
     * 发送MessageChain但是异步发送
     */
    @JvmAsync(suffix = "JvmAsync")
    public suspend fun sendPrivateMessageAsync(userId: Long, content: MessageChain) {
        this.send(
            ArrayPrivateMessageApi(
                params = ArrayPrivateMessageApi.Params(userId, content.finalArrayMsgList),
                echo = Uuid.random()
            ).toJson()
        )
    }

    /**
     * 发送私聊消息但是是服务器返回的消息类型
     */
    @JvmBlocking(suffix = "JvmBlocking")
    public suspend fun sendPrivateMessage(userId: Long, content: List<ArrayMessage>): Long? {
        val uuid = Uuid.random()
        val deferred = this.createCompletableDeferred(uuid)
        this.send(
            RawArrayPrivateMessageApi(
                params = RawArrayPrivateMessageApi.Params(userId, content),
                echo = uuid
            ).toJson()
        )
        val response = deferred.await().fromJson<SendMessageResponse>()
        return if (response.status == ActionStatus.ok) response.data!!.messageId else null
    }

    /**
     * 发送Raw List<ArrayMessage>但是异步发送
     */
    @JvmAsync(suffix = "JvmAsync")
    public suspend fun sendPrivateMessageAsync(userId: Long, content: List<ArrayMessage>) {
        this.send(
            RawArrayPrivateMessageApi(
                params = RawArrayPrivateMessageApi.Params(userId, content),
                echo = Uuid.random()
            ).toJson()
        )
    }

    /**
     * 异步向好友发送[Segment]
     */
    @JvmAsync(suffix = "JvmAsync")
    public suspend fun sendPrivateMessageAsync(userId: Long, content: Segment) {
        this.sendPrivateMessageAsync(userId, content.toMessageChain())
    }

    /**
     * 撤回消息(recall/revoke)
     */
    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    public suspend fun revokeMessage(messageId: Long) {
        this.send(RevokeMessageApi(params = RevokeMessageApi.Params(messageId)).toJson())
    }

    /**
     * 为某人的卡片点赞
     */
    @JvmOverloads
    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    public suspend fun sendLike(userId: Long, times: Int = 1) {
        this.send(SendLikeApi(params = SendLikeApi.Params(userId, times)).toJson())
    }

    /**
     * 将成员踢出群聊
     */
    @JvmOverloads
    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    public suspend fun kickGroupMember(groupId: Long, userId: Long, rejectJoinRequest: Boolean = false) {
        this.send(KickGroupMemberApi(params = KickGroupMemberApi.Params(groupId, userId, rejectJoinRequest)).toJson())
    }

    /**
     * 设置单个成员的禁言
     */
    @JvmOverloads
    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    public suspend fun setGroupBan(groupId: Long, userId: Long, duration: Int = 1800) {
        this.send(SetGroupBanApi(params = SetGroupBanApi.Params(groupId, userId, duration)).toJson())
    }

    /**
     * 设置单个成员的禁言
     */
    public suspend fun setGroupBan(groupId: Long, userId: Long, duration: Duration = 1800.seconds): Unit =
        this.setGroupBan(groupId, userId, duration.inWholeSeconds.toInt())

    /**
     * 设置全员禁言
     */
    @JvmOverloads
    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    public suspend fun setGroupWholeBan(groupId: Long, enable: Boolean = true) {
        this.send(SetGroupWholeBanApi(params = SetGroupWholeBanApi.Params(groupId, enable)).toJson())
    }

    /**
     * 设置群组管理员
     */
    @JvmOverloads
    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    public suspend fun setGroupAdmin(groupId: Long, userId: Long, enable: Boolean = true) {
        this.send(SetGroupAdminApi(params = SetGroupAdminApi.Params(groupId, userId, enable)).toJson())
    }

    /**
     * 设置是否可以匿名聊天
     */
    @JvmOverloads
    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    public suspend fun setGroupAnonymous(groupId: Long, enable: Boolean = true) {
        this.send(SetGroupAnonymousApi(params = SetGroupAnonymousApi.Params(groupId, enable)).toJson())
    }

    /**
     * 设置成群员的群昵称
     */
    @JvmOverloads
    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    public suspend fun setGroupMemberCard(groupId: Long, userId: Long, card: String = "") {
        this.send(SetGroupMemberCardApi(params = SetGroupMemberCardApi.Params(groupId, userId, card)).toJson())
    }

    /**
     * 设置群组名称
     */
    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    public suspend fun setGroupName(groupId: Long, newName: String) {
        this.send(SetGroupNameApi(params = SetGroupNameApi.Params(groupId, newName)).toJson())
    }

    /**
     * 退出群聊,如果是群主并且dismiss为true则解散群聊
     */
    @JvmOverloads
    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    public suspend fun setGroupLeaveOrDismiss(groupId: Long, dismiss: Boolean = false) {
        this.send(SetGroupLeaveApi(params = SetGroupLeaveApi.Params(groupId, dismiss)).toJson())
    }

    /**
     * 处理加好友请求
     */
    @JvmOverloads
    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    public suspend fun setFriendRequest(flag: String, approve: Boolean = true, remark: String = "") {
        this.send(SetFriendRequestApi(params = SetFriendRequestApi.Params(flag, approve, remark)).toJson())
    }

    /**
     * 处理加群请求
     */
    @JvmOverloads
    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    public suspend fun setGroupRequest(
        flag: String,
        type: String,
        approve: Boolean = true,
        reason: String? = null  // only reject user to join group need to provide this param
    ) {
        this.send(SetGroupRequestApi(params = SetGroupRequestApi.Params(flag, type, approve, reason)).toJson())
    }

    /**
     * 根据消息ID获取一条消息
     */
    @JvmBlocking(suffix = "JvmBlocking")
    public suspend fun getMessage(messageId: Long): GetMessage.Message {
        val uuid = Uuid.random()
        val deferred = this.createCompletableDeferred(uuid)
        this.send(GetMessageApi(params = GetMessageApi.Params(messageId), echo = uuid).toJson())
        val response = deferred.await()
        val result = response.fromJson<GetMessage>().data
        result.action = botInstance.action
        return result
    }

    /**
     * 获取账号登录信息
     */
    @JvmBlocking(suffix = "JvmBlocking")
    public suspend fun getLoginInfo(): LoginInfo.LoginInfo {
        val uuid = Uuid.random()
        val deferred = this.createCompletableDeferred(uuid)
        this.send(GetLoginInfoApi(echo = uuid).toJson())
        val response = deferred.await()
        return response.fromJson<LoginInfo>().data
    }

    /**
     * 获取陌生人信息
     */
    @JvmOverloads
    @JvmBlocking(suffix = "JvmBlocking")
    public suspend fun getStrangerInfo(userId: Long, noCache: Boolean = false): StrangerInfo.StrangerInfo {
        val uuid = Uuid.random()
        val deferred = this.createCompletableDeferred(uuid)
        this.send(GetStrangerInfoApi(params = GetStrangerInfoApi.Params(userId, noCache), echo = uuid).toJson())
        val response = deferred.await()
        return response.fromJson<StrangerInfo>().data
    }

    /**
     * 获取好友列表
     */
    @JvmBlocking(suffix = "JvmBlocking")
    public suspend fun getFriendList(): List<FriendList.Friend> {
        val uuid = Uuid.random()
        val deferred = this.createCompletableDeferred(uuid)
        this.send(GetFriendListApi(uuid).toJson())
        val response = deferred.await()
        return response.fromJson<FriendList>().data
    }

    /**
     * 获取群组信息
     */
    @JvmOverloads
    @JvmBlocking(suffix = "JvmBlocking")
    public suspend fun getGroupInfo(groupId: Long, noCache: Boolean = false): GroupInfo.GroupInfo {
        val uuid = Uuid.random()
        val deferred = this.createCompletableDeferred(uuid)
        this.send(GetGroupInfoApi(params = GetGroupInfoApi.Params(groupId, noCache), echo = uuid).toJson())
        val response = deferred.await()
        return response.fromJson<GroupInfo>().data
    }

    /**
     * 获取账号的群组列表
     */
    @JvmBlocking(suffix = "JvmBlocking")
    public suspend fun getGroupList(): List<GroupInfo.GroupInfo> {
        val uuid = Uuid.random()
        val deferred = this.createCompletableDeferred(uuid)
        this.send(GetGroupListApi(echo = uuid).toJson())
        val response = deferred.await()
        return response.fromJson<GroupList>().data
    }

    /**
     * 获取群组成员信息
     */
    @JvmOverloads
    @JvmBlocking(suffix = "JvmBlocking")
    public suspend fun getGroupMemberInfo(
        groupId: Long,
        userId: Long,
        noCache: Boolean = false
    ): GroupMemberList.MemberInfo {
        val uuid = Uuid.random()
        val deferred = this.createCompletableDeferred(uuid)
        this.send(
            GetGroupMemberInfoApi(
                params = GetGroupMemberInfoApi.Params(groupId, userId, noCache),
                echo = uuid
            ).toJson()
        )
        val response = deferred.await()
        return response.fromJson<GroupMemberInfo>().data
    }

    /**
     * 获取群组成员列表
     */
    @JvmBlocking(suffix = "JvmBlocking")
    public suspend fun getGroupMemberList(groupId: Long): List<GroupMemberList.MemberInfo> {
        val uuid = Uuid.random()
        val deferred = this.createCompletableDeferred(uuid)
        this.send(GetGroupMemberListApi(params = GetGroupMemberListApi.Params(groupId), echo = uuid).toJson())
        val response = deferred.await()
        return response.fromJson<GroupMemberList>().data
    }

    /**
     * 获取OneBot实现的版本信息
     */
    @JvmBlocking(suffix = "JvmBlocking")
    public suspend fun getVersionInfo(): OneBotVersionInfo.VersionInfo {
        val uuid = Uuid.random()
        val deferred = this.createCompletableDeferred(uuid)
        this.send(GetVersionInfoApi(echo = uuid).toJson())
        val response = deferred.await()
        return response.fromJson<OneBotVersionInfo>().data
    }

    /**
     * 检查是否可以发送图片
     */
    @JvmBlocking(suffix = "JvmBlocking")
    public suspend fun canSendImage(): Boolean {
        val uuid = Uuid.random()
        val deferred = this.createCompletableDeferred(uuid)
        this.send(CanSendImageApi(uuid).toJson())
        val response = deferred.await()
        return response.fromJson<CanSend>().data.yes
    }

    /**
     * 检查是否可以发送语音
     * (感觉没什么用)
     */
    @JvmBlocking(suffix = "JvmBlocking")
    public suspend fun canSendRecord(): Boolean {
        val uuid = Uuid.random()
        val deferred = this.createCompletableDeferred(uuid)
        this.send(CanSendRecordApi(uuid).toJson())
        val response = deferred.await()
        return response.fromJson<CanSend>().data.yes
    }

    /**
     * 用于获取收藏表情
     * 返回一个List<String> String为URL
     */
    @JvmBlocking(suffix = "JvmBlocking")
    public suspend fun fetchCustomFace(): List<String> {
        val uuid = Uuid.random()
        val deferred = this.createCompletableDeferred(uuid)
        this.send(FetchCustomFaceApi(echo = uuid).toJson())
        val response = deferred.await()
        return response.fromJson<CustomFace>().data
    }

    /**
     * 用于发送群聊中的合并转发消息链
     * 该方法有返回值返回forwardId
     */
    @JvmBlocking(suffix = "JvmBlocking")
    public suspend fun sendGroupForwardMsg(
        groupId: Long,
        message: NodeMessageChain
    ): ForwardMessageId.ForwardMessageId {
        val uuid = Uuid.random()
        val deferred = this.createCompletableDeferred(uuid)
        this.send(
            SendGroupForwardMsgApi(
                params = SendGroupForwardMsgApi.Params(groupId, message.nodes),
                echo = uuid
            ).toJson()
        )
        val response = deferred.await()
        return response.fromJson<ForwardMessageId>().data
    }

    /**
     * 用于发送群聊中的合并转发消息链
     * 但是使用异步的方式发送不会有返回值
     */
    @JvmAsync(suffix = "JvmAsync")
    public suspend fun sendGroupForwardMsgAsync(groupId: Long, message: NodeMessageChain) {
        this.send(
            SendGroupForwardMsgApi(
                params = SendGroupForwardMsgApi.Params(groupId, message.nodes),
                echo = Uuid.random()
            ).toJson()
        )
    }

    /**
     * 用于发送私聊的合并转发消息链
     * 该方法有返回值返回forwardId
     */
    @JvmBlocking(suffix = "JvmBlocking")
    public suspend fun sendPrivateForwardMsg(
        userId: Long,
        message: NodeMessageChain
    ): ForwardMessageId.ForwardMessageId {
        val uuid = Uuid.random()
        val deferred = this.createCompletableDeferred(uuid)
        this.send(
            SendPrivateForwardMsgApi(
                params = SendPrivateForwardMsgApi.Params(userId, message.nodes),
                echo = uuid
            ).toJson()
        )
        val response = deferred.await()
        return response.fromJson<ForwardMessageId>().data
    }

    /**
     * 用于发送私聊的合并转发消息链
     * 该方法使用异步的方式发送不会有返回值
     */
    @JvmAsync(suffix = "JvmAsync")
    public suspend fun sendPrivateForwardMsgAsync(userId: Long, message: NodeMessageChain) {
        this.send(
            SendPrivateForwardMsgApi(
                params = SendPrivateForwardMsgApi.Params(userId, message.nodes),
                echo = Uuid.random()
            ).toJson()
        )
    }

    /**
     * 用于发送私聊的戳一戳行为
     */
    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    public suspend fun sendFriendPoke(userId: Long) {
        this.send(FriendPokeApi(params = FriendPokeApi.Params(userId)).toJson())
    }

    /**
     * 用于发送群聊的戳一戳行为
     */
    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    public suspend fun sendGroupPoke(groupId: Long, userId: Long) {
        this.send(GroupPokeApi(params = GroupPokeApi.Params(groupId, userId)).toJson())
    }

    /**
     * 用于上传群文件
     * `file` -> 本地路径
     * `name` -> 上传到群文件夹显示的名字
     * `folder` -> 群内的目录
     * ***注意: 文件路径是OneBot实现的本地路径***
     */
    @JvmOverloads
    @JvmAsync(suffix = "JvmAsync")
    public suspend fun uploadGroupFileAsync(groupId: Long, filePath: String, name: String, folder: String = "/",
                                            echo: Uuid = Uuid.random()) {
        this.send(
            UploadGroupFileApi(
                params = UploadGroupFileApi.Params(groupId, filePath, name, folder),
                echo = echo
            ).toJson()
        )
    }

    /**
     * 上传群聊文件但是有返回值
     */
    @JvmOverloads
    @JvmBlocking(suffix = "JvmBlocking")
    public suspend fun uploadGroupFile(
        groupId: Long,
        filePath: String,
        name: String,
        folder: String = "/"
    ): UploadGroupFileResponse.UploadGroupFile {
        val uuid = Uuid.random()
        val deferred = this.createCompletableDeferred(uuid)
        this.uploadGroupFileAsync(groupId, filePath, name, folder, uuid)
        val response = deferred.await()
        return response.fromJson<UploadGroupFileResponse>().data
    }

    /**
     * 用于在私聊中发送文件
     * `file` -> 本地路径
     * `name` -> 上传到文件夹显示的名字
     * ***注意: 文件路径是OneBot实现的本地路径***
     */
    @JvmAsync(suffix = "JvmAsync")
    public suspend fun uploadPrivateFileAsync(userId: Long, file: String, name: String, echo: Uuid = Uuid.random()) {
        this.send(
            UploadPrivateFileApi(
                params = UploadPrivateFileApi.Params(userId, file, name),
                echo = echo
            ).toJson()
        )
    }

    /**
     * 发送好友文件但是有返回值
     */
    @JvmBlocking(suffix = "JvmBlocking")
    public suspend fun uploadPrivateFile(
        userId: Long,
        filePath: String,
        name: String
    ): UploadPrivateFileResponse.UploadPrivateFile {
        val uuid = Uuid.random()
        val deferred = this.createCompletableDeferred(uuid)
        this.uploadPrivateFileAsync(userId, filePath, name, uuid)
        val response = deferred.await()
        return response.fromJson<UploadPrivateFileResponse>().data
    }

    /**
     * 用于在获取群文件目录列表
     */
    @JvmBlocking(suffix = "JvmBlocking")
    public suspend fun getGroupRootFiles(groupId: Long): GetGroupRootFiles.RootFiles {
        val uuid = Uuid.random()
        val deferred = this.createCompletableDeferred(uuid)
        this.send(GetGroupRootFilesApi(params = GetGroupRootFilesApi.Params(groupId), echo = uuid).toJson())
        val response = deferred.await()
        return response.fromJson<GetGroupRootFiles>().data
    }

    /**
     * 用于在获取群文件中的子目录中的文件列表
     */
    @JvmBlocking(suffix = "JvmBlocking")
    public suspend fun getGroupFilesByFolder(groupId: Long, folderId: String): GetGroupRootFiles.RootFiles {
        val uuid = Uuid.random()
        val deferred = this.createCompletableDeferred(uuid)
        this.send(
            GetGroupFilesByFolderApi(
                params = GetGroupFilesByFolderApi.Params(groupId, folderId),
                echo = uuid
            ).toJson()
        )
        val response = deferred.await()
        return response.fromJson<GetGroupRootFiles>().data
    }

    /**
     * 用于在获取某个群文件的URL地址
     */
    @JvmBlocking(suffix = "JvmBlocking")
    public suspend fun getGroupFileUrl(groupId: Long, fileId: String, busId: Int): GetGroupFileUrl.FileURL {
        val uuid = Uuid.random()
        val deferred = this.createCompletableDeferred(uuid)
        this.send(GetGroupFileUrlApi(params = GetGroupFileUrlApi.Params(groupId, fileId, busId), echo = uuid).toJson())
        val response = deferred.await()
        return response.fromJson<GetGroupFileUrl>().data
    }

    /**
     * 用于设置群组成员专属头衔
     * @param title 头衔内容
     * @param duration 有效时间, 默认为永久有效
     */
    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    public suspend fun setGroupMemberSpecialTitle(groupId: Long, userId: Long, title: String = "", duration: Int = -1) {
        this.send(
            SetGroupMemberTitleApi(
                params = SetGroupMemberTitleApi.Params(
                    groupId,
                    userId,
                    title,
                    duration
                )
            ).toJson()
        )
    }

    /**
     * 设置群头衔
     * 但是duration使用的是Kotlin的Duration
     */
    @JvmOverloads
    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    public suspend fun setGroupMemberSpecialTitle(
        groupId: Long,
        userId: Long,
        title: String = "",
        duration: Duration = (-1).seconds
    ): Unit = setGroupMemberSpecialTitle(groupId, userId, title, duration.inWholeSeconds.toInt())

    /**
     * 用于发布群公告
     * @see releaseGroupNotice
     */
    @JvmOverloads
    @JvmAsync(suffix = "JvmAsync")
    public suspend fun releaseGroupNoticeAsync(groupId: Long, content: String, image: String = "") {
        this.send(
            ReleaseGroupNoticeApi(
                params = ReleaseGroupNoticeApi.Params(groupId, content, image),
                echo = Uuid.random()
            ).toJson()
        )
    }

    /**
     * 用于设置一条群公告, 但是[image]参数并不需要传入
     * 如果传入会导致发送失败返回一个String类型的公告ID
     */
    @JvmOverloads
    @JvmBlocking(suffix = "JvmBlocking")
    public suspend fun releaseGroupNotice(groupId: Long, content: String, image: String = ""): String {
        val uuid = Uuid.random()
        val deferred = this.createCompletableDeferred(uuid)
        this.send(
            ReleaseGroupNoticeApi(
                params = ReleaseGroupNoticeApi.Params(groupId, content, image),
                echo = uuid
            ).toJson()
        )
        val response = deferred.await()
        return response.fromJson<ReleaseGroupNotice>().data
    }

    /**
     * 用于获取所有的群公告
     */
    @JvmBlocking(suffix = "JvmBlocking")
    public suspend fun getAllGroupNotices(groupId: Long): List<GroupNotice.GroupNotice> {
        val uuid = Uuid.random()
        val deferred = this.createCompletableDeferred(uuid)
        this.send(GetGroupNoticeApi(params = GetGroupNoticeApi.Params(groupId), echo = uuid).toJson())
        val response = deferred.await()
        return response.fromJson<GroupNotice>().data
    }

    /**
     * 用于获取指定的群公告ID的内容
     */
    @JvmBlocking(suffix = "JvmBlocking")
    public suspend fun getGroupNoticeById(groupId: Long, noticeId: String): GroupNotice.GroupNotice? {
        return this.getAllGroupNotices(groupId).find { it.noticeId == noticeId }
    }

    /**
     * 用于删除指定ID的群公告, 无返回值
     */
    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    public suspend fun deleteGroupNotice(groupId: Long, noticeId: String) {
        this.send(DeleteGroupNoticeApi(params = DeleteGroupNoticeApi.Params(groupId, noticeId)).toJson())
    }

    /**
     * 用于使用一个表情(提供一个表情ID)回应某个消息
     * 需要提供message_id, isAdd参数如果为false则表示
     * 取消对这条消息的reaction
     */
    @JvmOverloads
    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    public suspend fun reaction(groupId: Long, messageId: Long, code: String, isAdd: Boolean = true) {
        this.send(ReactionApi(params = ReactionApi.Params(groupId, messageId, code, isAdd)).toJson())
    }

    /**
     * 用于使用一个[QQFace]对象回应某个消息
     * 需要提供message_id, isAdd参数如果为false则表示
     * 取消对这条消息的reaction
     */
    @JvmOverloads
    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    public suspend fun reaction(groupId: Long, messageId: Long, code: QQFace, isAdd: Boolean = true) {
        this.send(ReactionApi(params = ReactionApi.Params(groupId, messageId, code.id.toString(), isAdd)).toJson())
    }

    /**
     * 用于获取群内的精华消息
     */
    @JvmBlocking(suffix = "JvmBlocking")
    public suspend fun getEssenceMessageList(groupId: Long): List<EssenceMessageList.EssenceMessage> {
        val uuid = Uuid.random()
        val deferred = this.createCompletableDeferred(uuid)
        this.send(GetEssenceMessageListApi(params = GetEssenceMessageListApi.Params(groupId), echo = uuid).toJson())
        val response = deferred.await()
        return response.fromJson<EssenceMessageList>().data
    }

    /**
     * 用于设置群精华消息
     */
    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    public suspend fun setEssenceMessage(messageId: Long) {
        this.send(SetEssenceMessageApi(params = SetEssenceMessageApi.Params(messageId)).toJson())
    }

    /**
     * 用于删除群精华消息
     */
    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    public suspend fun deleteEssenceMessage(messageId: Long) {
        this.send(DeleteEssenceMessageApi(params = DeleteEssenceMessageApi.Params(messageId)).toJson())
    }

    /**
     * 用于设置某个消息为已读, 就是让消息列表的红点消失
     */
    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    public suspend fun markAsRead(messageId: Long) {
        this.send(MarkAsReadApi(params = MarkAsReadApi.Params(messageId)).toJson())
    }

    /**
     * 用于获取群聊的Honor信息
     */
    @JvmBlocking(suffix = "JvmBlocking")
    public suspend fun getGroupHonorInfo(groupId: Long, type: HonorType): HonorInfo.HonorInfo {
        val uuid = Uuid.random()
        val deferred = this.createCompletableDeferred(uuid)
        this.send(GetGroupHonorInfoApi(params = GetGroupHonorInfoApi.Params(groupId, type.type), echo = uuid).toJson())
        val response = deferred.await()
        return response.fromJson<HonorInfo>().data
    }

    /**
     * 用于获取CSRF Token
     */
    @JvmBlocking(suffix = "JvmBlocking")
    public suspend fun getCSRFToken(): String {
        val uuid = Uuid.random()
        val deferred = this.createCompletableDeferred(uuid)
        this.send(GetCSRFTokenApi(echo = uuid).toJson())
        val response = deferred.await()
        return response.fromJson<CSRFToken>().data.token
    }

    /**
     * 用于获取群聊中某个消息ID之前的历史聊天记录
     * 默认只获取20条聊天记录
     * @param messageId 获取这条消息上面的若干条消息
     * @param count 获取消息的数量
     */
    @JvmOverloads
    @JvmBlocking(suffix = "JvmBlocking")
    public suspend fun getGroupMessageHistory(
        groupId: Long,
        messageId: Long,
        count: Int = 20
    ): GroupMessageHistory.MessageHistory {
        val uuid = Uuid.random()
        val deferred = this.createCompletableDeferred(uuid)
        this.send(
            GetGroupMessageHistoryApi(
                params = GetGroupMessageHistoryApi.Params(groupId, messageId, count),
                echo = uuid
            ).toJson()
        )
        val response = deferred.await()
        val serializedResponse = response.fromJson<GroupMessageHistory>()
        serializedResponse.data.messages.forEach {
            val oldSender = it.sender
            val newSenderWithGroupId = GroupSender(
                oldSender.userId, oldSender.nickname,
                oldSender.sex, oldSender.role, oldSender.card,
                oldSender.level, oldSender.age, oldSender.title, groupId
            ).apply { action = this@OneBotAction }
            it.sender = newSenderWithGroupId
        }
        return serializedResponse.data
    }

    /**
     * 用于获取私聊中某个消息ID之前的历史聊天记录
     * 默认只获取20条聊天记录
     * @param messageId 获取这条消息上面的若干条消息
     * @param count 获取消息的数量
     */
    @JvmOverloads
    @JvmBlocking(suffix = "JvmBlocking")
    public suspend fun getPrivateMessageHistory(
        userId: Long,
        messageId: Long,
        count: Int = 20
    ): PrivateMessageHistory.MessageHistory {
        val uuid = Uuid.random()
        val deferred = this.createCompletableDeferred(uuid)
        this.send(
            GetPrivateMessageHistoryApi(
                params = GetPrivateMessageHistoryApi.Params(userId, messageId, count),
                echo = uuid
            ).toJson()
        )
        val response = deferred.await()
        return response.fromJson<PrivateMessageHistory>().data
    }

    /**
     * 用于获取一个合并转发消息链中的内容
     */
    @JvmBlocking(suffix = "JvmBlocking")
    public suspend fun getForwardMessage(id: String): ForwardMessage.ForwardMessage {
        val uuid = Uuid.random()
        val deferred = this.createCompletableDeferred(uuid)
        this.send(GetForwardMessageApi(params = GetForwardMessageApi.Params(id), uuid).toJson())
        val response = deferred.await()
        return response.fromJson<ForwardMessage>().data
    }

    /**
     * 获取OneBOt实现的状态
     * 部分额外字段由Lagrange.OneBot实现
     */
    @JvmBlocking(suffix = "JvmBlocking")
    public suspend fun getStatus(): RawHeartBeatEvent.Status {
        val uuid = Uuid.random()
        val deferred = this.createCompletableDeferred(uuid)
        this.send(GetStatusApi(echo = uuid).toJson())
        val response = deferred.await()
        return response.fromJson<OneBotStatus>().data
    }

    /**
     * 用于获取机器人账号对应某个域名的Cookie
     * 可以传入`vip.qq.com` `docs.qq.com`等等一系列域名
     */
    @JvmBlocking(suffix = "JvmBlocking")
    public suspend fun getCookies(domain: String): String {
        val uuid = Uuid.random()
        val deferred = this.createCompletableDeferred(uuid)
        this.send(GetCookiesApi(params = GetCookiesApi.Params(domain), echo = uuid).toJson())
        val response = deferred.await()
        return response.fromJson<GetCookies>().data.cookies
    }

    /**
     * 重启OneBot实现
     */
    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    public suspend fun setRestart() {
        this.send(SetRestartApi().toJson())
    }

    /**
     * 清除OneBot缓存
     */
    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    public suspend fun cleanCache() {
        this.send(CleanCacheApi().toJson())
    }

    /**
     * 调用框架中没有定义的api端点, 并且异步调用无返回值,
     * 传入api端点以及参数
     */
    @JvmAsync(suffix = "JvmAsync")
    public suspend fun callApiAsync(endpoint: String, params: Map<String, String>, echo: Uuid = Uuid.random()) {
        this.send(CallAPIApi(endpoint, params, echo).toJson())
    }

    /**
     * 调用框架中没有定义的api端点, 同步调用有返回值,
     * 返回一个JSON String,传入api端点以及参数
     */
    @JvmBlocking(suffix = "JvmBlocking")
    public suspend fun callApi(endpoint: String, params: Map<String, String>): String {
        val uuid = Uuid.random()
        val deferred = this.createCompletableDeferred(uuid)
        this.callApiAsync(endpoint, params, uuid)
        val response = deferred.await()
        return response
    }

    /**
     * 该方法是Lagrange.OneBot的拓展API
     * 用于上传一个图片到QQ图床中, 可以为base64
     * 如果传入base64不能附带base64图片前缀
     * 例如`data:image/png;base64`
     */
    @JvmOverloads
    @JvmBlocking(suffix = "JvmBlocking")
    public suspend fun uploadImage(image: String, base64: Boolean = false): String {
        val uuid = Uuid.random()
        val deferred = this.createCompletableDeferred(uuid)
        val file = if (base64) "base64://$image" else image
        this.send(UploadImageApi(UploadImageApi.Params(file), echo = uuid).toJson())
        val response = deferred.await()
        return response.fromJson<UploadImage>().data
    }

    /**
     * 用于设置机器人的头像, 如果传入的是base64则
     * 不能有base64前缀
     */
    @JvmOverloads
    @JvmBlocking(suffix = "JvmBlocking")
    public suspend fun setBotAvatar(image: String, base64: Boolean = false): Boolean {
        val uuid = Uuid.random()
        val deferred = this.createCompletableDeferred(uuid)
        val file = if (base64) "base64://$image" else image
        this.send(SetBotAvatarApi(SetBotAvatarApi.Params(file), echo = uuid).toJson())
        val response = deferred.await()
        return response.fromJson<SetBotAvatar>().status != "failed"
    }

    /**
     * 用于获取mface的key(mface指的是商城里的表情包)
     * 传入一个字符串列表返回一个字符串列表
     */
    @JvmBlocking(suffix = "JvmBlocking")
    public suspend fun fetchMFaceKey(emojiIds: List<String>): List<String> {
        val uuid = Uuid.random()
        val deferred = this.createCompletableDeferred(uuid)
        this.send(FetchMFaceKeyApi(FetchMFaceKeyApi.Params(emojiIds), echo = uuid).toJson())
        val response = deferred.await()
        return response.fromJson<FetchMFaceKey>().data
    }

    /**
     * 用于设置群聊的头像不能以base64的方式传入
     * @param image 头像URL
     */
    @JvmBlocking(suffix = "JvmBlocking")
    public suspend fun setGroupAvatar(groupId: Long, image: String): Boolean {
        val uuid = Uuid.random()
        val deferred = this.createCompletableDeferred(uuid)
        this.send(SetGroupAvatarApi(SetGroupAvatarApi.Params(groupId, image), echo = uuid).toJson())
        val response = deferred.await()
        return response.fromJson<SetGroupAvatar>().status != "failed"
    }

    /**
     * 使用OCR识别图片的文字并且获取所在的坐标位置
     */
    @JvmOverloads
    @JvmBlocking(suffix = "JvmBlocking")
    public suspend fun ocrImage(image: String, base64: Boolean = false): OCRImage.ORCResult? {
        val uuid = Uuid.random()
        val deferred = this.createCompletableDeferred(uuid)
        this.send(OCRImageApi(OCRImageApi.Params(if (base64) "base64://$image" else image), echo = uuid).toJson())
        val response = deferred.await()
        return response.fromJson<OCRImage>().data
    }

    /**
     * 用于设置Bot自身的在线状态
     */
    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    @OneBot11CompatibilityApi
    public suspend fun setOnlineStatus(status: OnlineStatus) {
        this.send(SetOnlineStatusApi(SetOnlineStatusApi.Params(status.statusCode)).toJson())
    }

    /**
     * 用于获取带分组的好友列表
     */
    @JvmBlocking(suffix = "JvmBlocking")
    @OneBot11CompatibilityApi
    public suspend fun getFriendsWithCategory(): List<GetFriendWithCategory.FriendCategory> {
        val uuid = Uuid.random()
        val deferred = this.createCompletableDeferred(uuid)
        this.send(GetFriendWithCategoryApi(echo = uuid).toJson())
        val response = deferred.await()
        return response.fromJson<GetFriendWithCategory>().data
    }

    /**
     * 用于获取已过滤的加群请求通知
     */
    @JvmBlocking(suffix = "JvmBlocking")
    @OneBot11CompatibilityApi
    public suspend fun getGroupIgnoreAddRequest(): List<GroupIgnoreAddRequest.Request> {
        val uuid = Uuid.random()
        val deferred = this.createCompletableDeferred(uuid)
        this.send(GetGroupIgnoreAddRequestApi(echo = uuid).toJson())
        val response = deferred.await()
        return response.fromJson<GroupIgnoreAddRequest>().data
    }

    /**
     * 用于获取Bot是否可以@全体以及@全体剩余的次数
     */
    @JvmBlocking(suffix = "JvmBlocking")
    @OneBot11CompatibilityApi
    public suspend fun getGroupAtAllRemain(groupId: Long): GroupAtAllRemain.AtAllRemain {
        val uuid = Uuid.random()
        val deferred = this.createCompletableDeferred(uuid)
        this.send(GetGroupAtAllRemainApi(GetGroupAtAllRemainApi.Params(groupId), echo = uuid).toJson())
        val response = deferred.await()
        return response.fromJson<GroupAtAllRemain>().data
    }

    /**
     * 用于删除好友操作
     * @param block 是否拉黑并且拒绝此人加好友
     */
    @JvmOverloads
    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    public suspend fun deleteFriend(userId: Long, block: Boolean = true) {
        this.send(DeleteFriendApi(DeleteFriendApi.Params(userId, block)).toJson())
    }

    /**
     * 用于获取群文件系统信息
     * 例如当前使用了多少空间以及总共有多少空间可以使用
     * 还可以获取总共有几个文件和总共能放下多少个文件
     */
    @JvmBlocking(suffix = "JvmBlocking")
    public suspend fun getGroupFileSystemInfo(groupId: Long): GroupFileSystemInfo.FileSystemInfo {
        val uuid = Uuid.random()
        val deferred = this.createCompletableDeferred(uuid)
        this.send(GetGroupFileSystemInfoApi(GetGroupFileSystemInfoApi.Params(groupId), echo = uuid).toJson())
        val response = deferred.await()
        return response.fromJson<GroupFileSystemInfo>().data
    }

    /**
     * 用于创建群文件中的文件夹
     */
    @JvmOverloads
    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    public suspend fun createGroupFileFolder(groupId: Long, name: String, parentId: String = "/") {
        this.send(CreateGroupFileFolderApi(CreateGroupFileFolderApi.Params(groupId, name, parentId)).toJson())
    }

    /**
     * 用于删除群文件中的文件夹
     */
    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    public suspend fun deleteGroupFileFolder(groupId: Long, folderId: String) {
        this.send(DeleteGroupFolderApi(DeleteGroupFolderApi.Params(groupId, folderId)).toJson())
    }

    /**
     * 用于获取官方机器人的UIN范围
     */
    @JvmBlocking(suffix = "JvmBlocking")
    @OneBot11CompatibilityApi
    public suspend fun getRobotUinRange(): List<RobotUinRange.UinRange> {
        val uuid = Uuid.random()
        val deferred = this.createCompletableDeferred(uuid)
        this.send(GetRobotUinRangeApi(echo = uuid).toJson())
        val response = deferred.await()
        return response.fromJson<RobotUinRange>().data
    }

    /**
     * 用于获取AI声聊的语音类型
     * [chatType]只能传1u
     */
    @JvmOverloads
    @JvmBlocking(suffix = "JvmBlocking")
    public suspend fun getAIRecordCharacters(groupId: Long, chatType: UInt = 1u): AIRecordCharacters {
        val uuid = Uuid.random()
        val deferred = this.createCompletableDeferred(uuid)
        this.send(
            GetAIRecordCharactersApi(
                params = GetAIRecordCharactersApi.Params(groupId, chatType),
                echo = uuid
            ).toJson()
        )
        val response = deferred.await()
        return response.fromJson<AIRecordCharacters>()
    }

    /**
     * 用于生成指定音色的AI声音, 传入[text], [groupId], [character]后可以生成
     * [character]是[getAIRecordCharacters]返回的[AIRecordCharacters.Character.characterId]
     * [chatType]只能传1u
     * 如果生成失败则返回null
     */
    @JvmOverloads
    @JvmBlocking(suffix = "JvmBlocking")
    public suspend fun getAIRecord(groupId: Long, character: String, text: String, chatType: UInt = 1u): String? {
        val uuid = Uuid.random()
        val deferred = this.createCompletableDeferred(uuid)
        this.send(
            GetAIRecordAndSendRecordApi(
                params = GetAIRecordAndSendRecordApi.Params(
                    chatType, text, groupId, character
                ), echo = uuid, action = "get_ai_record"
            ).toJson()
        )
        val response = deferred.await().fromJson<AIRecord>()
        return if (response.status == ActionStatus.failed) null else response.data
    }

    /**
     * 用于生成指定音色的AI声音
     * 但是使用了已知的[character] ([AIRecordCharacter])枚举类来发送
     */
    @JvmOverloads
    @JvmBlocking(suffix = "JvmBlocking")
    public suspend fun getAIRecord(
        groupId: Long,
        character: AIRecordCharacter,
        text: String,
        chatType: UInt = 1u
    ): String? {
        return this.getAIRecord(groupId, character.characterId, text, chatType)
    }

    /**
     * 用于直接向群内发送指定音色的AI声音, 传入[text], [groupId], [character]后可以生成
     * [character]是[getAIRecordCharacters]返回的[AIRecordCharacters.Character.characterId]
     * [chatType]只能传1u
     */
    @JvmOverloads
    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    public suspend fun sendGroupAIRecord(groupId: Long, character: String, text: String, chatType: UInt = 1u) {
        this.send(
            GetAIRecordAndSendRecordApi(
                params = GetAIRecordAndSendRecordApi.Params(
                    chatType, text, groupId, character
                ), echo = Uuid.random(), action = "send_group_ai_record"
            ).toJson()
        )
    }

    /**
     * 用于生成指定音色的AI声音
     * 但是使用了已知的[character] ([AIRecordCharacter])枚举类来发送
     */
    @JvmOverloads
    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    public suspend fun sendGroupAIRecord(
        groupId: Long,
        character: AIRecordCharacter,
        text: String,
        chatType: UInt = 1u
    ) {
        this.sendGroupAIRecord(groupId, character.characterId, text, chatType)
    }

    /**
     * 用于设置Bot的个性签名
     */
    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    @OneBot11CompatibilityApi
    public suspend fun setLongNick(longNick: String) {
        val payload = SetSelfLongNickApi(params = SetSelfLongNickApi.Params(longNick))
        this.send(payload.toJson())
    }

    /**
     * 用于创建收藏
     */
    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    @OneBot11CompatibilityApi
    public suspend fun createCollection(brief: String, rawData: String) {
        val payload = CreateCollectionApi(CreateCollectionApi.Params(brief, rawData))
        this.send(payload.toJson())
    }

    /**
     * 用于获取点赞列表
     */
    @JvmBlocking(suffix = "JvmBlocking")
    @OneBot11CompatibilityApi
    public suspend fun getProfileLike(): GetProfileLike.ProfileLike {
        val uuid = Uuid.random()
        val deferred = this.createCompletableDeferred(uuid)
        val payload = GetProfileLikeApi(echo = uuid)
        this.send(payload.toJson())
        val response = deferred.await()
        return response.fromJson<GetProfileLike>().data
    }

    /**
     * 用于签名一个小程序卡片
     * @param type 卡片签名类别
     * @param title 卡片标题
     * @param description 转发卡片内的描述
     * @param picUrl 封面URL
     * @param jumpUrl 点击后跳转的URL
     * @param iconUrl 图标URL
     * @param sdkId SDKID
     * @param appId 小程序ID
     */
    @JvmOverloads
    @JvmBlocking(suffix = "JvmBlocking")
    @OneBot11CompatibilityApi
    public suspend fun getMiniAppArk(
        type: MiniAppArkType,
        title: String,
        description: String,
        picUrl: String,
        jumpUrl: String,
        iconUrl: String? = null,
        sdkId: String? = null,
        appId: String? = null
    ): String {
        val uuid = Uuid.random()
        val deferred = this.createCompletableDeferred(uuid)
        val payload = GetMiniAppArkApi(
            GetMiniAppArkApi.Params(
                type.name,
                title,
                description,
                picUrl,
                jumpUrl,
                iconUrl,
                sdkId,
                appId,
            ), echo = uuid
        )
        this.send(payload.toJson())
        val response = deferred.await()
        return response
    }

    /**
     * 用于接龙表情
     * @param emojiId 表情ID
     */
    @JvmBlocking(suffix = "JvmBlocking")
    public suspend fun joinFriendFaceChain(userId: Long, messageId: Long, emojiId: Int): String {
        val uuid = Uuid.random()
        val deferred = this.createCompletableDeferred(uuid)
        val payload = JoinFriendEmojiChainApi(JoinFriendEmojiChainApi.Params(messageId, emojiId, userId), echo = uuid)
        this.send(payload.toJson())
        return deferred.await()
    }

    /**
     * 使用QQFace来接龙
     * @param emojiId 表情ID但是是[QQFace]对象
     */
    @JvmBlocking(suffix = "JvmBlocking")
    public suspend fun joinFriendFaceChain(userId: Long, messageId: Long, emojiId: QQFace): String {
        return this.joinFriendFaceChain(userId, messageId, emojiId.id)
    }

    /**
     * 这是一个用于获取RKey的API
     * rkey通常用于图片等资源
     * 有了rkey才能正常的下载来自QQ服务器的图片
     */
    @JvmBlocking(suffix = "JvmBlocking")
    public suspend fun getRKey(): GetRKey {
        val uuid = Uuid.random()
        val deferred = this.createCompletableDeferred(uuid)
        val payload = GetRKeyApi(echo = uuid)
        this.send(payload.toJson())
        return deferred.await().fromJson<GetRKey>()
    }

    /**
     * 设置群Bot发言状态
     * @param botId 机器人的QQ号
     * @param enable 是否开启
     */
    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    public suspend fun setGroupBotStatus(groupId: Long, botId: Long, enable: Boolean) {
        this.send(SetGroupBotStatusApi(params = SetGroupBotStatusApi.Params(groupId, botId, enable)).toJson())
    }

    /**
     * 获取加群通知
     */
    @JvmBlocking(suffix = "JvmBlocking")
    public suspend fun getGroupRequests(): List<GetGroupRequests.GroupRequests> {
        val uuid = Uuid.random()
        val deferred = this.createCompletableDeferred(uuid)
        this.send(GetGroupRequestsApi(uuid).toJson())
        val response = deferred.await()
        return response.fromJson<GetGroupRequests>().data
    }

    /**
     * 群打卡
     * Napcat
     */
    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    @OneBot11CompatibilityApi
    public suspend fun setGroupSign(groupId: Long) {
        this.send(SetGroupSignApi(params = SetGroupSignApi.Params(groupId)).toJson())
    }

    /**
     * 获取群聊推荐卡片
     */
    @JvmBlocking(suffix = "JvmBlocking")
    @OneBot11CompatibilityApi
    public suspend fun arkSharePeerGroup(groupId: Long): ArkSharePeerResponse.ArkSharePeer {
        val uuid = Uuid.random()
        val deferred = this.createCompletableDeferred(uuid)
        this.send(ArkSharePeerApi(params = ArkSharePeerApi.Params(null, groupId), echo = uuid).toJson())
        return deferred.await().fromJson<ArkSharePeerResponse>().data
    }

    /**
     * 获取好友推荐卡片, 但是通过qq号获取
     */
    @JvmBlocking(suffix = "JvmBlocking")
    @OneBot11CompatibilityApi
    public suspend fun arkSharePeerFriend(userId: Long): ArkSharePeerFriendResponse.ArkSharePeerFriend {
        val uuid = Uuid.random()
        val deferred = this.createCompletableDeferred(uuid)
        this.send(ArkSharePeerApi(params = ArkSharePeerApi.Params(userId, null), echo = uuid).toJson())
        return deferred.await().fromJson<ArkSharePeerFriendResponse>().data
    }

    /**
     * 获取推荐群聊卡片
     */
    @JvmBlocking(suffix = "JvmBlocking")
    @OneBot11CompatibilityApi
    public suspend fun arkShareGroup(groupId: Long): String {
        val uuid = Uuid.random()
        val deferred = this.createCompletableDeferred(uuid)
        this.send(ArkShareGroupApi(params = ArkShareGroupApi.Params(groupId), echo = uuid).toJson())
        return deferred.await().fromJson<ArkShareGroupResponse>().data
    }

    /**
     * 将消息转发给好友
     * @param messageId 原消息ID
     * @param userId 目标好友QQ号
     */
    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    @OneBot11CompatibilityApi
    public suspend fun forwardFriendSingleMessage(messageId: Long, userId: Long) {
        this.send(ForwardFriendSingleMsgApi(params = ForwardFriendSingleMsgApi.Params(messageId, userId)).toJson())
    }

    /**
     * 将消息转发到群聊
     * @param messageId 原消息ID
     * @param groupId 目标群号
     */
    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    @OneBot11CompatibilityApi
    public suspend fun forwardGroupSingleMessage(messageId: Long, groupId: Long) {
        this.send(ForwardGroupSingleMsgApi(params = ForwardGroupSingleMsgApi.Params(messageId, groupId)).toJson())
    }

    /**
     * 将英文翻译成中文
     * @param words 英文字符串列表
     * @return 翻译后的中文字符串列表
     */
    @JvmBlocking(suffix = "JvmBlocking")
    @OneBot11CompatibilityApi
    public suspend fun translateEN2ZH(words: List<String>): List<String> {
        val uuid = Uuid.random()
        val deferred = this.createCompletableDeferred(uuid)
        this.send(TranslateEN2ZHApi(params = TranslateEN2ZHApi.Params(words), echo = uuid).toJson())
        return deferred.await().fromJson<TranslateEN2ZHResponse>().data
    }

    /**
     * 设置好友备注
     * @param userId 要设置的好友的QQ号
     * @param remark 备注信息, 设置为`null`则表示恢复成原本的名字
     */
    @JvmOverloads
    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    @OneBot11CompatibilityApi
    public suspend fun setFriendRemark(userId: Long, remark: String? = null) {
        this.send(SetFriendRemarkApi(params = SetFriendRemarkApi.Params(userId, remark)).toJson())
    }

    /**
     * 设置好友分组
     * @param userId 要设置的好友QQ号
     * @param categoryId 分组ID 可以通过[getFriendsWithCategory]API获取
     */
    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    @OneBot11CompatibilityApi
    public suspend fun setFriendCategory(userId: Long, categoryId: String) {
        this.send(SetFriendCategoryApi(params = SetFriendCategoryApi.Params(userId, categoryId)).toJson())
    }

    /**
     * 设置表情回应
     * @param emojiId 表情ID
     */
    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    @OneBot11CompatibilityApi
    public suspend fun setMessageEmojiLike(messageId: Long, emojiId: Int) {
        this.send(SetMsgEmojiLikeApi(params = SetMsgEmojiLikeApi.Params(messageId, emojiId)).toJson())
    }

    /**
     * 设置表情回应
     * @param emoji [QQFace]枚举类
     */
    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    @OneBot11CompatibilityApi
    public suspend fun setMessageEmojiLike(messageId: Long, emoji: QQFace) {
        this.setMessageEmojiLike(messageId, emoji.id)
    }

    /**
     * 设置群聊备注
     * @param remark 备注信息
     */
    @JvmOverloads
    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    @OneBot11CompatibilityApi
    public suspend fun setGroupRemark(groupId: Long, remark: String? = null) {
        this.send(SetGroupRemarkApi(params = SetGroupRemarkApi.Params(groupId, remark)).toJson())
    }

    /**
     * 获取群聊被禁言的用户列表
     */
    @JvmBlocking(suffix = "JvmBlocking")
    @OneBot11CompatibilityApi
    public suspend fun getGroupShutList(groupId: Long): List<GroupShutListResponse.GroupShutListUser> {
        val uuid = Uuid.random()
        val deferred = this.createCompletableDeferred(uuid)
        this.send(GetGroupShutListApi(params = GetGroupShutListApi.Params(groupId), echo = uuid).toJson())
        return deferred.await().fromJson<GroupShutListResponse>().data
    }

    /**
     * 获取文件
     * @param fileId 文件的ID
     */
    @JvmBlocking(suffix = "JvmBlocking")
    @OneBot11CompatibilityApi
    public suspend fun getFile(fileId: String): GetFileResponse.GetFileResponseInfo {
        val uuid = Uuid.random()
        val deferred = this.createCompletableDeferred(uuid)
        this.send(GetFileApi(params = GetFileApi.Params(fileId), echo = uuid).toJson())
        return deferred.await().fromJson<GetFileResponse>().data
    }

    /**
     * 获取私聊中发送的文件下载地址
     * @param userId 对方QQ号
     * @param fileId 文件ID
     * @param fileHash 文件哈希值 可以不传
     * @return 文件的下载地址URL
     */
    @JvmOverloads
    @JvmBlocking(suffix = "JvmBlocking")
    public suspend fun getPrivateFileUrl(userId: Long, fileId: String, fileHash: String = ""): String {
        val uuid = Uuid.random()
        val deferred = this.createCompletableDeferred(uuid)
        this.send(
            GetPrivateFileUrlApi(
                param = GetPrivateFileUrlApi.Params(userId, fileHash, fileId),
                echo = uuid
            ).toJson()
        )
        return deferred.await().fromJson<GetPrivateFileUrl>().data.url
    }

    /**
     * 用于发送一个自定义的数据包
     * @param data 不知道
     * @param command 不知道
     * @param sign 是否签名
     * @param type 不知道
     */
    @JvmOverloads
    @JvmBlocking(suffix = "JvmBlocking")
    @InternalOneBot11Api
    public suspend fun sendPacket(
        data: String,
        command: String,
        sign: Boolean,
        type: Byte = 12
    ): SendPacketResponse.SendPacket {
        val uuid = Uuid.random()
        val deferred = this.createCompletableDeferred(uuid)
        this.send(_SendPacketApi(params = _SendPacketApi.Params(data, command, sign, type), echo = uuid).toJson())
        return deferred.await().fromJson<SendPacketResponse>().data
    }

    /**
     * 用于异步发送一个自定义的数据包
     * @param data 不知道
     * @param command 不知道
     * @param sign 是否签名
     * @param type 不知道
     */
    @JvmOverloads
    @JvmAsync(suffix = "JvmAsync")
    @InternalOneBot11Api
    public suspend fun sendPacketAsync(data: String, command: String, sign: Boolean, type: Byte = 12) {
        this.send(
            _SendPacketApi(
                params = _SendPacketApi.Params(data, command, sign, type),
                echo = Uuid.random()
            ).toJson()
        )
    }
}