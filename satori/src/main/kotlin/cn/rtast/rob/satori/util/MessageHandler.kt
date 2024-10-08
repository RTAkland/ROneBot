/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/8
 */


package cn.rtast.rob.satori.util

import cn.rtast.rob.common.util.toJson
import cn.rtast.rob.satori.RSatoriFactory
import cn.rtast.rob.satori.entity.out.AuthPacket
import org.java_websocket.handshake.ServerHandshake
import java.lang.Exception

object MessageHandler {

    private fun sendAuthPacket() {
        val authPacket = AuthPacket(body = AuthPacket.AuthBody(RSatoriFactory.token)).toJson()
        RSatoriFactory.client.send(authPacket)
    }

    fun onOpen(listener: SatoriListener, handshake: ServerHandshake) {
        this.sendAuthPacket()
        println("open")
    }

    fun onMessage(listener: SatoriListener, message: String) {
        println(message)
    }

    fun onClose(listener: SatoriListener, code: Int, reason: String, remote: Boolean) {
        println("close")
    }

    fun onError(listener: SatoriListener, ex: Exception) {
        println("error")
    }
}