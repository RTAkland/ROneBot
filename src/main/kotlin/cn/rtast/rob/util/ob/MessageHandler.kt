/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/26
 */


package cn.rtast.rob.util.ob

import cn.rtast.rob.ROneBotFactory.commandManager
import cn.rtast.rob.entity.BaseMessage
import cn.rtast.rob.entity.CanSend
import cn.rtast.rob.entity.ConnectEvent
import cn.rtast.rob.entity.FriendList
import cn.rtast.rob.entity.GroupInfo
import cn.rtast.rob.entity.GroupList
import cn.rtast.rob.entity.GroupMemberInfo
import cn.rtast.rob.entity.GroupMemberList
import cn.rtast.rob.entity.GroupMessage
import cn.rtast.rob.entity.HeartBeatEvent
import cn.rtast.rob.entity.LoginInfo
import cn.rtast.rob.entity.NoticeEvent
import cn.rtast.rob.entity.OneBotVersionInfo
import cn.rtast.rob.entity.PrivateMessage
import cn.rtast.rob.entity.ResponseMessage
import cn.rtast.rob.entity.StrangerInfo
import cn.rtast.rob.enums.MessageEchoType
import cn.rtast.rob.enums.MessageType
import cn.rtast.rob.enums.MetaEventType
import cn.rtast.rob.enums.PostType
import cn.rtast.rob.enums.SubType
import cn.rtast.rob.util.fromJson
import org.java_websocket.WebSocket

object MessageHandler {

    suspend fun onMessage(listener: OBMessage, websocket: WebSocket, message: String) {
        try {
            listener.onMessage(websocket, message)
            val serializedMessage = message.fromJson<BaseMessage>()
            if (serializedMessage.metaEventType != null) {
                when (serializedMessage.metaEventType) {
                    MetaEventType.heartbeat -> listener.onHeartBeatMessage(
                        websocket,
                        message.fromJson<HeartBeatEvent>()
                    )

                    MetaEventType.lifecycle -> listener.onConnectEvent(websocket, message.fromJson<ConnectEvent>())
                }
                return
            }

            if (serializedMessage.postType == PostType.message) {
                when (serializedMessage.messageType) {
                    MessageType.group -> {
                        val msg = message.fromJson<GroupMessage>()
                        commandManager.handleGroup(listener, msg)
                        listener.onGroupMessage(websocket, msg, message)
                    }

                    MessageType.private -> {
                        val msg = message.fromJson<PrivateMessage>()
                        commandManager.handlePrivate(listener, msg)
                        listener.onPrivateMessage(websocket, msg, message)
                    }

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
                    SubType.approve -> listener.onApproveMessage(
                        websocket,
                        msg.groupId,
                        msg.userId,
                        msg.operatorId,
                        time
                    )

                    SubType.add -> listener.onJoinRequest(websocket, msg.groupId, msg.userId, msg.comment!!, time)
                }
                return
            }

            val messageSign = message.fromJson<ResponseMessage>().echo
            when (messageSign) {
                MessageEchoType.CanSendImage -> listener.onCanSendImageResponse(
                    websocket,
                    message.fromJson<CanSend>().data.yes
                )

                MessageEchoType.CanSendRecord -> listener.onCanSendRecordResponse(
                    websocket,
                    message.fromJson<CanSend>().data.yes
                )

                MessageEchoType.GetForwardMessage -> listener.onGetForwardMessageResponse(websocket, message)
                MessageEchoType.GetFriendList -> listener.onGetFriendListResponse(
                    websocket,
                    message.fromJson<FriendList>()
                )

                MessageEchoType.GetGroupInfo -> listener.onGetGroupInfoResponse(
                    websocket,
                    message.fromJson<GroupInfo>()
                )

                MessageEchoType.GetGroupList -> listener.onGetGroupListResponse(
                    websocket,
                    message.fromJson<GroupList>()
                )

                MessageEchoType.GetGroupMemberList -> listener.onGetGroupMemberListResponse(
                    websocket,
                    message.fromJson<GroupMemberList>()
                )

                MessageEchoType.GetGroupMemberInfo -> listener.onGetGroupMemberInfoResponse(
                    websocket,
                    message.fromJson<GroupMemberInfo>()
                )

                MessageEchoType.GetLoginInfo -> listener.onGetLoginInfoResponse(
                    websocket,
                    message.fromJson<LoginInfo>()
                )

                MessageEchoType.GetMessage -> listener.onGetMessageResponse(websocket, message)
                MessageEchoType.GetStrangerInfo -> listener.onGetStrangerInfoResponse(
                    websocket,
                    message.fromJson<StrangerInfo>()
                )

                MessageEchoType.GetVersionInfo -> listener.onGetOneBotVersionInfoResponse(
                    websocket,
                    message.fromJson<OneBotVersionInfo>()
                )

                null -> {}
            }
        } catch (ex: Exception) {
            this.onError(listener, websocket, ex)
        }
    }

    suspend fun onOpen(listener: OBMessage, websocket: WebSocket) {
        println("New connection: ${websocket.remoteSocketAddress}")
        listener.onWebsocketOpen(websocket)
    }

    suspend fun onClose(listener: OBMessage, code: Int, reason: String, remote: Boolean) {
        listener.onWebsocketClose(code, reason, remote)
    }

    suspend fun onStart(listener: OBMessage) {
        listener.onWebsocketServerStart()
    }

    suspend fun onError(listener: OBMessage, websocket: WebSocket, ex: Exception) {
        listener.onWebsocketError(websocket, ex)
    }
}