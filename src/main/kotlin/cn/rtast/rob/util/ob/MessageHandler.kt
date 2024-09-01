/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/26
 */


package cn.rtast.rob.util.ob

import cn.rtast.rob.ROneBotFactory
import cn.rtast.rob.ROneBotFactory.commandManager
import cn.rtast.rob.entity.BaseMessage
import cn.rtast.rob.entity.CanSend
import cn.rtast.rob.entity.ConnectEvent
import cn.rtast.rob.entity.FriendList
import cn.rtast.rob.entity.GetMessage
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
import cn.rtast.rob.enums.ArrayMessageType
import cn.rtast.rob.enums.MessageEchoType
import cn.rtast.rob.enums.MessageType
import cn.rtast.rob.enums.MetaEventType
import cn.rtast.rob.enums.NoticeType
import cn.rtast.rob.enums.PostType
import cn.rtast.rob.enums.SubType
import cn.rtast.rob.util.fromJson
import org.java_websocket.WebSocket

object MessageHandler {

    private val listeningGroups = ROneBotFactory.getListeningGroups()

    suspend fun onMessage(listener: OBMessage, ws: WebSocket, message: String) {
        try {
            listener.onMessage(ws, message)
            val serializedMessage = message.fromJson<BaseMessage>()
            if (serializedMessage.metaEventType != null) {
                when (serializedMessage.metaEventType) {
                    MetaEventType.heartbeat -> listener.onHeartBeatEvent(
                        ws,
                        message.fromJson<HeartBeatEvent>()
                    )

                    MetaEventType.lifecycle -> listener.onConnectEvent(ws, message.fromJson<ConnectEvent>())
                }
                return
            }

            if (serializedMessage.postType == PostType.message) {
                when (serializedMessage.messageType) {
                    MessageType.group -> {
                        val msg = message.fromJson<GroupMessage>()
                        if (msg.groupId !in listeningGroups && listeningGroups.isNotEmpty()) return
                        msg.message.distinctBy { it.type }.forEach {
                            if (it.type == ArrayMessageType.reply) {
                                listener.onBeRepliedInGroup(ws, msg)
                                return@forEach
                            }
                            if (it.type == ArrayMessageType.at) {
                                listener.onBeAt(ws, msg)
                                return@forEach
                            }
                        }
                        commandManager.handleGroup(listener, msg)
                        listener.onGroupMessage(ws, msg, message)
                    }

                    MessageType.private -> {
                        val msg = message.fromJson<PrivateMessage>()
                        msg.message.forEach {
                            if (it.type == ArrayMessageType.reply) {
                                listener.onBeRepliedInPrivate(ws, msg)
                                return@forEach
                            }
                        }
                        commandManager.handlePrivate(listener, msg)
                        listener.onPrivateMessage(ws, msg, message)
                    }

                    null -> listener.onMessage(ws, message)
                }
                return
            }

            if (serializedMessage.postType == PostType.notice) {
                val time = serializedMessage.time
                val msg = message.fromJson<NoticeEvent>()
                if (msg.groupId != null && msg.groupId !in listeningGroups) return
                when (serializedMessage.noticeType) {
                    NoticeType.group_recall -> {
                        listener.onGroupMessageRevoke(
                            ws,
                            msg.groupId!!,
                            msg.userId,
                            msg.operatorId,
                            msg.messageId!!
                        )
                        return
                    }

                    NoticeType.friend_recall -> {
                        listener.onPrivateMessageRevoke(ws, msg.userId, msg.messageId!!)
                        return
                    }

                    null -> {}
                }
                when (serializedMessage.subType) {
                    SubType.kick -> listener.onMemberKick(ws, msg.groupId!!, msg.operatorId, time)
                    SubType.kick_me -> listener.onBeKicked(ws, msg.groupId!!, msg.operatorId, time)
                    SubType.unset -> listener.onUnsetOperator(ws, msg.groupId!!, msg.operatorId, time)
                    SubType.set -> listener.onSetOperator(ws, msg.groupId!!, msg.operatorId, time)
                    SubType.ban -> listener.onBan(ws, msg.groupId!!, msg.operatorId, msg.duration!!, time)
                    SubType.lift_ban -> listener.onPardon(
                        ws,
                        msg.groupId!!,
                        msg.operatorId,
                        msg.duration!!,
                        time
                    )

                    SubType.leave -> listener.onLeaveEvent(ws, msg.groupId!!, msg.userId, msg.operatorId, time)
                    SubType.invite -> listener.onInviteEvent(ws, msg.groupId!!, msg.userId, msg.operatorId, time)
                    SubType.approve -> listener.onApproveEvent(
                        ws,
                        msg.groupId!!,
                        msg.userId,
                        msg.operatorId,
                        time
                    )

                    SubType.add -> listener.onJoinRequest(ws, msg.groupId!!, msg.userId, msg.comment!!, time)
                }
                return
            }

            val messageSign = message.fromJson<ResponseMessage>().echo
            when (messageSign) {
                MessageEchoType.CanSendImage -> listener.onCanSendImageResponse(
                    ws,
                    message.fromJson<CanSend>().data.yes
                )

                MessageEchoType.CanSendRecord -> listener.onCanSendRecordResponse(
                    ws,
                    message.fromJson<CanSend>().data.yes
                )

                MessageEchoType.GetForwardMessage -> listener.onGetForwardMessageResponse(ws, message)
                MessageEchoType.GetFriendList -> listener.onGetFriendListResponse(ws, message.fromJson<FriendList>())
                MessageEchoType.GetGroupInfo -> listener.onGetGroupInfoResponse(ws, message.fromJson<GroupInfo>())
                MessageEchoType.GetGroupList -> listener.onGetGroupListResponse(ws, message.fromJson<GroupList>())
                MessageEchoType.GetGroupMemberList -> listener.onGetGroupMemberListResponse(
                    ws, message.fromJson<GroupMemberList>()
                )

                MessageEchoType.GetGroupMemberInfo -> listener.onGetGroupMemberInfoResponse(
                    ws,
                    message.fromJson<GroupMemberInfo>()
                )

                MessageEchoType.GetLoginInfo -> listener.onGetLoginInfoResponse(ws, message.fromJson<LoginInfo>())
                MessageEchoType.GetStrangerInfo -> listener.onGetStrangerInfoResponse(
                    ws,
                    message.fromJson<StrangerInfo>()
                )

                MessageEchoType.GetVersionInfo -> listener.onGetOneBotVersionInfoResponse(
                    ws, message.fromJson<OneBotVersionInfo>()
                )

                MessageEchoType.GetMessage -> {}

                null -> {
                    val getMsg = message.fromJson<GetMessage>()
                    val metadata = getMsg.echo.split(":").drop(1)
                    val id = metadata.first()
                    val groupId = metadata[1].toLong()
                    val sender = metadata.last().toLong()
                    if (getMsg.echo.startsWith("GetMessage:")) {
                        val msgType = getMsg.data.messageType
                        when (msgType) {
                            MessageType.private -> listener.onGetPrivateMessageResponse(
                                ws, getMsg.data.message, id, sender
                            )

                            MessageType.group -> listener.onGetGroupMessageResponse(
                                ws, getMsg.data.message, id, sender, groupId
                            )
                        }
                    }
                }
            }
        } catch (ex: Exception) {
            this.onError(listener, ws, ex)
        }
    }

    suspend fun onOpen(listener: OBMessage, websocket: WebSocket) {
        println("New connection: ${websocket.remoteSocketAddress}")
        listener.onWebsocketOpenEvent(websocket)
    }

    suspend fun onClose(listener: OBMessage, code: Int, reason: String, remote: Boolean) {
        println("Websocket connection closed")
        listener.onWebsocketCloseEvent(code, reason, remote)
    }

    suspend fun onStart(listener: OBMessage) {
        println("Websocket server started")
        listener.onWebsocketServerStartEvent()
    }

    suspend fun onError(listener: OBMessage, websocket: WebSocket, ex: Exception) {
        listener.onWebsocketErrorEvent(websocket, ex)
    }
}