/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/12
 */


package cn.rtast.rob.qqbot.legacy.util

import cn.rtast.rob.qqbot.legacy.qqbot.QQBotAction
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import java.net.URI

class WsClient internal constructor(
    private val address: String,
    private val action: QQBotAction
) : WebSocketClient(URI(address)) {
    override fun onOpen(handshakedata: ServerHandshake) {
        TODO("Not yet implemented")
    }

    override fun onMessage(message: String) {
        TODO("Not yet implemented")
    }

    override fun onClose(code: Int, reason: String, remote: Boolean) {
        TODO("Not yet implemented")
    }

    override fun onError(ex: Exception) {
        TODO("Not yet implemented")
    }
}