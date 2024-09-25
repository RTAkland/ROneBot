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

    private fun sendToWs(message: Any) {
        if (isServer) {
            websocketServer?.connections?.forEach { it.send(message.toJson()) }
            return
        }
        websocket?.send(message.toJson())
    }

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

    suspend fun sendGroupMessage(groupId: Long, content: CQMessageChain) {
        this.sendGroupMessage(groupId, content.finalString)
    }

    suspend fun sendGroupMessage(groupId: Long, content: MessageChain) {
        this.sendToWs(ArrayGroupMessageOut(params = ArrayGroupMessageOut.Params(groupId, content.finalArrayMsgList)))
    }

    suspend fun sendGroupMessage(groupId: Long, content: List<ArrayMessage>) {
        this.sendToWs(RawArrayGroupMessageOut(params = RawArrayGroupMessageOut.Params(groupId, content)))
    }

    suspend fun sendPrivateMessage(userId: Long, content: String) {
        this.sendToWs(CQCodePrivateMessageOut(params = CQCodePrivateMessageOut.Params(userId, content)))
    }

    suspend fun sendPrivateMessage(userId: Long, content: CQMessageChain) {
        this.sendPrivateMessage(userId, content.finalString)
    }

    suspend fun sendPrivateMessage(userId: Long, content: MessageChain) {
        this.sendToWs(ArrayPrivateMessageOut(params = ArrayPrivateMessageOut.Params(userId, content.finalArrayMsgList)))
    }

    suspend fun sendPrivateMessage(userId: Long, content: List<ArrayMessage>) {
        this.sendToWs(RawArrayPrivateMessageOut(params = RawArrayPrivateMessageOut.Params(userId, content)))
    }

    suspend fun revokeMessage(messageId: Long) {
        this.sendToWs(RevokeMessageOut(params = RevokeMessageOut.Params(messageId)))
    }

    suspend fun sendLike(userId: Long, times: Int = 1) {
        this.sendToWs(SendLikeOut(params = SendLikeOut.Params(userId, times)))
    }

    suspend fun kickGroupMember(groupId: Long, userId: Long, rejectJoinRequest: Boolean = false) {
        this.sendToWs(KickGroupMemberOut(params = KickGroupMemberOut.Params(groupId, userId, rejectJoinRequest)))
    }

    suspend fun setGroupBan(groupId: Long, userId: Long, duration: Int = 1800) {
        this.sendToWs(SetGroupBanOut(params = SetGroupBanOut.Params(groupId, userId, duration)))
    }

    suspend fun setGroupWholeBan(groupId: Long, enable: Boolean = true) {
        this.sendToWs(SetGroupWholeBanOut(params = SetGroupWholeBanOut.Params(groupId, enable)))
    }

    suspend fun setGroupAdmin(groupId: Long, userId: Long, enable: Boolean = true) {
        this.sendToWs(SetGroupAdminOut(params = SetGroupAdminOut.Params(groupId, userId, enable)))
    }

    suspend fun setGroupAnonymous(groupId: Long, enable: Boolean = true) {
        this.sendToWs(SetGroupAnonymousOut(params = SetGroupAnonymousOut.Params(groupId, enable)))
    }

    suspend fun setGroupMemberCard(groupId: Long, userId: Long, card: String = "") {
        this.sendToWs(SetGroupMemberCardOut(params = SetGroupMemberCardOut.Params(groupId, userId, card)))
    }

    suspend fun setGroupName(groupId: Long, groupName: String) {
        this.sendToWs(SetGroupNameOut(params = SetGroupNameOut.Params(groupId, groupName)))
    }

    suspend fun setGroupLeaveOrDismiss(groupId: Long, dismiss: Boolean = false) {
        this.sendToWs(SetGroupLeaveOut(params = SetGroupLeaveOut.Params(groupId, dismiss)))
    }

    suspend fun setGroupMemberTitle(groupId: Long, userId: Long, title: String = "", duration: Int = -1) {
        this.sendToWs(SetGroupMemberTitleOut(params = SetGroupMemberTitleOut.Params(groupId, userId, title, duration)))
    }

    suspend fun setFriendRequest(flag: String, approve: Boolean = true, remark: String = "") {
        this.sendToWs(SetFriendRequestOut(params = SetFriendRequestOut.Params(flag, approve, remark)))
    }

    suspend fun setGroupRequest(
        flag: String,
        type: String,
        approve: Boolean = true,
        reason: String = ""  // only reject user to join group need to provide this param
    ) {
        this.sendToWs(SetGroupRequestOut(params = SetGroupRequestOut.Params(flag, type, approve, reason)))
    }

    suspend fun getMessage(messageId: Long): GetMessage.Data {
        val deferred = this.createCompletableDeferred(MessageEchoType.GetMessage)
        this.sendToWs(GetMessageOut(params = GetMessageOut.Params(messageId)))
        val response = deferred.await()
        return response.fromJson<GetMessage>().data
    }

    suspend fun getLoginInfo(): LoginInfo.Data {
        val deferred = this.createCompletableDeferred(MessageEchoType.GetLoginInfo)
        this.sendToWs(GetLoginInfoOut())
        val response = deferred.await()
        return response.fromJson<LoginInfo>().data
    }

    suspend fun getStrangerInfo(userId: Long, noCache: Boolean = false): StrangerInfo.Data {
        val deferred = this.createCompletableDeferred(MessageEchoType.GetStrangerInfo)
        this.sendToWs(GetStrangerInfoOut(params = GetStrangerInfoOut.Params(userId, noCache)))
        val response = deferred.await()
        return response.fromJson<StrangerInfo>().data
    }

    suspend fun getFriendList(): List<FriendList.Data> {
        val deferred = this.createCompletableDeferred(MessageEchoType.GetFriendList)
        this.sendToWs(GetFriendListOut())
        val response = deferred.await()
        return response.fromJson<FriendList>().data
    }

    suspend fun getGroupInfo(groupId: Long, noCache: Boolean = false): GroupInfo.Data {
        val deferred = this.createCompletableDeferred(MessageEchoType.GetGroupInfo)
        this.sendToWs(GetGroupInfoOut(params = GetGroupInfoOut.Params(groupId, noCache)))
        val response = deferred.await()
        return response.fromJson<GroupInfo>().data
    }

    suspend fun getGroupList(): List<GroupList.Data> {
        val deferred = this.createCompletableDeferred(MessageEchoType.GetGroupList)
        this.sendToWs(GetGroupListOut())
        val response = deferred.await()
        return response.fromJson<GroupList>().data
    }

    suspend fun getGroupMemberInfo(groupId: Long, userId: Long, noCache: Boolean = false): GroupMemberList.Data {
        val deferred = this.createCompletableDeferred(MessageEchoType.GetGroupMemberInfo)
        this.sendToWs(GetGroupMemberInfoOut(params = GetGroupMemberInfoOut.Params(groupId, userId, noCache)))
        val response = deferred.await()
        return response.fromJson<GroupMemberInfo>().data
    }

    suspend fun getGroupMemberList(groupId: Long): List<GroupMemberList.Data> {
        val deferred = this.createCompletableDeferred(MessageEchoType.GetGroupMemberList)
        this.sendToWs(GetGroupMemberListOut(params = GetGroupMemberListOut.Params(groupId)))
        val response = deferred.await()
        return response.fromJson<GroupMemberList>().data
    }

    suspend fun getVersionInfo(): OneBotVersionInfo.Data {
        val deferred = this.createCompletableDeferred(MessageEchoType.GetVersionInfo)
        this.sendToWs(GetVersionInfo())
        val response = deferred.await()
        return response.fromJson<OneBotVersionInfo>().data
    }

    suspend fun canSendImage(): Boolean {
        val deferred = this.createCompletableDeferred(MessageEchoType.CanSendImage)
        this.sendToWs(CanSendImageOut())
        val response = deferred.await()
        return response.fromJson<CanSend>().data.yes
    }

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
}