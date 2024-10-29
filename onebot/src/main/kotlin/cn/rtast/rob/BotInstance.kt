/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/23
 */

@file:Suppress("unused")

package cn.rtast.rob

import cn.rtast.rob.common.BaseBotInstance
import cn.rtast.rob.enums.internal.InstanceType
import cn.rtast.rob.util.CommandManager
import cn.rtast.rob.util.ob.OneBotAction
import cn.rtast.rob.util.ob.OneBotListener
import cn.rtast.rob.util.scheduler.BotCoroutineScheduler
import cn.rtast.rob.util.ws.WsClient
import cn.rtast.rob.util.ws.WsServer
import org.java_websocket.WebSocket
import org.java_websocket.server.WebSocketServer

/**
 * 一个Bot实例, 最好使用[ROneBotFactory]的静态方法进行
 * 创建Bot, 这样所有通过[ROneBotFactory]注册的Bot实例
 * 都会被存储再[ROneBotFactory.botInstances]列表中
 * 但是[BotInstance]的构造器已经被设置成了
 * `internal`所以用户没有办法直接创建Bot实例
 */
class BotInstance internal constructor(
    private val address: String,
    private val accessToken: String,
    private val listener: OneBotListener,
    private val autoReconnect: Boolean,
    private val messageQueueLimit: Int,
    private val port: Int,
    private val instanceType: InstanceType
) : BaseBotInstance {
    /**
     * 设置监听的群聊
     */
    internal val listenedGroups = mutableListOf<Long>()

    /**
     * 用于访问action
     */
    lateinit var action: OneBotAction

    /**
     * Bot实例为客户端的时候[websocket]不为null
     * 并且[OneBotAction]所有的操作都使用这个websocket
     * 字段操作
     */
    var websocket: WebSocket? = null

    /**
     * Bot实例为服务端的时候[websocketServer]不不为null
     * 所有的[OneBotAction]操作都是用websocketServer的
     * [WebSocketServer.connections]列表的客户端连接来操作
     */
    var websocketServer: WebSocketServer? = null

    /**
     * 实例作用域的scheduler
     */
    val scheduler = BotCoroutineScheduler(this)

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
     */
    val isActionInitialized get() = ::action.isInitialized

    /**
     * 设置要监听的群号
     */
    fun addListeningGroups(vararg groups: Long) = groups.forEach { listenedGroups.add(it) }

    /**
     * 设置要监听的群号
     */
    fun addListeningGroup(groupId: Long) = listenedGroups.add(groupId)

    /**
     * 创建一个Bot实例
     */
    override suspend fun createBot(): BotInstance {
        when (instanceType) {
            InstanceType.Client -> {
                websocket = WsClient(address, accessToken, listener, autoReconnect, messageQueueLimit, this).also {
                    this.action = it.createAction()
                    it.connectBlocking()
                }
            }

            InstanceType.Server -> {
                websocketServer = WsServer(port, accessToken, listener, messageQueueLimit, this).also {
                    this.action = it.createAction()
                    it.start()
                }
            }
        }
        return this
    }

    override suspend fun disposeBot() {
        when (instanceType) {
            InstanceType.Client -> websocket?.close()
            InstanceType.Server -> websocketServer?.stop(0, "ROneBot Websocket Server closed manually!")
        }
        System.gc()
    }
}