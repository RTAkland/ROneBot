/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/26
 */


package cn.rtast.rob.util.ob

import cn.rtast.rob.ROneBotFactory.websocket
import cn.rtast.rob.entity.out.CanSendImageOut
import cn.rtast.rob.entity.out.CanSendRecordOut
import cn.rtast.rob.entity.out.GetForwardMessageOut
import cn.rtast.rob.entity.out.GetFriendListOut
import cn.rtast.rob.entity.out.GetGroupHonorInfoOut
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
import cn.rtast.rob.enums.HonorType
import cn.rtast.rob.util.toJson

interface OBAction {
    // do not inheritance all suspend function, or you know what you are doing!
    suspend fun sendGroupMessage(groupId: Long, content: String) {
        websocket?.send(GroupMessageOut(params = GroupMessageOut.Params(groupId, content)).toJson())
    }

    suspend fun sendPrivateMessage(userId: Long, content: String) {
        websocket?.send(PrivateMessageOut(params = PrivateMessageOut.Params(userId, content)).toJson())
    }

    suspend fun revokeMessage(messageId: Long) {
        websocket?.send(RevokeMessageOut(params = RevokeMessageOut.Params(messageId)).toJson())
    }

    suspend fun getMessage(messageId: Long) {
        websocket?.send(GetMessageOut(params = GetMessageOut.Params(messageId)).toJson())
    }

    suspend fun getForwardMessage(messageId: Long) {
        websocket?.send(GetForwardMessageOut(params = GetForwardMessageOut.Params(messageId)).toJson())
    }

    suspend fun sendLike(userId: Long, times: Int = 1) {
        websocket?.send(SendLikeOut(params = SendLikeOut.Params(userId, times)).toJson())
    }

    suspend fun kickGroupMember(groupId: Long, userId: Long, rejectJoinRequest: Boolean = false) {
        websocket?.send(
            KickGroupMemberOut(
                params = KickGroupMemberOut.Params(
                    groupId,
                    userId,
                    rejectJoinRequest
                )
            ).toJson()
        )
    }

    suspend fun setGroupBan(groupId: Long, userId: Long, duration: Int = 1800) {
        websocket?.send(SetGroupBanOut(params = SetGroupBanOut.Params(groupId, userId, duration)).toJson())
    }

    suspend fun setGroupWholeBan(groupId: Long, enable: Boolean = true) {
        websocket?.send(SetGroupWholeBanOut(params = SetGroupWholeBanOut.Params(groupId, enable)).toJson())
    }

    suspend fun setGroupAdmin(groupId: Long, userId: Long, enable: Boolean = true) {
        websocket?.send(SetGroupAdminOut(params = SetGroupAdminOut.Params(groupId, userId, enable)).toJson())
    }

    suspend fun setGroupAnonymous(groupId: Long, enable: Boolean = true) {
        websocket?.send(SetGroupAnonymousOut(params = SetGroupAnonymousOut.Params(groupId, enable)).toJson())
    }

    suspend fun setGroupMemberCard(groupId: Long, userId: Long, card: String = "") {
        websocket?.send(SetGroupMemberCardOut(params = SetGroupMemberCardOut.Params(groupId, userId, card)).toJson())
    }

    suspend fun setGroupName(groupId: Long, groupName: String) {
        websocket?.send(SetGroupNameOut(params = Params(groupId, groupName)).toJson())
    }

    suspend fun setGroupLeaveOrDismiss(groupId: Long, dismiss: Boolean = false) {
        websocket?.send(SetGroupLeaveOut(params = SetGroupLeaveOut.Params(groupId, dismiss)).toJson())
    }

    suspend fun setGroupMemberTitle(groupId: Long, userId: Long, title: String = "", duration: Int = -1) {
        websocket?.send(
            SetGroupMemberTitleOut(
                params = SetGroupMemberTitleOut.Params(
                    groupId,
                    userId,
                    title,
                    duration
                )
            ).toJson()
        )
    }

    suspend fun setFriendRequest(flag: String, approve: Boolean = true, remark: String = "") {
        websocket?.send(SetFriendRequestOut(params = SetFriendRequestOut.Params(flag, approve, remark)).toJson())
    }

    suspend fun setGroupRequest(
        flag: String,
        type: String,
        approve: Boolean = true,
        reason: String = ""
    ) {
        websocket?.send(
            SetGroupRequestOut(
                params = SetGroupRequestOut.Params(
                    flag,
                    type,
                    type,
                    approve,
                    reason
                )
            ).toJson()
        )
    }

    suspend fun getLoginInfo() {
        websocket?.send(GetLoginInfoOut().toJson())
    }

    suspend fun getStrangerInfo(userId: Long, noCache: Boolean = false) {
        websocket?.send(GetStrangerInfoOut(params = GetStrangerInfoOut.Params(userId, noCache)).toJson())
    }

    suspend fun getFriendList() {
        websocket?.send(GetFriendListOut().toJson())
    }

    suspend fun getGroupInfo(groupId: Long, noCache: Boolean = false) {
        websocket?.send(GetGroupInfoOut(params = GetGroupInfoOut.Params(groupId, noCache)).toJson())
    }

    suspend fun getGroupList() {
        websocket?.send(GetGroupListOut().toJson())
    }

    suspend fun getGroupMemberInfo(groupId: Long, userId: Long, noCache: Boolean = false) {
        websocket?.send(GetGroupMemberInfoOut(params = GetGroupMemberInfoOut.Params(groupId, userId, noCache)).toJson())
    }

    suspend fun getGroupMemberList(groupId: Long) {
        websocket?.send(GetGroupMemberListOut(params = GetGroupMemberListOut.Params(groupId)).toJson())
    }

    suspend fun getGroupHonorInfo(groupId: Long, honorType: HonorType) {
        websocket?.send(GetGroupHonorInfoOut(params = GetGroupHonorInfoOut.Params(groupId, honorType)).toJson())
    }

    suspend fun getVersionInfo() {
        websocket?.send(GetVersionInfo().toJson())
    }

    suspend fun canSendImage() {
        websocket?.send(CanSendImageOut().toJson())
    }

    suspend fun canSendRecord() {
        websocket?.send(CanSendRecordOut().toJson())
    }
}