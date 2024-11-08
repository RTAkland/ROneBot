/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/27
 */


package cn.rtast.rob.kritor

import cn.rtast.rob.BotFactory

object RKritorFactory : BotFactory {

    val botInstances = mutableListOf<BotInstance>()

    suspend fun createClient(host: String, port: Int, account: String, ticket: String): BotInstance {
        val instance = BotInstance(host, port, account, ticket).createBot()
        botInstances.add(instance)
        return instance
    }
}