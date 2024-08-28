/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/26
 */


package cn.rtast.rob.util.ws

import cn.rtast.rob.util.ob.MessageHandler
import cn.rtast.rob.util.ob.OBMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import java.lang.Exception
import java.net.URI

internal class WsClient(
    address: String,
    accessToken: String,
    private val listener: OBMessage
) : WebSocketClient(URI(address), mapOf("Authorization" to "Bearer $accessToken")) {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    private val websocket = this

    override fun onOpen(handshakedata: ServerHandshake) {
        coroutineScope.launch {
            MessageHandler.onOpen(listener, websocket)
        }
    }

    override fun onMessage(message: String) {
        coroutineScope.launch {
            MessageHandler.onMessage(listener, websocket, message)
        }
    }

    override fun onClose(code: Int, reason: String, remote: Boolean) {
        coroutineScope.launch {
            MessageHandler.onClose(listener, code, reason, remote)
        }
    }

    override fun onError(ex: Exception) {}
}