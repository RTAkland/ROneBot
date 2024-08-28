/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/26
 */


package cn.rtast.rob.util.ob

import cn.rtast.rob.entity.ConnectEvent
import cn.rtast.rob.entity.FriendList
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
    suspend fun onWebsocketServerStart() {}
    suspend fun onConnectEvent(websocket: WebSocket, event: ConnectEvent) {}
    suspend fun onHeartBeatMessage(websocket: WebSocket, event: HeartBeatEvent) {}
    suspend fun onMessage(websocket: WebSocket, rawMessage: String) {}
    suspend fun onGroupMessage(websocket: WebSocket, message: GroupMessage, json: String) {}
    suspend fun onPrivateMessage(websocket: WebSocket, message: PrivateMessage, json: String) {}
    suspend fun onInviteMessage(websocket: WebSocket, groupId: Long, userId: Long, operator: Long, time: Long) {}
    suspend fun onApproveMessage(webSocket: WebSocket, groupId: Long, userId: Long, operator: Long, time: Long) {}
    suspend fun onLeaveMessage(webSocket: WebSocket, groupId: Long, userId: Long, operator: Long, time: Long) {}
    suspend fun onMemberKick(webSocket: WebSocket, time: Long) {}
    suspend fun onBeKicked(webSocket: WebSocket, time: Long) {}
    suspend fun onWebsocketOpen(websocket: WebSocket) {}
    suspend fun onWebsocketClose(code: Int, reason: String, remote: Boolean) {}
    suspend fun onSetOperator(webSocket: WebSocket, time: Long) {}
    suspend fun onUnsetOperator(webSocket: WebSocket, time: Long) {}
    suspend fun onBan(webSocket: WebSocket, time: Long) {}
    suspend fun onPardon(webSocket: WebSocket, time: Long) {}
    suspend fun onJoinRequest(webSocket: WebSocket, groupId: Long, userId: Long, comment: String, time: Long) {}
    suspend fun onGroupMemberListResponse(webSocket: WebSocket, members: GroupMemberList) {}
    suspend fun onOneBotVersionInfoResponse(webSocket: WebSocket, info: OneBotVersionInfo) {}
    suspend fun onGroupMemberInfoResponse(webSocket: WebSocket, info: GroupMemberInfo) {}
    suspend fun onGroupListResponse(webSocket: WebSocket, groupList: GroupList) {}
    suspend fun onFriendListResponse(webSocket: WebSocket, friendList: FriendList) {}
    suspend fun onStrangerInfoResponse(webSocket: WebSocket, info: StrangerInfo) {}
    suspend fun onLoginInfoResponse(webSocket: WebSocket, info: LoginInfo) {}
    suspend fun onCanSendResponse(webSocket: WebSocket, result: Boolean) {}
}