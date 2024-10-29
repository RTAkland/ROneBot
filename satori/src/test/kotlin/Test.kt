/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/8
 */

import cn.rtast.rob.satori.RSatoriFactory
import cn.rtast.rob.satori.entity.GroupMessage
import cn.rtast.rob.satori.util.SatoriAction
import cn.rtast.rob.satori.util.SatoriListener
import org.java_websocket.handshake.ServerHandshake

class RSatori : SatoriListener {
    override suspend fun onGroupMessage(message: GroupMessage.Message) {
        println(message.action)
    }

    override suspend fun onWebsocketError(action: SatoriAction, e: Exception) {
        e.printStackTrace()
    }

    override suspend fun onWebsocketOpen(action: SatoriAction, handshake: ServerHandshake) {
        println("open")
    }
}

suspend fun main() {
    val client = RSatoriFactory.createClient("ws://127.0.0.1:9999", "114514ghpA@", RSatori())
}