/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/26
 */

@file:Suppress("unused")

package cn.rtast.rob

import cn.rtast.rob.util.scheduler.CoroutineScheduler
import cn.rtast.rob.util.CommandManager
import cn.rtast.rob.util.ob.OneBotAction
import cn.rtast.rob.util.ob.OneBotListener
import cn.rtast.rob.util.ws.WsClient
import cn.rtast.rob.util.ws.WsServer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.java_websocket.WebSocket
import org.java_websocket.server.WebSocketServer


object ROneBotFactory {

    private val listenedGroups = mutableListOf<Long>()
    internal var isServer = false
    internal val actionCoroutineScope = CoroutineScope(Dispatchers.IO)
    lateinit var action: OneBotAction
    val commandManager = CommandManager()
    val scheduler = CoroutineScheduler()
    var websocket: WebSocket? = null
    var websocketServer: WebSocketServer? = null

    /**
     * 判断action变量是否已经初始化,
     * 并且使用getter来动态的获取是否初始化
     * internal only
     */
    internal val isActionInitialized get() = ::action.isInitialized

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
        websocket = WsClient(
            address,
            accessToken,
            listener,
            autoReconnect,
            messageQueueLimit
        ).also { it.connectBlocking() }
        action = listener
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
        isServer = true
        websocketServer = WsServer(
            port,
            accessToken,
            listener,
            messageQueueLimit
        ).also { it.start() }
        action = listener
        return this
    }

    /**
     * 设置要监听的群号
     */
    fun addListeningGroups(vararg groups: Long) {
        groups.forEach { listenedGroups.add(it) }
    }

    /**
     * 获取所有被监听的群号
     */
    fun getListeningGroups() = listenedGroups
}