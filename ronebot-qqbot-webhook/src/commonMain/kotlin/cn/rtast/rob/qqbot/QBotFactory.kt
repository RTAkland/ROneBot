/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/11
 */

@file:Suppress("unused")

package cn.rtast.rob.qqbot

import cn.rtast.klogging.LogLevel
import cn.rtast.rob.BotFactory
import cn.rtast.rob.interceptor.ICommandInterceptor
import cn.rtast.rob.qqbot.command.BaseCommand
import cn.rtast.rob.qqbot.command.CommandManagerImpl
import cn.rtast.rob.qqbot.entity.inbound.C2CMessageCreateEvent
import cn.rtast.rob.qqbot.entity.inbound.GroupAtMessageCreateEvent
import cn.rtast.rob.qqbot.qbot.QQBotListener
import cn.rtast.rob.scheduler.GlobalCoroutineScheduler
import love.forte.plugin.suspendtrans.annotation.JvmAsync
import love.forte.plugin.suspendtrans.annotation.JvmBlocking
import kotlin.jvm.JvmOverloads
import kotlin.jvm.JvmStatic

public class QBotFactory {
    public companion object : BotFactory {
        /**
         * 命令管理器
         */
        @JvmStatic
        public val commandManager: CommandManagerImpl = CommandManagerImpl()

        /**
         * 所有Bot实例
         */
        @JvmStatic
        internal val botInstances = mutableListOf<BotInstance>()

        /**
         * 全局作用域的指令拦截器, 只能有一个拦截器
         */
        @JvmStatic
        public lateinit var interceptor: ICommandInterceptor<BaseCommand, GroupAtMessageCreateEvent, C2CMessageCreateEvent>

        /**
         * 判断拦截器是否已经初始化
         */
        @JvmStatic
        internal val isInterceptorInitialized get() = ::interceptor.isInitialized

        /**
         * 全局作用域的任务调度器
         */
        @JvmStatic
        public val globalScheduler: GlobalCoroutineScheduler<BotInstance> = GlobalCoroutineScheduler(botInstances)

        /**
         * 创建HTTP服务器 / 只能在QQBot webhook中使用这种方式
         */
        @JvmOverloads
        @JvmStatic
        @JvmAsync(suffix = "JvmAsync")
        @JvmBlocking(suffix = "JvmBlocking")
        public suspend fun createServer(
            port: Int,
            appId: String,
            clientSecret: String,
            listener: QQBotListener,
            logLevel: LogLevel = LogLevel.INFO
        ): BotInstance {
            val instance = BotInstance(
                port, appId,
                clientSecret,
                listener, logLevel
            ).apply { createBot() }
            botInstances.add(instance)
            return instance
        }

        @JvmStatic
        override var totalCommandExecutionTimes: Int = 0

        @JvmStatic
        override var privateCommandExecutionTimes: Int = 0

        @JvmStatic
        override var groupCommandExecutionTimes: Int = 0
    }

    override fun toString(): String {
        return "QBotFactory"
    }
}