/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/26
 */


package cn.rtast.rob.util.ob

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
import org.java_websocket.WebSocket

interface OBAction {

    // do not inheritance all function, or you know what you are doing!
    fun sendGroupMessage(websocket: WebSocket, groupId: Long, content: String) {
        websocket.send(GroupMessageOut(params = GroupMessageOut.Params(groupId, content)).toJson())
    }

    fun sendPrivateMessage(websocket: WebSocket, userId: Long, content: String) {
        websocket.send(PrivateMessageOut(params = PrivateMessageOut.Params(userId, content)).toJson())
    }

    fun revokeMessage(websocket: WebSocket, messageId: Long) {
        websocket.send(RevokeMessageOut(params = RevokeMessageOut.Params(messageId)).toJson())
    }

    fun getMessage(websocket: WebSocket, messageId: Long) {
        websocket.send(GetMessageOut(params = GetMessageOut.Params(messageId)).toJson())
    }

    fun getForwardMessage(websocket: WebSocket, messageId: Long) {
        websocket.send(GetForwardMessageOut(params = GetForwardMessageOut.Params(messageId)).toJson())
    }

    fun sendLike(websocket: WebSocket, userId: Long, times: Int = 1) {
        websocket.send(SendLikeOut(params = SendLikeOut.Params(userId, times)).toJson())
    }

    fun kickGroupMember(websocket: WebSocket, groupId: Long, userId: Long, rejectJoinRequest: Boolean = false) {
        websocket.send(
            KickGroupMemberOut(
                params = KickGroupMemberOut.Params(
                    groupId,
                    userId,
                    rejectJoinRequest
                )
            ).toJson()
        )
    }

    fun setGroupBan(websocket: WebSocket, groupId: Long, userId: Long, duration: Int = 1800) {
        websocket.send(SetGroupBanOut(params = SetGroupBanOut.Params(groupId, userId, duration)).toJson())
    }

    fun setGroupWholeBan(websocket: WebSocket, groupId: Long, enable: Boolean = true) {
        websocket.send(SetGroupWholeBanOut(params = SetGroupWholeBanOut.Params(groupId, enable)).toJson())
    }

    fun setGroupAdmin(websocket: WebSocket, groupId: Long, userId: Long, enable: Boolean = true) {
        websocket.send(SetGroupAdminOut(params = SetGroupAdminOut.Params(groupId, userId, enable)).toJson())
    }

    fun setGroupAnonymous(websocket: WebSocket, groupId: Long, enable: Boolean = true) {
        websocket.send(SetGroupAnonymousOut(params = SetGroupAnonymousOut.Params(groupId, enable)).toJson())
    }

    fun setGroupMemberCard(websocket: WebSocket, groupId: Long, userId: Long, card: String = "") {
        websocket.send(SetGroupMemberCardOut(params = SetGroupMemberCardOut.Params(groupId, userId, card)).toJson())
    }

    fun setGroupName(websocket: WebSocket, groupId: Long, groupName: String) {
        websocket.send(SetGroupNameOut(params = Params(groupId, groupName)).toJson())
    }

    fun setGroupLeaveOrDismiss(websocket: WebSocket, groupId: Long, dismiss: Boolean = false) {
        websocket.send(SetGroupLeaveOut(params = SetGroupLeaveOut.Params(groupId, dismiss)).toJson())
    }

    fun setGroupMemberTitle(websocket: WebSocket, groupId: Long, userId: Long, title: String = "", duration: Int = -1) {
        websocket.send(
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

    fun setFriendRequest(websocket: WebSocket, flag: String, approve: Boolean = true, remark: String = "") {
        websocket.send(SetFriendRequestOut(params = SetFriendRequestOut.Params(flag, approve, remark)).toJson())
    }

    fun setGroupRequest(
        websocket: WebSocket,
        flag: String,
        type: String,
        approve: Boolean = true,
        reason: String = ""
    ) {
        websocket.send(
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

    fun getLoginInfo(websocket: WebSocket) {
        websocket.send(GetLoginInfoOut().toJson())
    }

    fun getStrangerInfo(websocket: WebSocket, userId: Long, noCache: Boolean = false) {
        websocket.send(GetStrangerInfoOut(params = GetStrangerInfoOut.Params(userId, noCache)).toJson())
    }

    fun getFriendList(websocket: WebSocket) {
        websocket.send(GetFriendListOut().toJson())
    }

    fun getGroupInfo(websocket: WebSocket, groupId: Long, noCache: Boolean = false) {
        websocket.send(GetGroupInfoOut(params = GetGroupInfoOut.Params(groupId, noCache)).toJson())
    }

    fun getGroupList(websocket: WebSocket) {
        websocket.send(GetGroupListOut().toJson())
    }

    fun getGroupMemberInfo(websocket: WebSocket, groupId: Long, userId: Long, noCache: Boolean = false) {
        websocket.send(GetGroupMemberInfoOut(params = GetGroupMemberInfoOut.Params(groupId, userId, noCache)).toJson())
    }

    fun getGroupMemberList(websocket: WebSocket, groupId: Long) {
        websocket.send(GetGroupMemberListOut(params = GetGroupMemberListOut.Params(groupId)).toJson())
    }

    fun getGroupHonorInfo(websocket: WebSocket, groupId: Long, honorType: HonorType) {
        websocket.send(GetGroupHonorInfoOut(params = GetGroupHonorInfoOut.Params(groupId, honorType)).toJson())
    }
}