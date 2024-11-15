/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/11
 */


package cn.rtast.rob.qqbot

import cn.rtast.rob.BotFactory
import cn.rtast.rob.qqbot.qbot.QQBotListener

object QBotFactory : BotFactory {

    internal val botInstances = mutableListOf<BotInstance>()

    suspend fun createServer(port: Int, appId: String, clientSecret: String, listener: QQBotListener): BotInstance {
        val instance = BotInstance(port, appId, clientSecret, listener).apply { createBot() }
        botInstances.add(instance)
        return instance
    }
}