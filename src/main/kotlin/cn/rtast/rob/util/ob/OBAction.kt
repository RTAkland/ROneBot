/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/26
 */


package cn.rtast.rob.util.ob

import cn.rtast.rob.ROneBotFactory.isServer
import cn.rtast.rob.ROneBotFactory.websocket
import cn.rtast.rob.ROneBotFactory.websocketServer
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
import cn.rtast.rob.entity.out.GroupMessageOut
import cn.rtast.rob.entity.out.KickGroupMemberOut
import cn.rtast.rob.entity.out.PrivateMessageOut
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

    private fun sendToWs(message: String) {
        if (isServer) {
            websocketServer?.connections?.forEach { it.send(message) }
            return
        }
        websocket?.send(message)
    }

    // do not inheritance all suspend function, or you know what you are doing!
    suspend fun sendGroupMessage(groupId: Long, content: String) {
        val msg = GroupMessageOut(params = GroupMessageOut.Params(groupId, content)).toJson()
        this.sendToWs(msg)
    }

    suspend fun sendPrivateMessage(userId: Long, content: String) {
        val msg = PrivateMessageOut(params = PrivateMessageOut.Params(userId, content)).toJson()
        this.sendToWs(msg)
    }

    suspend fun revokeMessage(messageId: Long) {
        val msg = RevokeMessageOut(params = RevokeMessageOut.Params(messageId)).toJson()
        this.sendToWs(msg)
    }

    suspend fun getMessage(messageId: Long) {
        val msg = GetMessageOut(params = GetMessageOut.Params(messageId)).toJson()
        this.sendToWs(msg)
    }

    suspend fun getForwardMessage(messageId: String) {
        val msg = GetForwardMessageOut(params = GetForwardMessageOut.Params(messageId)).toJson()
        this.sendToWs(msg)
    }

    suspend fun sendLike(userId: Long, times: Int = 1) {
        val msg = SendLikeOut(params = SendLikeOut.Params(userId, times)).toJson()
        this.sendToWs(msg)
    }

    suspend fun kickGroupMember(groupId: Long, userId: Long, rejectJoinRequest: Boolean = false) {
        val msg = KickGroupMemberOut(params = KickGroupMemberOut.Params(groupId, userId, rejectJoinRequest)).toJson()
        this.sendToWs(msg)
    }

    suspend fun setGroupBan(groupId: Long, userId: Long, duration: Int = 1800) {
        val msg = SetGroupBanOut(params = SetGroupBanOut.Params(groupId, userId, duration)).toJson()
        this.sendToWs(msg)
    }

    suspend fun setGroupWholeBan(groupId: Long, enable: Boolean = true) {
        val msg = SetGroupWholeBanOut(params = SetGroupWholeBanOut.Params(groupId, enable)).toJson()
        this.sendToWs(msg)
    }

    suspend fun setGroupAdmin(groupId: Long, userId: Long, enable: Boolean = true) {
        val msg = SetGroupAdminOut(params = SetGroupAdminOut.Params(groupId, userId, enable)).toJson()
        this.sendToWs(msg)
    }

    suspend fun setGroupAnonymous(groupId: Long, enable: Boolean = true) {
        val msg = SetGroupAnonymousOut(params = SetGroupAnonymousOut.Params(groupId, enable)).toJson()
        this.sendToWs(msg)
    }

    suspend fun setGroupMemberCard(groupId: Long, userId: Long, card: String = "") {
        val msg = SetGroupMemberCardOut(params = SetGroupMemberCardOut.Params(groupId, userId, card)).toJson()
        this.sendToWs(msg)
    }

    suspend fun setGroupName(groupId: Long, groupName: String) {
        val msg = SetGroupNameOut(params = Params(groupId, groupName)).toJson()
        this.sendToWs(msg)
    }

    suspend fun setGroupLeaveOrDismiss(groupId: Long, dismiss: Boolean = false) {
        val msg = SetGroupLeaveOut(params = SetGroupLeaveOut.Params(groupId, dismiss)).toJson()
        this.sendToWs(msg)
    }

    suspend fun setGroupMemberTitle(groupId: Long, userId: Long, title: String = "", duration: Int = -1) {
        val msg =
            SetGroupMemberTitleOut(params = SetGroupMemberTitleOut.Params(groupId, userId, title, duration)).toJson()
        this.sendToWs(msg)
    }

    suspend fun setFriendRequest(flag: String, approve: Boolean = true, remark: String = "") {
        val msg = SetFriendRequestOut(params = SetFriendRequestOut.Params(flag, approve, remark)).toJson()
        this.sendToWs(msg)
    }

    suspend fun setGroupRequest(
        flag: String,
        type: String,
        approve: Boolean = true,
        reason: String = ""
    ) {
        val msg = SetGroupRequestOut(params = SetGroupRequestOut.Params(flag, type, type, approve, reason)).toJson()
        this.sendToWs(msg)
    }

    suspend fun getLoginInfo() {
        val msg = GetLoginInfoOut().toJson()
        this.sendToWs(msg)
    }

    suspend fun getStrangerInfo(userId: Long, noCache: Boolean = false) {
        val msg = GetStrangerInfoOut(params = GetStrangerInfoOut.Params(userId, noCache)).toJson()
        this.sendToWs(msg)
    }

    suspend fun getFriendList() {
        val msg = GetFriendListOut().toJson()
        this.sendToWs(msg)
    }

    suspend fun getGroupInfo(groupId: Long, noCache: Boolean = false) {
        val msg = GetGroupInfoOut(params = GetGroupInfoOut.Params(groupId, noCache)).toJson()
        this.sendToWs(msg)
    }

    suspend fun getGroupList() {
        val msg = GetGroupListOut().toJson()
        this.sendToWs(msg)
    }

    suspend fun getGroupMemberInfo(groupId: Long, userId: Long, noCache: Boolean = false) {
        val msg = GetGroupMemberInfoOut(params = GetGroupMemberInfoOut.Params(groupId, userId, noCache)).toJson()
        this.sendToWs(msg)
    }

    suspend fun getGroupMemberList(groupId: Long) {
        val msg = GetGroupMemberListOut(params = GetGroupMemberListOut.Params(groupId)).toJson()
        this.sendToWs(msg)
    }

    suspend fun getVersionInfo() {
        val msg = GetVersionInfo().toJson()
        this.sendToWs(msg)
    }

    suspend fun canSendImage() {
        val msg = CanSendImageOut().toJson()
        this.sendToWs(msg)
    }

    suspend fun canSendRecord() {
        val msg = CanSendRecordOut().toJson()
        this.sendToWs(msg)
    }
}