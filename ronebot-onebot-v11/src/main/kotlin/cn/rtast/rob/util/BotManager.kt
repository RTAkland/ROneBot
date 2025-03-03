/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/4
 */

package cn.rtast.rob.util

import cn.rtast.rob.BotInstance


class BotManager : IBotManager<List<BotInstance>, BotInstance> {

    internal val botInstances = mutableListOf<BotInstance>()

    override suspend operator fun get(id: IBotManager.ID): BotInstance? {
        botInstances.forEach {
            if (it.action.getLoginInfo().userId == id.id.toLong()) {
                return it
            }
        }
        return null
    }

    override suspend fun addBotInstance(botInstance: BotInstance) =
        botInstances.add(botInstance)

    override suspend fun removeBotInstance(botInstance: BotInstance) =
        botInstances.remove(botInstance)

    override suspend fun allBots(): List<BotInstance> = botInstances
}
