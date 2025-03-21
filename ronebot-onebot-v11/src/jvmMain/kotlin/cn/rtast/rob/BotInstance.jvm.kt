/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/23
 */

@file:Suppress("unused")

package cn.rtast.rob

import cn.rtast.rob.enums.internal.InstanceType
import cn.rtast.rob.onebot.OneBotAction
import cn.rtast.rob.onebot.OneBotListener
import cn.rtast.rob.scheduler.BotCoroutineScheduler
import cn.rtast.rob.util.ws.WebsocketClient
import cn.rtast.rob.util.ws.WebsocketServer
import java.net.http.WebSocket
import kotlin.time.Duration

public class BotInstance internal constructor(
    private val address: String,
    private val accessToken: String,
    private val listener: OneBotListener,
    private val autoReconnect: Boolean,
    private val port: Int,
    private val instanceType: InstanceType,
    private val path: String,
    private val reconnectInterval: Duration,
    private val executeDuration: Duration
) : IBotInstance(
    address,
    accessToken,
    listener,
    autoReconnect,
    port,
    instanceType,
    path,
    reconnectInterval,
    executeDuration
) {
    /**
     * Bot实例为客户端的时候[websocket]不为null
     * 并且[OneBotAction]所有的操作都使用这个websocket
     * 字段操作
     */
    public var websocket: WebSocket? = null

    /**
     * Bot实例为服务端的时候[websocketServer]不不为null
     * 所有的[OneBotAction]操作都是用websocketServer的
     * [WebSocketServer.connections]列表的客户端连接来操作
     */
    public var websocketServer: WebSocketServer? = null

    /**
     * 实例作用域的scheduler
     */
    public val scheduler: BotCoroutineScheduler<IBotInstance> = BotCoroutineScheduler(this)

    /**
     * 判断action变量是否已经初始化,
     * 并且使用getter来动态的获取是否初始化
     */
    override val isActionInitialized: Boolean get() = ::action.isInitialized

    /**
     * 事件监听器的过滤器
     */
    internal var enableEventListenerFilter: Boolean = true

    /**
     * 开启过滤群聊事件, 开启后只能接收到[listenedGroups]列表内的群聊事件
     */
    public fun enableEventFilter() {
        enableEventListenerFilter = true
    }

    /**
     * 关闭过滤群聊事件
     */
    public fun disableEventFilter() {
        enableEventListenerFilter = false
    }

    /**
     * 设置要监听的群号
     */
    public fun addListeningGroups(vararg groups: Long): IBotInstance {
        groups.forEach { listenedGroups.add(it) }
        return this
    }

    /**
     * 设置要监听的群号
     */
    public fun addListeningGroup(groupId: Long): IBotInstance {
        listenedGroups.add(groupId)
        return this
    }

    /**
     * 批量添加监听群聊
     */
    public fun addListeningGroup(groups: Collection<Long>): IBotInstance {
        listenedGroups.addAll(groups)
        return this
    }

    /**
     * 创建一个Bot实例
     */
    actual override suspend fun createBot(): IBotInstance {
        when (instanceType) {
            InstanceType.Client -> {
                websocket = WebsocketClient(
                    address,
                    accessToken,
                    listener,
                    autoReconnect,
                    this,
                    reconnectInterval.inWholeMilliseconds,
                    executeDuration
                ).also {
                    this.action = it.createAction()
                    it.connectBlocking()
                }
            }

            InstanceType.Server -> {
                websocketServer = WebsocketServer(port, accessToken, listener, this, path, executeDuration).also {
                    this.action = it.createAction()
                    it.start()
                }
            }
        }
        return this
    }

    actual override suspend fun disposeBot() {
        when (instanceType) {
            InstanceType.Client -> websocket?.close()
            InstanceType.Server -> websocketServer?.stop(0, "ROneBot Websocket Server closed manually!")
        }
        System.gc()
    }
}