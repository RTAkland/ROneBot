/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/26
 */


package cn.rtast.rob.util.ws

import cn.rtast.rob.util.ob.MessageHandler
import cn.rtast.rob.util.ob.OBMessage
import org.java_websocket.WebSocket
import org.java_websocket.handshake.ClientHandshake
import org.java_websocket.server.WebSocketServer
import java.lang.Exception
import java.net.InetSocketAddress

internal class WsServer(
    port: Int,
    private val listener: OBMessage
) : WebSocketServer(InetSocketAddress(port)) {
    override fun onOpen(conn: WebSocket, handshake: ClientHandshake) {
        MessageHandler.onOpen(listener, conn)
    }

    override fun onClose(conn: WebSocket, code: Int, reason: String, remote: Boolean) {
        MessageHandler.onClose(listener, code, reason, remote)
    }

    override fun onMessage(conn: WebSocket, message: String) {
        MessageHandler.onMessage(listener, conn, message)
    }

    override fun onError(conn: WebSocket, ex: Exception) {
    }

    override fun onStart() {
        MessageHandler.onStart(listener)
    }
}