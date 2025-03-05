/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/26
 */

@file:Suppress("unused")

package cn.rtast.rob

import cn.rtast.rob.command.BrigadierCommandManagerImpl
import cn.rtast.rob.command.CommandManagerImpl
import cn.rtast.rob.entity.GroupMessage
import cn.rtast.rob.entity.PrivateMessage
import cn.rtast.rob.enums.internal.InstanceType
import cn.rtast.rob.interceptor.IExecutionInterceptor
import cn.rtast.rob.onebot.OneBotListener
import cn.rtast.rob.scheduler.GlobalCoroutineScheduler
import cn.rtast.rob.session.FunctionalSessionManager
import cn.rtast.rob.session.SessionManager
import cn.rtast.rob.util.BaseCommand
import cn.rtast.rob.util.BotManager
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds


/**
 * OneBot协议的入口点所有Bot实例都从
 * 此静态类中创建
 */
public object ROneBotFactory : BotFactory {

    /**
     * 所有Bot实例的管理器
     */
    public val botManager: BotManager = BotManager()

    /**
     * 动态的获取所有的Bot实例
     */
    internal val botInstances get() = botManager.botInstances.keys.toList()

    /**
     * 全局作用域的任务调度器但是这个调度器执行任务
     * 并不会判断Bot实例是否已经初始化完成
     * 所以你需要自行[forEach]然后判断每个Bot实例
     * 是否已经初始化完成, 否则会抛出错误
     */
    public val globalScheduler: GlobalCoroutineScheduler<BotInstance> = GlobalCoroutineScheduler(botInstances)

    /**
     * 在全局作用域的命令管理器
     */
    public val commandManager: CommandManagerImpl = CommandManagerImpl()

    /**
     * 通过继承来实现命令的方式的会话管理器
     */
    public val sessionManager: SessionManager = SessionManager()

    /**
     * 函数式命令处理器的会话管理器
     */
    public val functionalSessionManager: FunctionalSessionManager = FunctionalSessionManager()

    /**
     * 使用Brigadier来管理的指令
     */
    public val brigadierCommandManager: BrigadierCommandManagerImpl = BrigadierCommandManagerImpl(botInstances)

    /**
     * 获取所有的Bot实例数量
     */
    public val botInstanceCount: Int get() = botManager.botInstances.size

    /**
     * 全局作用域的指令拦截器, 只能有一个拦截器
     */
    public lateinit var interceptor: IExecutionInterceptor<BaseCommand, GroupMessage, PrivateMessage>

    /**
     * 判断拦截器是否已经初始化
     */
    internal val isInterceptorInitialized get() = ::interceptor.isInitialized

    /**
     * 创建一个Websocket客户端连接到OneBot实现
     * 返回一个创建的Bot实例对象
     */
    @JvmOverloads
    public suspend fun createClient(
        address: String,
        accessToken: String,
        listener: OneBotListener = object : OneBotListener {},
        reconnectInterval: Duration = 3.seconds,
        autoReconnect: Boolean = true,
        messageQueueLimit: Int = 512,
    ): BotInstance {
        val instance =
            BotInstance(
                address, accessToken, listener,
                autoReconnect, messageQueueLimit, 0,
                InstanceType.Client, "/", reconnectInterval
            ).createBot()
        botManager.addBotInstance(instance)
        return instance
    }

    /**
     * 监听一个指定的端口来监听Websocket连接
     * 让OneBot实现作为客户端连接到ROB
     * 返回一个创建的Bot实例对象
     */
    @JvmOverloads
    public suspend fun createServer(
        port: Int,
        accessToken: String,
        listener: OneBotListener = object : OneBotListener {},
        path: String = "/",
        autoReconnect: Boolean = true,
        messageQueueLimit: Int = 512,
    ): BotInstance {
        // 这里的 127.0.0.1并没有任何作用, 仅仅是为了当作占位符使用
        // 实际上 Websocket 服务器监听的是 `::` 包括了ipv4 和 ipv6
        val instance = BotInstance(
            "127.0.0.1", accessToken, listener,
            autoReconnect, messageQueueLimit, port,
            InstanceType.Server, path, 0.seconds
        ).createBot()
        botManager.addBotInstance(instance)
        return instance
    }

    override fun toString(): String {
        return "ROneBotFactory"
    }

    override var totalCommandExecutionTimes: Int = 0
    override var privateCommandExecutionTimes: Int = 0
    override var groupCommandExecutionTimes: Int = 0
}