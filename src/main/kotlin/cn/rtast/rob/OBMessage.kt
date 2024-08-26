/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/26
 */


package cn.rtast.rob

import cn.rtast.rob.entity.ConnectEvent
import cn.rtast.rob.entity.GroupMessage
import cn.rtast.rob.entity.HeartBeatEvent
import cn.rtast.rob.entity.PrivateMessage
import org.java_websocket.WebSocket
import org.java_websocket.handshake.ServerHandshake

interface OBMessage {
    fun onConnectEvent(websocket: WebSocket, event: ConnectEvent) {}
    fun onHeartBeatMessage(websocket: WebSocket, event: HeartBeatEvent) {}
    fun onMessage(websocket: WebSocket, rawMessage: String) {}
    fun onGroupMessage(websocket: WebSocket, message: GroupMessage, rawMessage: String, json: String) {}
    fun onPrivateMessage(websocket: WebSocket, message: PrivateMessage, rawMessage: String, json: String) {}
    fun onInviteMessage(websocket: WebSocket, groupId: Long, userId: Long, operator: Long, time: Long) {}
    fun onApproveMessage(webSocket: WebSocket, groupId: Long, userId: Long, operator: Long, time: Long) {}
    fun onLeaveMessage(webSocket: WebSocket, groupId: Long, userId: Long, operator: Long, time: Long) {}
    fun onMemberKick(webSocket: WebSocket, time: Long) {}
    fun onBeKicked(webSocket: WebSocket, time: Long) {}
    fun onWebsocketOpen(serverHandshake: ServerHandshake) {}
    fun onWebsocketClose(code: Int, reason: String, remote: Boolean) {}
    fun onSetOperator(webSocket: WebSocket, time: Long) {}
    fun onUnsetOperator(webSocket: WebSocket, time: Long) {}
    fun onBan(webSocket: WebSocket, time: Long) {}
    fun onPardon(webSocket: WebSocket, time: Long) {}
    fun onJoinRequest(webSocket: WebSocket, groupId: Long, userId: Long, comment: String, time: Long) {}
}