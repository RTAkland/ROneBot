/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/8
 */


package cn.rtast.rob.satori

import cn.rtast.rob.BotFactory
import cn.rtast.rob.satori.enums.Platforms
import cn.rtast.rob.satori.satori.SatoriListener

object RSatoriFactory : BotFactory {

    val botInstances = mutableListOf<BotInstance>()

    suspend fun createClient(
        address: String,
        userId: String,
        token: String,
        listener: SatoriListener,
        platform: Platforms
    ): BotInstance {
        return BotInstance(address, listener, userId, token, platform).also { botInstances.add(it) }
            .apply { createBot() }
    }

    override var totalCommandExecutionTimes = 0
    override var privateCommandExecutionTimes = 0
    override var groupCommandExecutionTimes = 0
}