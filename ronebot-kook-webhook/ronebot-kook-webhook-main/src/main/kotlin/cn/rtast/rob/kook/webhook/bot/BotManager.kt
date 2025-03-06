/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/7
 */

package cn.rtast.rob.kook.webhook.bot

import cn.rtast.rob.kook.webhook.BotInstance
import cn.rtast.rob.kook.webhook.kook.KookAction
import cn.rtast.rob.util.IBotManager
import cn.rtast.rob.util.Logger

public class BotManager : IBotManager<List<BotInstance>, BotInstance, KookAction> {
    override val logger: org.slf4j.Logger = Logger.getLogger()

    internal val botInstances = mutableMapOf<BotInstance, Boolean>()

    override suspend fun get(id: IBotManager.ID): BotInstance? {
        TODO("Not yet implemented")
    }

    override fun allBots(): List<BotInstance> {
        return botInstances.keys.toList()
    }

    override suspend fun addBotInstance(botInstance: BotInstance) {
        botInstances[botInstance] = true
    }

    override suspend fun removeBotInstance(botInstance: BotInstance) {
        botInstances.remove(botInstance)
    }

    override suspend fun disableBotInstance(botInstance: BotInstance) {
        botInstances[botInstance] = false
    }

    override suspend fun enableBotInstance(botInstance: BotInstance) {
        botInstances[botInstance] = true
    }

    override suspend fun disableAllBots() {
        botInstances.keys.forEach { botInstances[it] = false }
    }

    override suspend fun enableAllBots() {
        botInstances.keys.forEach { botInstances[it] = true }
    }

    override suspend fun getBotInstanceStatus(botInstance: BotInstance): Boolean {
        return botInstances[botInstance] == true
    }

    override suspend fun getBotInstanceByAction(action: KookAction): BotInstance {
        return action.botInstance
    }
}