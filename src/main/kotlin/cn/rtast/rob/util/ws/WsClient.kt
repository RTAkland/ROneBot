/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/26
 */


package cn.rtast.rob.util.ws

import cn.rtast.rob.util.ob.MessageHandler
import cn.rtast.rob.util.ob.OBMessage
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
        MessageHandler.onOpen(listener, this)
    }

    override fun onMessage(message: String) {
        MessageHandler.onMessage(listener, this, message)
    }

    override fun onClose(code: Int, reason: String, remote: Boolean) {
        MessageHandler.onClose(listener, code, reason, remote)
    }

    override fun onError(ex: Exception) {}
}