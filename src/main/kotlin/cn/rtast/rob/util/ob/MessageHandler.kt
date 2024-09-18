/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/26
 */


package cn.rtast.rob.util.ob

import cn.rtast.rob.ROneBotFactory
import cn.rtast.rob.ROneBotFactory.commandManager
import cn.rtast.rob.entity.*
import cn.rtast.rob.enums.*
import cn.rtast.rob.util.fromJson
import org.java_websocket.WebSocket

object MessageHandler {

    private val listeningGroups = ROneBotFactory.getListeningGroups()

    suspend fun onMessage(listener: OBMessage, message: String) {
        try {
            listener.onMessage(message)
            val serializedMessage = message.fromJson<BaseMessage>()
            if (serializedMessage.metaEventType != null) {
                when (serializedMessage.metaEventType) {
                    MetaEventType.heartbeat -> listener.onHeartBeatEvent(message.fromJson<HeartBeatEvent>())
                    MetaEventType.lifecycle -> listener.onConnectEvent(message.fromJson<ConnectEvent>())
                }
                return
            }

            if (serializedMessage.postType == PostType.message) {
                when (serializedMessage.messageType) {
                    MessageType.group -> {
                        val msg = message.fromJson<GroupMessage>()
                        val oldSender = msg.sender
                        val newSenderWithGroupId = Sender(
                            oldSender.userId,
                            oldSender.nickname,
                            oldSender.sex,
                            oldSender.role,
                            oldSender.card,
                            oldSender.level,
                            oldSender.age,
                            msg.groupId
                        )
                        msg.sender = newSenderWithGroupId
                        if (msg.groupId !in listeningGroups && listeningGroups.isNotEmpty()) return
                        msg.message.distinctBy { it.type }.forEach {
                            if (it.type == ArrayMessageType.reply) {
                                listener.onBeRepliedInGroup(msg)
                                return@forEach
                            }
                            if (it.type == ArrayMessageType.at) {
                                listener.onBeAt(msg)
                                return@forEach
                            }
                        }
                        commandManager.handleGroup(listener, msg)
                        listener.onGroupMessage(msg, message)
                    }

                    MessageType.private -> {
                        val msg = message.fromJson<PrivateMessage>()
                        msg.message.forEach {
                            if (it.type == ArrayMessageType.reply) {
                                listener.onBeRepliedInPrivate(msg)
                                return@forEach
                            }
                        }
                        commandManager.handlePrivate(listener, msg)
                        listener.onPrivateMessage(msg, message)
                    }

                    null -> listener.onMessage(message)
                }
                return
            }

            if (serializedMessage.postType == PostType.notice) {
                val time = serializedMessage.time
                val msg = message.fromJson<NoticeEvent>()
                if (msg.groupId != null && msg.groupId !in listeningGroups && listeningGroups.isNotEmpty()) return
                when (serializedMessage.noticeType) {
                    NoticeType.group_recall -> {
                        listener.onGroupMessageRevoke(
                            GroupRevokeMessage(
                                msg.groupId!!,
                                msg.userId,
                                msg.messageId!!,
                                msg.operatorId
                            )
                        )
                        return
                    }

                    NoticeType.friend_recall -> {
                        listener.onPrivateMessageRevoke(
                            PrivateRevokeMessage(
                                msg.userId,
                                msg.messageId!!,
                                msg.operatorId
                            )
                        )
                        return
                    }

                    NoticeType.group_upload, NoticeType.offline_file -> {
                        val file = message.fromJson<FileEvent>()
                        if (file.groupId == null) {
                            listener.onPrivateFileUpload(file.userId, file)
                        } else {
                            listener.onGroupFileUpload(file.groupId, file.userId, file)
                        }
                        return
                    }

                    null -> {}
                }
                when (serializedMessage.subType) {
                    SubType.kick -> listener.onMemberKick(msg.groupId!!, msg.operatorId, time)
                    SubType.kick_me -> listener.onBeKicked(msg.groupId!!, msg.operatorId, time)
                    SubType.unset -> listener.onUnsetOperator(msg.groupId!!, msg.operatorId, time)
                    SubType.set -> listener.onSetOperator(msg.groupId!!, msg.operatorId, time)
                    SubType.ban -> listener.onBan(msg.groupId!!, msg.operatorId, msg.duration!!, time)
                    SubType.lift_ban -> listener.onPardon(msg.groupId!!, msg.operatorId, msg.duration!!, time)
                    SubType.leave -> listener.onLeaveEvent(msg.groupId!!, msg.userId, msg.operatorId, time)
                    SubType.invite -> listener.onInviteEvent(msg.groupId!!, msg.userId, msg.operatorId, time)
                    SubType.approve -> listener.onApproveEvent(msg.groupId!!, msg.userId, msg.operatorId, time)
                    SubType.add -> listener.onJoinRequest(msg.groupId!!, msg.userId, msg.comment!!, time)
                }
                return
            }

            val messageSign = message.fromJson<ResponseMessage>().echo
            when (messageSign) {
                MessageEchoType.CanSendImage -> listener.onCanSendImageResponse(message.fromJson<CanSend>().data.yes)
                MessageEchoType.CanSendRecord -> listener.onCanSendRecordResponse(message.fromJson<CanSend>().data.yes)
                MessageEchoType.GetForwardMessage -> listener.onGetForwardMessageResponse(message)
                MessageEchoType.GetFriendList -> listener.onGetFriendListResponse(message.fromJson<FriendList>())
                MessageEchoType.GetGroupInfo -> listener.onGetGroupInfoResponse(message.fromJson<GroupInfo>())
                MessageEchoType.GetGroupList -> listener.onGetGroupListResponse(message.fromJson<GroupList>())
                MessageEchoType.GetGroupMemberList -> listener.onGetGroupMemberListResponse(message.fromJson<GroupMemberList>())
                MessageEchoType.GetGroupMemberInfo -> listener.onGetGroupMemberInfoResponse(message.fromJson<GroupMemberInfo>())
                MessageEchoType.GetLoginInfo -> listener.onGetLoginInfoResponse(message.fromJson<LoginInfo>())
                MessageEchoType.GetStrangerInfo -> listener.onGetStrangerInfoResponse(message.fromJson<StrangerInfo>())
                MessageEchoType.GetVersionInfo -> listener.onGetOneBotVersionInfoResponse(message.fromJson<OneBotVersionInfo>())
                MessageEchoType.GetMessage -> {}

                null -> {
                    val getMsg = message.fromJson<GetMessage>()
                    if (getMsg.echo == null) return
                    val metadata = getMsg.echo.split(":").drop(1)
                    val id = metadata.first()
                    val groupId = metadata.last().toLong()
                    if (getMsg.echo.startsWith("GetMessage:")) {
                        val msgType = getMsg.data.messageType
                        when (msgType) {
                            MessageType.private -> listener.onGetPrivateMessageResponse(
                                GetMessage(
                                    GetMessage.Data(
                                        getMsg.data.time,
                                        getMsg.data.messageType,
                                        getMsg.data.message,
                                        getMsg.data.messageId,
                                        getMsg.data.sender, null, id
                                    ),
                                    getMsg.echo
                                )
                            )

                            MessageType.group -> listener.onGetGroupMessageResponse(
                                GetMessage(
                                    GetMessage.Data(
                                        getMsg.data.time,
                                        getMsg.data.messageType,
                                        getMsg.data.message,
                                        getMsg.data.messageId,
                                        getMsg.data.sender, groupId, id
                                    ),
                                    getMsg.echo
                                )
                            )
                        }
                    }
                }
            }
        } catch (ex: Exception) {
            this.onError(listener, ex)
        }
    }

    suspend fun onOpen(listener: OBMessage, websocket: WebSocket) {
        println("New connection: ${websocket.remoteSocketAddress}")
        listener.onWebsocketOpenEvent()
    }

    suspend fun onClose(listener: OBMessage, code: Int, reason: String, remote: Boolean, ws: WebSocket) {
        println("Websocket connection closed(${ws.remoteSocketAddress})")
        listener.onWebsocketCloseEvent(code, reason, remote)
    }

    suspend fun onStart(listener: OBMessage, port: Int) {
        println("Websocket server started on $port")
        listener.onWebsocketServerStartEvent()
    }

    suspend fun onError(listener: OBMessage, ex: Exception) {
        listener.onWebsocketErrorEvent(ex)
    }
}