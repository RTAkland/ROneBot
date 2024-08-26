/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/26
 */


package cn.rtast.rob.util

import cn.rtast.rob.OBMessage
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
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import java.lang.Exception
import java.net.URI

internal class WsClient(
    address: String,
    accessToken: String,
    private val listener: OBMessage
) : WebSocketClient(URI(address), mapOf("Authorization" to "Bearer $accessToken")) {

    override fun onOpen(handshakedata: ServerHandshake) {
        listener.onWebsocketOpen(handshakedata)
    }

    override fun onMessage(message: String) {
        val serializedMessage = message.fromJson<BaseMessage>()
        if (serializedMessage.metaEventType != null) {
            when (serializedMessage.metaEventType) {
                MetaEventType.heartbeat -> listener.onHeartBeatMessage(this, message.fromJson<HeartBeatEvent>())
                MetaEventType.lifecycle -> listener.onConnectEvent(this, message.fromJson<ConnectEvent>())
            }
            return
        }

        if (serializedMessage.postType == PostType.message) {
            when (serializedMessage.messageType) {
                MessageType.group -> listener.onGroupMessage(
                    this,
                    message.fromJson<GroupMessage>(),
                    serializedMessage.rawMessage,
                    message
                )

                MessageType.private -> listener.onPrivateMessage(
                    this,
                    message.fromJson<PrivateMessage>(),
                    serializedMessage.rawMessage,
                    message
                )

                null -> listener.onMessage(this, message)
            }
        }

        if (serializedMessage.postType == PostType.notice) {
            val time = serializedMessage.time
            val msg = message.fromJson<NoticeEvent>()
            when (serializedMessage.subType) {
                SubType.kick -> listener.onMemberKick(this, time)
                SubType.kick_me -> listener.onBeKicked(this, time)
                SubType.unset -> listener.onUnsetOperator(this, time)
                SubType.set -> listener.onSetOperator(this, time)
                SubType.ban -> listener.onBan(this, time)
                SubType.lift_ban -> listener.onPardon(this, time)
                SubType.leave -> listener.onLeaveMessage(this, msg.groupId, msg.userId, msg.operatorId, time)
                SubType.invite -> listener.onInviteMessage(this, msg.groupId, msg.userId, msg.operatorId, time)
                SubType.approve -> listener.onApproveMessage(this, msg.groupId, msg.userId, msg.operatorId, time)
                SubType.add -> listener.onJoinRequest(this, msg.groupId, msg.userId, msg.comment!!, time)
            }
        }
    }

    override fun onClose(code: Int, reason: String, remote: Boolean) {
        listener.onWebsocketClose(code, reason, remote)
    }

    override fun onError(ex: Exception) {}
}