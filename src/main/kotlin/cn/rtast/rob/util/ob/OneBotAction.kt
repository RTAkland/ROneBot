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
import cn.rtast.rob.entity.metadata.OneBotVersionInfo
import cn.rtast.rob.entity.out.*
import cn.rtast.rob.enums.MessageEchoType
import cn.rtast.rob.util.fromJson
import cn.rtast.rob.util.toJson
import kotlinx.coroutines.CompletableDeferred

interface OneBotAction {

    /**
     * 懒狗写法
     */
    private fun sendToWs(message: Any) {
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
     * 向一个群聊中发送一段纯文本消息
     */
    suspend fun sendGroupMessage(groupId: Long, content: String) {
        this.sendToWs(CQCodeGroupMessageOut(params = CQCodeGroupMessageOut.Params(groupId, content)))
    }

    /**
     * 向所有群聊中发送MessageChain消息链消息
     * 所有群聊指ROneBotFactory中设置的监听群号
     * 如果没有设置则此方法以及重载方法将毫无作用
     */
    suspend fun broadcastMessage(content: MessageChain) {
        ROneBotFactory.getListeningGroups().forEach {
            this.sendGroupMessage(it, content)
        }
    }

    /**
     * 向所有监听的群聊发送一条纯文本消息
     */
    suspend fun broadcastMessage(content: String) {
        ROneBotFactory.getListeningGroups().forEach {
            this.sendGroupMessage(it, content)
        }
    }

    /**
     * 向所有监听的群聊发送一条CQMessageChain消息
     */
    suspend fun broadcastMessage(content: CQMessageChain) {
        ROneBotFactory.getListeningGroups().forEach {
            this.sendGroupMessage(it, content)
        }
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
        this.sendToWs(ArrayGroupMessageOut(params = ArrayGroupMessageOut.Params(groupId, content.finalArrayMsgList)))
    }

    /**
     * 发送群组消息但是是服务器返回的消息类型
     */
    suspend fun sendGroupMessage(groupId: Long, content: List<ArrayMessage>) {
        this.sendToWs(RawArrayGroupMessageOut(params = RawArrayGroupMessageOut.Params(groupId, content)))
    }

    /**
     * 发送私聊消息但是是纯文本
     */
    suspend fun sendPrivateMessage(userId: Long, content: String) {
        this.sendToWs(CQCodePrivateMessageOut(params = CQCodePrivateMessageOut.Params(userId, content)))
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
        this.sendToWs(ArrayPrivateMessageOut(params = ArrayPrivateMessageOut.Params(userId, content.finalArrayMsgList)))
    }

    /**
     * 发送私聊消息但是是服务器返回的消息类型
     */
    suspend fun sendPrivateMessage(userId: Long, content: List<ArrayMessage>) {
        this.sendToWs(RawArrayPrivateMessageOut(params = RawArrayPrivateMessageOut.Params(userId, content)))
    }

    /**
     * 撤回消息(recall/revoke)
     */
    suspend fun revokeMessage(messageId: Long) {
        this.sendToWs(RevokeMessageOut(params = RevokeMessageOut.Params(messageId)))
    }

    /**
     * 为某人的卡片点赞
     */
    suspend fun sendLike(userId: Long, times: Int = 1) {
        this.sendToWs(SendLikeOut(params = SendLikeOut.Params(userId, times)))
    }

    /**
     * 将成员踢出群聊
     */
    suspend fun kickGroupMember(groupId: Long, userId: Long, rejectJoinRequest: Boolean = false) {
        this.sendToWs(KickGroupMemberOut(params = KickGroupMemberOut.Params(groupId, userId, rejectJoinRequest)))
    }

    /**
     * 设置单个成员的禁言
     */
    suspend fun setGroupBan(groupId: Long, userId: Long, duration: Int = 1800) {
        this.sendToWs(SetGroupBanOut(params = SetGroupBanOut.Params(groupId, userId, duration)))
    }

    /**
     * 设置全员禁言
     */
    suspend fun setGroupWholeBan(groupId: Long, enable: Boolean = true) {
        this.sendToWs(SetGroupWholeBanOut(params = SetGroupWholeBanOut.Params(groupId, enable)))
    }

    /**
     * 设置群组管理员
     */
    suspend fun setGroupAdmin(groupId: Long, userId: Long, enable: Boolean = true) {
        this.sendToWs(SetGroupAdminOut(params = SetGroupAdminOut.Params(groupId, userId, enable)))
    }

    /**
     * 设置是否可以匿名聊天
     */
    suspend fun setGroupAnonymous(groupId: Long, enable: Boolean = true) {
        this.sendToWs(SetGroupAnonymousOut(params = SetGroupAnonymousOut.Params(groupId, enable)))
    }

    /**
     * 设置成群员的群昵称
     */
    suspend fun setGroupMemberCard(groupId: Long, userId: Long, card: String = "") {
        this.sendToWs(SetGroupMemberCardOut(params = SetGroupMemberCardOut.Params(groupId, userId, card)))
    }

    /**
     * 设置群组名称
     */
    suspend fun setGroupName(groupId: Long, groupName: String) {
        this.sendToWs(SetGroupNameOut(params = SetGroupNameOut.Params(groupId, groupName)))
    }

    /**
     * 推出群聊,如果是群主并且dismiss为true则解散群聊
     */
    suspend fun setGroupLeaveOrDismiss(groupId: Long, dismiss: Boolean = false) {
        this.sendToWs(SetGroupLeaveOut(params = SetGroupLeaveOut.Params(groupId, dismiss)))
    }

    /**
     * 设置群组成员专属头衔
     */
    suspend fun setGroupMemberTitle(groupId: Long, userId: Long, title: String = "", duration: Int = -1) {
        this.sendToWs(SetGroupMemberTitleOut(params = SetGroupMemberTitleOut.Params(groupId, userId, title, duration)))
    }

    /**
     * 处理加好友请求
     */
    suspend fun setFriendRequest(flag: String, approve: Boolean = true, remark: String = "") {
        this.sendToWs(SetFriendRequestOut(params = SetFriendRequestOut.Params(flag, approve, remark)))
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
        this.sendToWs(SetGroupRequestOut(params = SetGroupRequestOut.Params(flag, type, approve, reason)))
    }

    /**
     * 根据消息ID获取一条消息
     */
    suspend fun getMessage(messageId: Long): GetMessage.Data {
        val deferred = this.createCompletableDeferred(MessageEchoType.GetMessage)
        this.sendToWs(GetMessageOut(params = GetMessageOut.Params(messageId)))
        val response = deferred.await()
        return response.fromJson<GetMessage>().data
    }

    /**
     * 获取账号登录信息
     */
    suspend fun getLoginInfo(): LoginInfo.Data {
        val deferred = this.createCompletableDeferred(MessageEchoType.GetLoginInfo)
        this.sendToWs(GetLoginInfoOut())
        val response = deferred.await()
        return response.fromJson<LoginInfo>().data
    }

    /**
     * 获取陌生人信息
     */
    suspend fun getStrangerInfo(userId: Long, noCache: Boolean = false): StrangerInfo.Data {
        val deferred = this.createCompletableDeferred(MessageEchoType.GetStrangerInfo)
        this.sendToWs(GetStrangerInfoOut(params = GetStrangerInfoOut.Params(userId, noCache)))
        val response = deferred.await()
        return response.fromJson<StrangerInfo>().data
    }

    /**
     * 获取好友列表
     */
    suspend fun getFriendList(): List<FriendList.Data> {
        val deferred = this.createCompletableDeferred(MessageEchoType.GetFriendList)
        this.sendToWs(GetFriendListOut())
        val response = deferred.await()
        return response.fromJson<FriendList>().data
    }

    /**
     * 获取群组信息
     */
    suspend fun getGroupInfo(groupId: Long, noCache: Boolean = false): GroupInfo.Data {
        val deferred = this.createCompletableDeferred(MessageEchoType.GetGroupInfo)
        this.sendToWs(GetGroupInfoOut(params = GetGroupInfoOut.Params(groupId, noCache)))
        val response = deferred.await()
        return response.fromJson<GroupInfo>().data
    }

    /**
     * 获取账号的群组列表
     */
    suspend fun getGroupList(): List<GroupList.Data> {
        val deferred = this.createCompletableDeferred(MessageEchoType.GetGroupList)
        this.sendToWs(GetGroupListOut())
        val response = deferred.await()
        return response.fromJson<GroupList>().data
    }

    /**
     * 获取群组成员信息
     */
    suspend fun getGroupMemberInfo(groupId: Long, userId: Long, noCache: Boolean = false): GroupMemberList.Data {
        val deferred = this.createCompletableDeferred(MessageEchoType.GetGroupMemberInfo)
        this.sendToWs(GetGroupMemberInfoOut(params = GetGroupMemberInfoOut.Params(groupId, userId, noCache)))
        val response = deferred.await()
        return response.fromJson<GroupMemberInfo>().data
    }

    /**
     * 获取群组成员列表
     */
    suspend fun getGroupMemberList(groupId: Long): List<GroupMemberList.Data> {
        val deferred = this.createCompletableDeferred(MessageEchoType.GetGroupMemberList)
        this.sendToWs(GetGroupMemberListOut(params = GetGroupMemberListOut.Params(groupId)))
        val response = deferred.await()
        return response.fromJson<GroupMemberList>().data
    }

    /**
     * 获取OneBot实现的版本信息
     */
    suspend fun getVersionInfo(): OneBotVersionInfo.Data {
        val deferred = this.createCompletableDeferred(MessageEchoType.GetVersionInfo)
        this.sendToWs(GetVersionInfo())
        val response = deferred.await()
        return response.fromJson<OneBotVersionInfo>().data
    }

    /**
     * 检查是否可以发送图片
     */
    suspend fun canSendImage(): Boolean {
        val deferred = this.createCompletableDeferred(MessageEchoType.CanSendImage)
        this.sendToWs(CanSendImageOut())
        val response = deferred.await()
        return response.fromJson<CanSend>().data.yes
    }

    /**
     * 检查是否可以发送语音
     * (感觉没什么用)
     */
    suspend fun canSendRecord(): Boolean {
        val deferred = this.createCompletableDeferred(MessageEchoType.CanSendRecord)
        this.sendToWs(CanSendRecordOut())
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
        this.sendToWs(FetchCustomFaceOut())
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
        this.sendToWs(SendGroupForwardMsg(params = SendGroupForwardMsg.Params(groupId, message.nodes)))
        val response = deferred.await()
        return response.fromJson<ForwardMessageId>().data
    }

    /**
     * 该方法是Lagrange.OneBot的拓展API
     * 用于发送私聊的合并转发消息链
     * 该方法有返回值返回forwardId
     */
    suspend fun sendPrivateForwardMsg(userId: Long, message: NodeMessageChain): ForwardMessageId.Data {
        val deferred = this.createCompletableDeferred(MessageEchoType.SendForwardMsg)
        this.sendToWs(SendPrivateForwardMsg(params = SendPrivateForwardMsg.Params(userId, message.nodes)))
        val response = deferred.await()
        return response.fromJson<ForwardMessageId>().data
    }

    /**
     * 该方法是Lagrange.OneBot的拓展方法
     * 用于发送私聊的戳一戳行为
     */
    suspend fun sendFriendPoke(userId: Long) {
        this.sendToWs(FriendPokeOut(params = FriendPokeOut.Params(userId)))
    }

    /**
     * 该方法是Lagrange.OneBot的拓展方法
     * 用于发送群聊的戳一戳行为
     */
    suspend fun sendGroupPoke(groupId: Long, userId: Long) {
        this.sendToWs(GroupPokeOut(params = GroupPokeOut.Params(groupId, userId)))
    }
}