/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/26
 */

@file:Suppress("unused")

package cn.rtast.rob

import cn.rtast.klogging.LogLevel
import cn.rtast.rob.command.CommandManagerImpl
import cn.rtast.rob.enums.internal.InstanceType
import cn.rtast.rob.interceptor.CommandInterceptor
import cn.rtast.rob.interceptor.defaultInterceptor
import cn.rtast.rob.onebot.OneBotListener
import cn.rtast.rob.scheduler.GlobalCoroutineScheduler
import cn.rtast.rob.session.SessionManager
import cn.rtast.rob.util.BotManager
import love.forte.plugin.suspendtrans.annotation.JvmBlocking
import kotlin.jvm.JvmName
import kotlin.jvm.JvmOverloads
import kotlin.jvm.JvmStatic
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds


/**
 * 创建客户端的入口点
 */
public class OneBotFactory {
    public companion object : BotFactory {
        /**
         * 所有命令执行次数
         */
        @JvmStatic
        override var totalCommandExecutionTimes: Int = 0

        /**
         * 私聊命令执行次数
         */
        @JvmStatic
        override var privateCommandExecutionTimes: Int = 0

        /**
         * 群聊命令执行次数
         */
        @JvmStatic
        override var groupCommandExecutionTimes: Int = 0

        /**
         * 所有Bot实例的管理器
         */
        public val botManager: BotManager = BotManager()

        /**
         * 全局作用域的任务调度器但是这个调度器执行任务
         * 并不会判断Bot实例是否已经初始化完成
         * 所以你需要自行[forEach]然后判断每个Bot实例
         * 是否已经初始化完成, 否则会抛出错误
         */
        public val globalScheduler: GlobalCoroutineScheduler<BotInstance> =
            GlobalCoroutineScheduler(botManager.allBots())

        /**
         * 在全局作用域的命令管理器
         */
        public val commandManager: CommandManagerImpl = CommandManagerImpl()

        /**
         * 通过继承来实现命令的方式的会话管理器
         */
        public val sessionManager: SessionManager = SessionManager()

        /**
         * 全局作用域的指令拦截器
         */
        @get:JvmName("#$")
        internal var interceptor: CommandInterceptor = defaultInterceptor

        /**
         * 设置BaseCommand的拦截器
         */
        public fun setInterceptor(interceptor: CommandInterceptor) {
            this.interceptor = interceptor
        }

        /**
         * 将BaseCommand的拦截器设置为初始值
         */
        public fun clearInterceptor() {
            this.interceptor = defaultInterceptor
        }

        /**
         * 获取BaseCommand的拦截器
         */
        public fun getInterceptor(): CommandInterceptor = interceptor

        /**
         * 创建一个Websocket客户端连接到OneBot实现
         * 返回一个创建的Bot实例对象
         */
        @JvmOverloads
        @JvmBlocking(suffix = "JvmBlocking")
        public suspend fun createClient(
            address: String,
            accessToken: String,
            listener: OneBotListener = object : OneBotListener {},
            reconnectInterval: Duration = 3.seconds,
            autoReconnect: Boolean = true,
            messageExecuteDuration: Duration = 0.seconds,
            logLevel: LogLevel = LogLevel.INFO
        ): BotInstance {
            val instance =
                BotInstance(
                    address, accessToken, listener,
                    autoReconnect, 0, InstanceType.Client,
                    "/", reconnectInterval, messageExecuteDuration,
                    logLevel
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
        @JvmBlocking(suffix = "JvmBlocking")
        public suspend fun createServer(
            port: Int,
            accessToken: String,
            listener: OneBotListener = object : OneBotListener {},
            path: String = "/",
            messageExecuteDuration: Duration = 0.seconds,
            logLevel: LogLevel = LogLevel.INFO
        ): BotInstance {
            // 这里的 127.0.0.1并没有任何作用, 仅仅是为了当作占位符使用
            // 实际上 Websocket 服务器监听的是 `::` 包括了ipv4 和 ipv6
            val instance = BotInstance(
                "127.0.0.1", accessToken, listener,
                true, port, InstanceType.Server,
                path, 0.seconds, messageExecuteDuration,
                logLevel
            ).createBot()
            botManager.addBotInstance(instance)
            return instance
        }
    }

    override fun toString(): String {
        return "OneBotFactory"
    }
}