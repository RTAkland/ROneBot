/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/26
 */


package cn.rtast.rob

import cn.rtast.rob.util.CommandManager
import cn.rtast.rob.util.ob.OBAction
import cn.rtast.rob.util.ob.OBMessage
import cn.rtast.rob.util.ws.WsClient
import cn.rtast.rob.util.ws.WsServer
import org.java_websocket.WebSocket
import org.java_websocket.server.WebSocketServer


object ROneBotFactory {

    internal var websocket: WebSocket? = null
    internal var websocketServer: WebSocketServer? = null
    internal lateinit var action: OBAction
    internal var isServer = false
    private val listenedGroups = mutableListOf<Long>()
    val commandManager = CommandManager()

    fun createClient(
        address: String,
        accessToken: String,
        listener: OBMessage,
        autoReconnect: Boolean = true
    ): ROneBotFactory {
        action = listener
        websocket = WsClient(address, accessToken, listener, autoReconnect).also { it.connect() }
        return this
    }

    fun createServer(port: Int, accessToken: String, listener: OBMessage): ROneBotFactory {
        action = listener
        isServer = true
        websocketServer = WsServer(port, accessToken, listener).also { it.start() }
        return this
    }

    /**
     * set group ids to listen and reply, if is empty then listen all groups
     */
    fun addListeningGroups(vararg groups: Long) {
        groups.forEach { listenedGroups.add(it) }
    }

    fun getListeningGroups() = listenedGroups
}