/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/26
 */


package cn.rtast.rob.util.ob

import cn.rtast.rob.entity.BaseMessage
import cn.rtast.rob.entity.ConnectEvent
import cn.rtast.rob.entity.GroupMessage
import cn.rtast.rob.entity.HeartBeatEvent
import cn.rtast.rob.entity.NoticeEvent
import cn.rtast.rob.entity.PrivateMessage
import cn.rtast.rob.enums.MessageType
import cn.rtast.rob.enums.MetaEventType
import cn.rtast.rob.enums.PostType
import cn.rtast.rob.enums.SubType
import cn.rtast.rob.util.fromJson
import org.java_websocket.WebSocket

object MessageHandler {
    fun onMessage(listener: OBMessage, websocket: WebSocket, message: String) {
        listener.onMessage(websocket, message)
        val serializedMessage = message.fromJson<BaseMessage>()
        if (serializedMessage.metaEventType != null) {
            when (serializedMessage.metaEventType) {
                MetaEventType.heartbeat -> listener.onHeartBeatMessage(websocket, message.fromJson<HeartBeatEvent>())
                MetaEventType.lifecycle -> listener.onConnectEvent(websocket, message.fromJson<ConnectEvent>())
            }
            return
        }

        if (serializedMessage.postType == PostType.message) {
            when (serializedMessage.messageType) {
                MessageType.group -> listener.onGroupMessage(websocket, message.fromJson<GroupMessage>(), message)
                MessageType.private -> listener.onPrivateMessage(websocket, message.fromJson<PrivateMessage>(), message)
                null -> listener.onMessage(websocket, message)
            }
            return
        }

        if (serializedMessage.postType == PostType.notice) {
            val time = serializedMessage.time
            val msg = message.fromJson<NoticeEvent>()
            when (serializedMessage.subType) {
                SubType.kick -> listener.onMemberKick(websocket, time)
                SubType.kick_me -> listener.onBeKicked(websocket, time)
                SubType.unset -> listener.onUnsetOperator(websocket, time)
                SubType.set -> listener.onSetOperator(websocket, time)
                SubType.ban -> listener.onBan(websocket, time)
                SubType.lift_ban -> listener.onPardon(websocket, time)
                SubType.leave -> listener.onLeaveMessage(websocket, msg.groupId, msg.userId, msg.operatorId, time)
                SubType.invite -> listener.onInviteMessage(websocket, msg.groupId, msg.userId, msg.operatorId, time)
                SubType.approve -> listener.onApproveMessage(websocket, msg.groupId, msg.userId, msg.operatorId, time)
                SubType.add -> listener.onJoinRequest(websocket, msg.groupId, msg.userId, msg.comment!!, time)
            }
        }
    }

    fun onOpen(listener: OBMessage, websocket: WebSocket) {
        println("New connection: ${websocket.remoteSocketAddress}")
        listener.onWebsocketOpen(websocket)
    }

    fun onClose(listener: OBMessage, code: Int, reason: String, remote: Boolean) {
        listener.onWebsocketClose(code, reason, remote)
    }

    fun onStart(listener: OBMessage) {
        listener.onWebsocketServerStart()
    }
}