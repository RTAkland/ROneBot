/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/8
 */


package cn.rtast.rob.satori

import cn.rtast.rob.BotFactory
import cn.rtast.rob.satori.enums.Platforms
import cn.rtast.rob.satori.satori.SatoriListener

public object RSatoriFactory : BotFactory {

    public val botInstances: MutableList<BotInstance> = mutableListOf<BotInstance>()

    public suspend fun createClient(
        address: String,
        userId: String,
        token: String,
        listener: SatoriListener,
        platform: Platforms
    ): BotInstance {
        return BotInstance(address, listener, userId, token, platform).also { botInstances.add(it) }
            .apply { createBot() }
    }

    override var totalCommandExecutionTimes: Int = 0
    override var privateCommandExecutionTimes: Int = 0
    override var groupCommandExecutionTimes: Int = 0
}