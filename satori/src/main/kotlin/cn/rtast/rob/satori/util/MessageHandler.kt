/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/8
 */

package cn.rtast.rob.satori.util

import cn.rtast.rob.common.util.fromJson
import cn.rtast.rob.common.util.toJson
import cn.rtast.rob.satori.RSatoriFactory
import cn.rtast.rob.satori.entity.BaseMessage
import cn.rtast.rob.satori.entity.GroupMessage
import cn.rtast.rob.satori.entity.GroupRevokeMessage
import cn.rtast.rob.satori.entity.LoginInfo
import cn.rtast.rob.satori.entity.PrivateMessage
import cn.rtast.rob.satori.entity.PrivateRevokeMessage
import cn.rtast.rob.satori.entity.internal.OPMessage
import cn.rtast.rob.satori.entity.out.AuthPacketOut
import cn.rtast.rob.satori.entity.out.PingPacketOut
import cn.rtast.rob.satori.enums.OPCode
import cn.rtast.rob.satori.enums.forCode
import org.java_websocket.handshake.ServerHandshake
import java.lang.Exception
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

object MessageHandler {

    private val scheduler = Executors.newScheduledThreadPool(1)

    fun sendAuthPacket() {
        val authPacket = AuthPacketOut(body = AuthPacketOut.AuthBody(RSatoriFactory.token)).toJson()
        RSatoriFactory.client.send(authPacket)
    }

    fun startHeartbeat() {
        val task = Runnable {
            val pingPacket = PingPacketOut().toJson()
            RSatoriFactory.client.send(pingPacket)
        }
        scheduler.scheduleAtFixedRate(task, 0, 5, TimeUnit.SECONDS)
    }

    suspend fun onOpen(listener: SatoriListener, handshake: ServerHandshake) {
        this.sendAuthPacket()
        this.startHeartbeat()
        listener.onWebsocketOpen(handshake)
    }

    suspend fun onMessage(listener: SatoriListener, message: String) {
        val baseMessage = message.fromJson<OPMessage>()
        val opCode = baseMessage.op.forCode()
        when (opCode) {
            OPCode.EVENT -> {
                val generalMessage = message.fromJson<BaseMessage>()
                when (generalMessage.body.type) {
                    "message-created" -> {
                        if (generalMessage.body.member != null) {
                            listener.onGroupMessage(message.fromJson<GroupMessage>())
                        } else {
                            listener.onPrivateMessage(message.fromJson<PrivateMessage>())
                        }
                    }

                    "message-deleted" -> {
                        if (generalMessage.body.member != null) {
                            listener.onGroupMessageRevoke(message.fromJson<GroupRevokeMessage>())
                        } else {
                            listener.onPrivateMessageRevoke(message.fromJson<PrivateRevokeMessage>())
                        }
                    }
                }
            }

            OPCode.Pong -> listener.onPong()
            OPCode.READY -> listener.onReady(message.fromJson<LoginInfo>())
            OPCode.IDENTIFY -> {}  // nothing here
            OPCode.Ping -> {}  // nothing here
        }
    }

    suspend fun onClose(listener: SatoriListener, code: Int, reason: String, remote: Boolean) =
        listener.onWebsocketClose(reason, code, remote)

    suspend fun onError(listener: SatoriListener, ex: Exception) =
        listener.onWebsocketError(ex)
}