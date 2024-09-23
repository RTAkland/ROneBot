/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/26
 */


package cn.rtast.rob

import cn.rtast.rob.util.CommandManager
import cn.rtast.rob.util.onebot.OneBotAction
import cn.rtast.rob.util.onebot.OneBotListener
import cn.rtast.rob.util.ws.WsClient
import cn.rtast.rob.util.ws.WsServer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.java_websocket.WebSocket
import org.java_websocket.server.WebSocketServer


object ROneBotFactory {

    internal var websocket: WebSocket? = null
    internal var websocketServer: WebSocketServer? = null
    internal lateinit var action: OneBotAction
    internal var isServer = false
    internal val actionCoroutineScope = CoroutineScope(Dispatchers.IO)
    private val listenedGroups = mutableListOf<Long>()
    val commandManager = CommandManager()

    /**
     * 创建一个Websocket客户端连接到OneBot实现
     * 返回ROneBotFactory本身
     */
    fun createClient(
        address: String,
        accessToken: String,
        listener: OneBotListener,
        autoReconnect: Boolean = true,
        messageQueueLimit: Int = 512
    ): ROneBotFactory {
        action = listener
        websocket = WsClient(
            address,
            accessToken,
            listener,
            autoReconnect,
            messageQueueLimit
        ).also { it.connect() }
        return this
    }

    /**
     * 监听一个指定的端口来监听Websocket连接
     * 让OneBot实现作为客户端连接到ROB
     */
    fun createServer(
        port: Int,
        accessToken: String,
        listener: OneBotListener,
        messageQueueLimit: Int = 512
    ): ROneBotFactory {
        action = listener
        isServer = true
        websocketServer = WsServer(
            port,
            accessToken,
            listener,
            messageQueueLimit
        ).also { it.start() }
        return this
    }

    /**
     * 设置要监听的群号
     */
    fun addListeningGroups(vararg groups: Long) {
        groups.forEach { listenedGroups.add(it) }
    }

    fun getListeningGroups() = listenedGroups
}