/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/26
 */


package cn.rtast.rob.util.ob

import cn.rtast.rob.entity.ArrayMessage
import cn.rtast.rob.entity.ConnectEvent
import cn.rtast.rob.entity.FriendList
import cn.rtast.rob.entity.GroupInfo
import cn.rtast.rob.entity.GroupList
import cn.rtast.rob.entity.GroupMemberInfo
import cn.rtast.rob.entity.GroupMemberList
import cn.rtast.rob.entity.GroupMessage
import cn.rtast.rob.entity.HeartBeatEvent
import cn.rtast.rob.entity.LoginInfo
import cn.rtast.rob.entity.OneBotVersionInfo
import cn.rtast.rob.entity.PrivateMessage
import cn.rtast.rob.entity.StrangerInfo
import org.java_websocket.WebSocket

interface OBMessage : OBAction {
    suspend fun onWebsocketErrorEvent(ws: WebSocket, ex: Exception) {}
    suspend fun onWebsocketOpenEvent(ws: WebSocket) {}
    suspend fun onWebsocketCloseEvent(code: Int, reason: String, remote: Boolean) {}
    suspend fun onWebsocketServerStartEvent() {}
    suspend fun onConnectEvent(ws: WebSocket, event: ConnectEvent) {}
    suspend fun onHeartBeatEvent(ws: WebSocket, event: HeartBeatEvent) {}
    suspend fun onMessage(ws: WebSocket, rawMessage: String) {}
    suspend fun onGroupMessageRevoke(ws: WebSocket, groupId: Long, userId: Long, operator: Long, messageId: String) {}
    suspend fun onPrivateMessageRevoke(ws: WebSocket, userId: Long, messageId: String) {}
    suspend fun onBeAt(ws: WebSocket, message: GroupMessage) {}
    suspend fun onBeRepliedInGroup(ws: WebSocket, message: GroupMessage) {}
    suspend fun onBeRepliedInPrivate(ws: WebSocket, message: PrivateMessage) {}
    suspend fun onGroupMessage(ws: WebSocket, message: GroupMessage, json: String) {}
    suspend fun onPrivateMessage(ws: WebSocket, message: PrivateMessage, json: String) {}
    suspend fun onInviteEvent(ws: WebSocket, groupId: Long, userId: Long, operator: Long, time: Long) {}
    suspend fun onApproveEvent(ws: WebSocket, groupId: Long, userId: Long, operator: Long, time: Long) {}
    suspend fun onLeaveEvent(ws: WebSocket, groupId: Long, userId: Long, operator: Long, time: Long) {}
    suspend fun onMemberKick(ws: WebSocket, groupId: Long, operator: Long, time: Long) {}
    suspend fun onBeKicked(ws: WebSocket, groupId: Long, operator: Long, time: Long) {}
    suspend fun onSetOperator(ws: WebSocket, groupId: Long, operator: Long, time: Long) {}
    suspend fun onUnsetOperator(ws: WebSocket, groupId: Long, operator: Long, time: Long) {}
    suspend fun onBan(ws: WebSocket, groupId: Long, operator: Long, duration: Int, time: Long) {}
    suspend fun onPardon(ws: WebSocket, groupId: Long, operator: Long, duration: Int, time: Long) {}
    suspend fun onJoinRequest(ws: WebSocket, groupId: Long, userId: Long, comment: String, time: Long) {}
    suspend fun onGetGroupMemberListResponse(ws: WebSocket, members: GroupMemberList) {}
    suspend fun onGetOneBotVersionInfoResponse(ws: WebSocket, info: OneBotVersionInfo) {}
    suspend fun onGetGroupMemberInfoResponse(ws: WebSocket, info: GroupMemberInfo) {}
    suspend fun onGetGroupListResponse(ws: WebSocket, groupList: GroupList) {}
    suspend fun onGetFriendListResponse(ws: WebSocket, friendList: FriendList) {}
    suspend fun onGetStrangerInfoResponse(ws: WebSocket, info: StrangerInfo) {}
    suspend fun onGetLoginInfoResponse(ws: WebSocket, info: LoginInfo) {}
    suspend fun onCanSendImageResponse(ws: WebSocket, result: Boolean) {}
    suspend fun onCanSendRecordResponse(ws: WebSocket, result: Boolean) {}
    suspend fun onGetGroupMessageResponse(
        ws: WebSocket,
        message: List<ArrayMessage>,
        id: String,
        sender: Long,
        groupId: Long
    ) {
    }

    suspend fun onGetPrivateMessageResponse(ws: WebSocket, message: List<ArrayMessage>, id: String, sender: Long) {}
    suspend fun onGetForwardMessageResponse(ws: WebSocket, messageJson: String) {}
    suspend fun onGetGroupInfoResponse(ws: WebSocket, groupInfo: GroupInfo) {}
}