/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/11
 */

@file:Suppress("unused")

package cn.rtast.rob.qqbot

import cn.rtast.rob.BotFactory
import cn.rtast.rob.qqbot.qbot.QQBotListener
import cn.rtast.rob.scheduler.GlobalCoroutineScheduler

object QBotFactory : BotFactory {

    internal val botInstances = mutableListOf<BotInstance>()

    /**
     * 全局作用域的任务调度器
     */
    val globalScheduler = GlobalCoroutineScheduler(botInstances)

    suspend fun createServer(port: Int, appId: String, clientSecret: String, listener: QQBotListener): BotInstance {
        val instance = BotInstance(port, appId, clientSecret, listener).apply { createBot() }
        botInstances.add(instance)
        return instance
    }
}