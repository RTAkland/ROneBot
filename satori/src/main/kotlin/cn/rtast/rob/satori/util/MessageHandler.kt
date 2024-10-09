/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/8
 */

@file:Suppress("unused")

package cn.rtast.rob.satori.util

import cn.rtast.rob.common.util.fromJson
import cn.rtast.rob.common.util.toJson
import cn.rtast.rob.satori.RSatoriFactory
import cn.rtast.rob.satori.entity.internal.BaseMessage
import cn.rtast.rob.satori.entity.LoginInfo
import cn.rtast.rob.satori.entity.out.AuthPacketOut
import cn.rtast.rob.satori.entity.out.PingPacketOut
import cn.rtast.rob.satori.enums.OPCode
import cn.rtast.rob.satori.enums.forCode
import org.java_websocket.handshake.ServerHandshake
import java.lang.Exception
import java.util.concurrent.Executors

object MessageHandler {

    private val scheduler = Executors.newScheduledThreadPool(1)
    private var ready = false

    fun sendAuthPacket() {
        val authPacket = AuthPacketOut(body = AuthPacketOut.AuthBody(RSatoriFactory.token)).toJson()
        RSatoriFactory.client.send(authPacket)
    }

    fun startHeartbeat() {
        val pingPacket = PingPacketOut().toJson()
        RSatoriFactory.client.send(pingPacket)
    }

    fun onOpen(listener: SatoriListener, handshake: ServerHandshake) {
        this.sendAuthPacket()
        this.startHeartbeat()
    }

    suspend fun onMessage(listener: SatoriListener, message: String) {
        val baseMessage = message.fromJson<BaseMessage>()
        val opCode = baseMessage.op.forCode()
        when (opCode) {
            OPCode.EVENT -> {
                println(message)
            }
            OPCode.Pong -> listener.onPong()
            OPCode.READY -> listener.onReady(message.fromJson<LoginInfo>())
            OPCode.IDENTIFY -> {}
            OPCode.Ping -> {}
        }
    }

    fun onClose(listener: SatoriListener, code: Int, reason: String, remote: Boolean) {
    }

    fun onError(listener: SatoriListener, ex: Exception) {
        ex.printStackTrace()
    }
}