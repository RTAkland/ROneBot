/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/8
 */


package cn.rtast.rob.satori

import cn.rtast.rob.common.BotFactory
import cn.rtast.rob.satori.util.SatoriListener

object RSatoriFactory : BotFactory {

    val botInstances = mutableListOf<BotInstance>()

    suspend fun createClient(address: String, token: String, listener: SatoriListener): BotInstance {
        return BotInstance("$address/v1/events", listener, token).also { botInstances.add(it) }.also { it.createBot() }
    }
}