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
    fun onWebsocketServerStart() {}
    fun onConnectEvent(websocket: WebSocket, event: ConnectEvent) {}
    fun onHeartBeatMessage(websocket: WebSocket, event: HeartBeatEvent) {}
    fun onMessage(websocket: WebSocket, rawMessage: String) {}
    fun onGroupMessage(websocket: WebSocket, message: GroupMessage, json: String) {}
    fun onPrivateMessage(websocket: WebSocket, message: PrivateMessage, json: String) {}
    fun onInviteMessage(websocket: WebSocket, groupId: Long, userId: Long, operator: Long, time: Long) {}
    fun onApproveMessage(webSocket: WebSocket, groupId: Long, userId: Long, operator: Long, time: Long) {}
    fun onLeaveMessage(webSocket: WebSocket, groupId: Long, userId: Long, operator: Long, time: Long) {}
    fun onMemberKick(webSocket: WebSocket, time: Long) {}
    fun onBeKicked(webSocket: WebSocket, time: Long) {}
    fun onWebsocketOpen(websocket: WebSocket) {}
    fun onWebsocketClose(code: Int, reason: String, remote: Boolean) {}
    fun onSetOperator(webSocket: WebSocket, time: Long) {}
    fun onUnsetOperator(webSocket: WebSocket, time: Long) {}
    fun onBan(webSocket: WebSocket, time: Long) {}
    fun onPardon(webSocket: WebSocket, time: Long) {}
    fun onJoinRequest(webSocket: WebSocket, groupId: Long, userId: Long, comment: String, time: Long) {}
    fun onGroupMemberListResponse(webSocket: WebSocket, members: GroupMemberList) {}
    fun onOneBotVersionInfoResponse(webSocket: WebSocket, info: OneBotVersionInfo) {}
    fun onGroupMemberInfoResponse(webSocket: WebSocket, info: GroupMemberInfo) {}
    fun onGroupListResponse(webSocket: WebSocket, groupList: GroupList) {}
    fun onFriendListResponse(webSocket: WebSocket, friendList: FriendList) {}
    fun onStrangerInfoResponse(webSocket: WebSocket, info: StrangerInfo) {}
    fun onLoginInfoResponse(webSocket: WebSocket, info: LoginInfo) {}
    fun onCanSendResponse(webSocket: WebSocket, result: Boolean) {}
}