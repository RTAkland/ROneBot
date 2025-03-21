/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/4
 */

package cn.rtast.rob.util

import cn.rtast.rob.IBotInstance
import cn.rtast.rob.onebot.OneBotAction


public class BotManager : IBotManager<List<IBotInstance>, IBotInstance, OneBotAction> {

    internal val botInstances = mutableMapOf<IBotInstance, Boolean>()

    override suspend operator fun get(id: IBotManager.ID): IBotInstance? {
        botInstances.forEach {
            if (it.key.action.getLoginInfo().userId == id.id.toLong()) {
                return it.key
            }
        }
        return null
    }

    override suspend fun addBotInstance(botInstance: IBotInstance) {
        botInstances[botInstance] = true
    }

    override suspend fun removeBotInstance(botInstance: IBotInstance) {
        botInstances.remove(botInstance)
    }

    override suspend fun disableBotInstance(botInstance: IBotInstance) {
        botInstances[botInstance] = false
    }

    override suspend fun enableBotInstance(botInstance: IBotInstance) {
        botInstances[botInstance] = true
    }

    override suspend fun disableAllBots() {
        botInstances.keys.forEach { botInstances[it] = false }
    }

    override suspend fun enableAllBots() {
        botInstances.keys.forEach { botInstances[it] = true }
    }

    override suspend fun getBotInstanceStatus(botInstance: IBotInstance): Boolean {
        return botInstances[botInstance] == true
    }

    override suspend fun getBotInstanceByAction(action: OneBotAction): IBotInstance {
        return action.botInstance
    }

    override fun allBots(): List<IBotInstance> = botInstances.keys.toList()
}
