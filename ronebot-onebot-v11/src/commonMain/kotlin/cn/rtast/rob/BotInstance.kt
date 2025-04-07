/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/23
 */

@file:Suppress("unused")
@file:OptIn(InternalROneBotApi::class)

package cn.rtast.rob

import cn.rtast.klogging.LogLevel
import cn.rtast.rob.annotations.InternalROneBotApi
import cn.rtast.rob.enums.internal.InstanceType
import cn.rtast.rob.onebot.OneBotAction
import cn.rtast.rob.onebot.OneBotListener
import cn.rtast.rob.scheduler.BotCoroutineScheduler
import cn.rtast.rob.util.MessageHandler
import cn.rtast.rob.util.getLogger
import cn.rtast.rob.util.ws.WebsocketSession
import cn.rtast.rob.util.ws.createClient
import cn.rtast.rob.util.ws.createServer
import love.forte.plugin.suspendtrans.annotation.JvmAsync
import love.forte.plugin.suspendtrans.annotation.JvmBlocking
import kotlin.jvm.JvmName
import kotlin.time.Duration

/**
 * 一个Bot实例, 最好使用[OneBotFactory]的静态方法进行
 * 创建Bot, 这样所有通过[OneBotFactory]注册的Bot实例
 * 都会被存储再[cn.rtast.rob.util.BotManager.botInstances]列表中
 * 但是[BotInstance]的构造器已经被设置成了
 * `internal`所以用户没有办法直接创建Bot实例
 */
public class BotInstance internal constructor(
    private val address: String,
    private val accessToken: String,
    private val listener: OneBotListener,
    private val autoReconnect: Boolean,
    private val port: Int,
    private val instanceType: InstanceType,
    private val path: String,
    private val reconnectInterval: Duration,
    private val executeDuration: Duration,
    logLevel: LogLevel
) : BaseBotInstance {

    @get:JvmName("#$")
    internal val logger = getLogger(if (instanceType == InstanceType.Server) "[S]" else "[C]").apply { setLoggingLevel(logLevel) }

    /**
     * 设置监听的群聊
     */
    @get:JvmName("#$")
    internal val listenedGroups = mutableListOf<Long>()

    /**
     * 消息处理器
     */
    @get:JvmName("#$")
    internal val messageHandler = MessageHandler(this)

    /**
     * 用于访问action
     */
    public lateinit var action: OneBotAction

    /**
     * Bot实例为客户端的时候[websocket]不为null
     * 并且[OneBotAction]所有的操作都使用这个websocket
     * 字段操作
     */
    public var websocket: WebsocketSession? = null

    /**
     * Bot实例为服务端的时候[websocketServer]不不为null
     */
    public var websocketServer: WebsocketSession? = null

    /**
     * 实例作用域的scheduler
     */
    public val scheduler: BotCoroutineScheduler<BotInstance> = BotCoroutineScheduler(this)

    /**
     * 判断action变量是否已经初始化,
     * 并且使用getter来动态的获取是否初始化
     */
    override val isActionInitialized: Boolean get() = ::action.isInitialized

    /**
     * 事件监听器的过滤器
     */
    @get:JvmName("#$")
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
    public fun addListeningGroups(vararg groups: Long): BotInstance {
        groups.forEach { listenedGroups.add(it) }
        return this
    }

    /**
     * 设置要监听的群号
     */
    public fun addListeningGroup(groupId: Long): BotInstance {
        listenedGroups.add(groupId)
        return this
    }

    /**
     * 批量添加监听群聊
     */
    public fun addListeningGroup(groups: Collection<Long>): BotInstance {
        listenedGroups.addAll(groups)
        return this
    }

    /**
     * 创建一个Bot实例
     */
    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    override suspend fun createBot(): BotInstance {
        when (instanceType) {
            InstanceType.Client -> {
                websocket = createClient(
                    address,
                    accessToken,
                    listener,
                    autoReconnect,
                    this,
                    reconnectInterval.inWholeMilliseconds,
                    executeDuration
                )
            }

            InstanceType.Server -> {
                websocketServer = createServer(port, accessToken, listener, this, path, executeDuration)
            }
        }
        return this
    }

    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    override suspend fun disposeBot() {
        when (instanceType) {
            InstanceType.Client -> websocket?.closeClient()
            InstanceType.Server -> websocketServer?.closeServer()
        }
    }

    override fun toString(): String {
        return "BotInstance(address=\"$address\", " +
                "accessToken=\"********\", " +
                "autoReconnect=$autoReconnect, " +
                "port=$port, " +
                "instanceType=$instanceType, " +
                "path=\"$path\", " +
                "reconnectInterval=${reconnectInterval.inWholeSeconds})"
    }
}