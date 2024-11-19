/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/27
 */

@file:Suppress("unused")

package cn.rtast.rob.kritor

import cn.rtast.rob.BotFactory
import cn.rtast.rob.kritor.kritor.KritorListener
import cn.rtast.rob.scheduler.GlobalCoroutineScheduler

object RKritorFactory : BotFactory {

    val botInstances = mutableListOf<BotInstance>()

    val globalScheduler = GlobalCoroutineScheduler(botInstances)

    suspend fun createClient(
        host: String,
        port: Int,
        account: String,
        ticket: String,
        listener: KritorListener
    ): BotInstance {
        val instance = BotInstance(host, port, account, ticket, listener).createBot()
        botInstances.add(instance)
        return instance
    }
}