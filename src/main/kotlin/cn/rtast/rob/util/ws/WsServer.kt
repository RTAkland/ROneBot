/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/26
 */


package cn.rtast.rob.util.ws

import cn.rtast.rob.ROneBotFactory.websocket
import cn.rtast.rob.util.ob.MessageHandler
import cn.rtast.rob.util.ob.OBMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.java_websocket.WebSocket
import org.java_websocket.handshake.ClientHandshake
import org.java_websocket.server.WebSocketServer
import java.lang.Exception
import java.net.InetSocketAddress

internal class WsServer(
    port: Int,
    private val listener: OBMessage
) : WebSocketServer(InetSocketAddress(port)) {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    override fun onOpen(conn: WebSocket, handshake: ClientHandshake) {
        if (websocket == null) {
            websocket = conn
        }
        coroutineScope.launch {
            MessageHandler.onOpen(listener, conn)
        }
    }

    override fun onClose(conn: WebSocket, code: Int, reason: String, remote: Boolean) {
        websocket = null
        coroutineScope.launch {
            MessageHandler.onClose(listener, code, reason, remote)
        }
    }

    override fun onMessage(conn: WebSocket, message: String) {
        coroutineScope.launch {
            MessageHandler.onMessage(listener, conn, message)
        }
    }

    override fun onError(conn: WebSocket, ex: Exception) {}

    override fun onStart() {
        coroutineScope.launch {
            MessageHandler.onStart(listener)
        }
    }
}