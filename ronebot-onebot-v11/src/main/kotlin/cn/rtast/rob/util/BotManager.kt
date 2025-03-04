/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/4
 */

package cn.rtast.rob.util

import cn.rtast.rob.BotInstance
import cn.rtast.rob.onebot.OneBotAction


class BotManager : IBotManager<List<BotInstance>, BotInstance, OneBotAction> {

    internal val botInstances = mutableMapOf<BotInstance, Boolean>()

    override val logger = Logger.getLogger()

    override suspend operator fun get(id: IBotManager.ID): BotInstance? {
        botInstances.forEach {
            if (it.key.action.getLoginInfo().userId == id.id.toLong()) {
                return it.key
            }
        }
        return null
    }

    override suspend fun addBotInstance(botInstance: BotInstance) {
        botInstances[botInstance] = true
        logger.debug("已添加{}", botInstances)
    }

    override suspend fun removeBotInstance(botInstance: BotInstance) {
        botInstances.remove(botInstance)
        logger.debug("已移除{}", botInstance)
    }

    override suspend fun disableBotInstance(botInstance: BotInstance) {
        botInstances[botInstance] = false
        logger.debug("已禁用{}", botInstance)
    }

    override suspend fun enableBotInstance(botInstance: BotInstance) {
        botInstances[botInstance] = true
        logger.debug("已启用{}", botInstances)
    }

    override suspend fun disableAllBots() {
        botInstances.keys.forEach { botInstances[it] = false }
        logger.debug("所有Bot实例已被禁用")
    }

    override suspend fun enableAllBots() {
        botInstances.keys.forEach { botInstances[it] = true }
        logger.debug("所有Bot实例已被启用")
    }

    override suspend fun getBotInstanceStatus(botInstance: BotInstance): Boolean {
        return botInstances[botInstance] == true
    }

    override suspend fun getBotInstanceByAction(action: OneBotAction): BotInstance {
        return action.botInstance
    }

    override fun allBots(): List<BotInstance> = botInstances.keys.toList()
}
