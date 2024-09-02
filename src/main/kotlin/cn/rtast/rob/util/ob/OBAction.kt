/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/26
 */


package cn.rtast.rob.util.ob

import cn.rtast.rob.ROneBotFactory.isServer
import cn.rtast.rob.ROneBotFactory.websocket
import cn.rtast.rob.ROneBotFactory.websocketServer
import cn.rtast.rob.entity.ArrayMessage
import cn.rtast.rob.entity.out.ArrayGroupMessageOut
import cn.rtast.rob.entity.out.ArrayPrivateMessageOut
import cn.rtast.rob.entity.out.CQCodeGroupMessageOut
import cn.rtast.rob.entity.out.CanSendImageOut
import cn.rtast.rob.entity.out.CanSendRecordOut
import cn.rtast.rob.entity.out.GetForwardMessageOut
import cn.rtast.rob.entity.out.GetFriendListOut
import cn.rtast.rob.entity.out.GetGroupInfoOut
import cn.rtast.rob.entity.out.GetGroupListOut
import cn.rtast.rob.entity.out.GetGroupMemberInfoOut
import cn.rtast.rob.entity.out.GetGroupMemberListOut
import cn.rtast.rob.entity.out.GetLoginInfoOut
import cn.rtast.rob.entity.out.GetMessageOut
import cn.rtast.rob.entity.out.GetStrangerInfoOut
import cn.rtast.rob.entity.out.GetVersionInfo
import cn.rtast.rob.entity.out.KickGroupMemberOut
import cn.rtast.rob.entity.out.RawArrayGroupMessageOut
import cn.rtast.rob.entity.out.RawArrayPrivateMessageOut
import cn.rtast.rob.entity.out.RevokeMessageOut
import cn.rtast.rob.entity.out.SendLikeOut
import cn.rtast.rob.entity.out.SetFriendRequestOut
import cn.rtast.rob.entity.out.SetGroupAdminOut
import cn.rtast.rob.entity.out.SetGroupAnonymousOut
import cn.rtast.rob.entity.out.SetGroupBanOut
import cn.rtast.rob.entity.out.SetGroupLeaveOut
import cn.rtast.rob.entity.out.SetGroupMemberCardOut
import cn.rtast.rob.entity.out.SetGroupMemberTitleOut
import cn.rtast.rob.entity.out.SetGroupNameOut
import cn.rtast.rob.entity.out.SetGroupNameOut.Params
import cn.rtast.rob.entity.out.SetGroupRequestOut
import cn.rtast.rob.entity.out.SetGroupWholeBanOut
import cn.rtast.rob.util.toJson

interface OBAction {

    private fun sendToWs(message: Any) {
        if (isServer) {
            websocketServer?.connections?.forEach { it.send(message.toJson()) }
            return
        }
        websocket?.send(message.toJson())
    }

    // do not override all suspend function, or you know what you are doing!
    suspend fun sendGroupMessage(groupId: Long, content: String) {
        this.sendToWs(CQCodeGroupMessageOut(params = CQCodeGroupMessageOut.Params(groupId, content)))
    }

    suspend fun sendGroupMessage(groupId: Long, content: MessageChain) {
        this.sendGroupMessage(groupId, content.finalString)
    }

    suspend fun sendGroupMessage(groupId: Long, content: ArrayMessageChain) {
        this.sendToWs(ArrayGroupMessageOut(params = ArrayGroupMessageOut.Params(groupId, content.finalArrayMsgList)))
    }

    suspend fun sendGroupMessage(groupId: Long, content: List<ArrayMessage>) {
        this.sendToWs(RawArrayGroupMessageOut(params = RawArrayGroupMessageOut.Params(groupId, content)))
    }

    suspend fun sendPrivateMessage(userId: Long, content: String) {
        this.sendToWs(CQCodeGroupMessageOut(params = CQCodeGroupMessageOut.Params(userId, content)))
    }

    suspend fun sendPrivateMessage(userId: Long, content: MessageChain) {
        this.sendPrivateMessage(userId, content.finalString)
    }

    suspend fun sendPrivateMessage(userId: Long, content: ArrayMessageChain) {
        this.sendToWs(ArrayPrivateMessageOut(params = ArrayPrivateMessageOut.Params(userId, content.finalArrayMsgList)))
    }

    suspend fun sendPrivateMessage(userId: Long, content: List<ArrayMessage>) {
        this.sendToWs(RawArrayPrivateMessageOut(params = RawArrayPrivateMessageOut.Params(userId, content)))
    }

    suspend fun revokeMessage(messageId: Long) {
        this.sendToWs(RevokeMessageOut(params = RevokeMessageOut.Params(messageId)))
    }

    // group id only need when get group message using message id
    suspend fun getMessage(messageId: Long, identifier: String, groupId: Long = 0L) {
        this.sendToWs(
            GetMessageOut(
                params = GetMessageOut.Params(messageId),
                echo = "GetMessage:$identifier:$groupId"
            )
        )
    }

    suspend fun getForwardMessage(messageId: String) {
        this.sendToWs(GetForwardMessageOut(params = GetForwardMessageOut.Params(messageId)))
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
        this.sendToWs(SetGroupNameOut(params = Params(groupId, groupName)))
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
        this.sendToWs(SetGroupRequestOut(params = SetGroupRequestOut.Params(flag, type, type, approve, reason)))
    }

    suspend fun getLoginInfo() {
        this.sendToWs(GetLoginInfoOut())
    }

    suspend fun getStrangerInfo(userId: Long, noCache: Boolean = false) {
        this.sendToWs(GetStrangerInfoOut(params = GetStrangerInfoOut.Params(userId, noCache)))
    }

    suspend fun getFriendList() {
        this.sendToWs(GetFriendListOut())
    }

    suspend fun getGroupInfo(groupId: Long, noCache: Boolean = false) {
        this.sendToWs(GetGroupInfoOut(params = GetGroupInfoOut.Params(groupId, noCache)))
    }

    suspend fun getGroupList() {
        this.sendToWs(GetGroupListOut())
    }

    suspend fun getGroupMemberInfo(groupId: Long, userId: Long, noCache: Boolean = false) {
        this.sendToWs(GetGroupMemberInfoOut(params = GetGroupMemberInfoOut.Params(groupId, userId, noCache)))
    }

    suspend fun getGroupMemberList(groupId: Long) {
        this.sendToWs(GetGroupMemberListOut(params = GetGroupMemberListOut.Params(groupId)))
    }

    suspend fun getVersionInfo() {
        this.sendToWs(GetVersionInfo())
    }

    suspend fun canSendImage() {
        this.sendToWs(CanSendImageOut())
    }

    suspend fun canSendRecord() {
        this.sendToWs(CanSendRecordOut())
    }
}