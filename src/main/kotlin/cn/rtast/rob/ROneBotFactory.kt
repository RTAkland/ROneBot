/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/26
 */


package cn.rtast.rob

import cn.rtast.rob.util.ob.OBMessage
import cn.rtast.rob.util.ws.WsClient
import cn.rtast.rob.util.ws.WsServer
import org.java_websocket.WebSocket
import org.java_websocket.server.WebSocketServer


object ROneBotFactory {

    private lateinit var wsClient: WebSocket
    private lateinit var wsServer: WebSocketServer

    @JvmOverloads
    fun createClient(
        address: String, accessToken: String, listener: OBMessage, alsoConnect: Boolean = true
    ): WebSocket {
        wsClient = WsClient(address, accessToken, listener).also { if (alsoConnect) it.connect() }
        return wsClient
    }

    fun close() {
        wsClient.close()
    }

    @JvmOverloads
    fun createServer(port: Int, listener: OBMessage, start: Boolean = true): WebSocketServer {
        wsServer = WsServer(port, listener).also { if (start) it.start() }
        return wsServer
    }

    fun start() {
        wsServer.start()
    }
}