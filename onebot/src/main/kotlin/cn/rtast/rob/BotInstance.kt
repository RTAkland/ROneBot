/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/23
 */

package cn.rtast.rob

import cn.rtast.rob.enums.internal.InstanceType
import cn.rtast.rob.util.CommandManager
import cn.rtast.rob.util.ob.OneBotAction
import cn.rtast.rob.util.ob.OneBotListener
import cn.rtast.rob.util.scheduler.CoroutineScheduler
import cn.rtast.rob.util.ws.WsClient
import cn.rtast.rob.util.ws.WsServer
import org.java_websocket.WebSocket
import org.java_websocket.server.WebSocketServer

class BotInstance(
    private val address: String,
    private val accessToken: String,
    private val listener: OneBotListener,
    private val autoReconnect: Boolean,
    private val messageQueueLimit: Int,
    private val port: Int,
    private val instanceType: InstanceType
) {
    var websocket: WebSocket? = null
    var websocketServer: WebSocketServer? = null
    internal var isServer = false

    /**
     * 实例作用域的scheduler
     */
    val scheduler = CoroutineScheduler(this)

    /**
     * 设置监听的群聊
     */
    internal val listenedGroups = mutableListOf<Long>()

    /**
     * 用于访问action
     */
    lateinit var action: OneBotAction

    /**
     * 在单个实例中的命令管理器
     * 这个命令管理器优先级比全局作用域高
     * 但是如果在当前实例注册了命令又
     * 在全局作用域注册了命令那么这个命令会被执行两次
     */
    val commandManager = CommandManager()

    /**
     * 判断action变量是否已经初始化,
     * 并且使用getter来动态的获取是否初始化
     * internal only
     */
    internal val isActionInitialized get() = ::action.isInitialized


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

    /**
     * 创建一个Bot实例
     */
    fun createBot(): BotInstance {
        when (instanceType) {
            InstanceType.Client -> {
                websocket = WsClient(
                    address,
                    accessToken,
                    listener,
                    autoReconnect,
                    messageQueueLimit,
                    this, OneBotAction(this, InstanceType.Client, websocket)
                ).also { it.connectBlocking() }
                action = OneBotAction(this, InstanceType.Client, websocket)
            }

            InstanceType.Server -> {
                isServer = true
                websocketServer = WsServer(
                    port,
                    accessToken,
                    listener,
                    messageQueueLimit,
                    this, OneBotAction(this, InstanceType.Server, websocket as WebSocket)
                ).also { it.start() }
                action = OneBotAction(this, InstanceType.Server, websocket as WebSocket)
            }
        }
        return this
    }
}