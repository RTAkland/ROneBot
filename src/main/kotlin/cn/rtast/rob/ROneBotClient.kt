/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/26
 */


package cn.rtast.rob

import cn.rtast.rob.entity.GroupMessage
import cn.rtast.rob.util.WsClient
import org.java_websocket.WebSocket


class ROneBotClient {

    private lateinit var ws: WebSocket

    @JvmOverloads
    fun create(address: String, accessToken: String, listener: OBMessage, alsoConnect: Boolean = true): WebSocket {
        ws = WsClient(address, accessToken, listener).also { if (alsoConnect) it.connect() }
        return ws
    }

    fun close() {
        ws.close()
    }
}