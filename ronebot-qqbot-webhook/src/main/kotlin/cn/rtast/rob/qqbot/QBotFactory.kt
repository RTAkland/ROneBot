/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/11
 */

@file:Suppress("unused")

package cn.rtast.rob.qqbot

import cn.rtast.rob.BotFactory
import cn.rtast.rob.interceptor.IExecutionInterceptor
import cn.rtast.rob.qqbot.command.BaseCommand
import cn.rtast.rob.qqbot.command.CommandManagerImpl
import cn.rtast.rob.qqbot.entity.inbound.C2CMessageCreateEvent
import cn.rtast.rob.qqbot.entity.inbound.GroupAtMessageCreateEvent
import cn.rtast.rob.qqbot.qbot.QQBotListener
import cn.rtast.rob.scheduler.GlobalCoroutineScheduler

object QBotFactory : BotFactory {

    val commandManager = CommandManagerImpl()

    internal val botInstances = mutableListOf<BotInstance>()

    /**
     * 全局作用域的指令拦截器, 只能有一个拦截器
     */
    lateinit var interceptor: IExecutionInterceptor<BaseCommand, GroupAtMessageCreateEvent, C2CMessageCreateEvent>

    /**
     * 判断拦截器是否已经初始化
     */
    internal val isInterceptorInitialized get() = ::interceptor.isInitialized

    /**
     * 全局作用域的任务调度器
     */
    val globalScheduler = GlobalCoroutineScheduler(botInstances)

    suspend fun createServer(port: Int, appId: String, clientSecret: String, listener: QQBotListener): BotInstance {
        val instance = BotInstance(port, appId, clientSecret, listener).apply { createBot() }
        botInstances.add(instance)
        return instance
    }

    override var totalCommandExecutionTimes = 0
    override var privateCommandExecutionTimes = 0
    override var groupCommandExecutionTimes = 0
}