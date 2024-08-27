/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/26
 */


package cn.rtast.rob

import cn.rtast.rob.util.MessageCommand
import cn.rtast.rob.util.ob.OBMessage
import cn.rtast.rob.util.ws.WsClient
import cn.rtast.rob.util.ws.WsServer
import org.java_websocket.WebSocket


object ROneBotFactory {

    internal var websocket: WebSocket? = null
    val commandManager = MessageCommand()

    fun createClient(address: String, accessToken: String, listener: OBMessage): ROneBotFactory {
        websocket = WsClient(address, accessToken, listener).also { it.connect() }
        return this
    }

    fun createServer(port: Int, listener: OBMessage): ROneBotFactory {
        WsServer(port, listener).also { it.start() }
        return this
    }
}