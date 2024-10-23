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
    private val listenedGroups = mutableListOf<Long>()
    internal var isServer = false
    val commandManager = CommandManager()
    val scheduler = CoroutineScheduler(this)
    var websocket: WebSocket? = null
    var websocketServer: WebSocketServer? = null
    lateinit var action: OneBotAction

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
                ).also {
                    it.start()
                }
                action = OneBotAction(this, InstanceType.Server, websocket as WebSocket)
            }
        }
        return this
    }
}