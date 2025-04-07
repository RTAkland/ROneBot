/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/4
 */

package cn.rtast.rob.util

import cn.rtast.rob.BotInstance
import cn.rtast.rob.onebot.OneBotAction
import love.forte.plugin.suspendtrans.annotation.JvmAsync
import love.forte.plugin.suspendtrans.annotation.JvmBlocking


public class BotManager : IBotManager<List<BotInstance>, BotInstance, OneBotAction> {

    internal val botInstances = mutableMapOf<BotInstance, Boolean>()

    override suspend operator fun get(id: IBotManager.ID): BotInstance? {
        botInstances.forEach {
            if (it.key.action.getLoginInfo().userId == id.id.toLong()) {
                return it.key
            }
        }
        return null
    }

    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    override suspend fun addBotInstance(botInstance: BotInstance) {
        botInstances[botInstance] = true
    }

    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    override suspend fun removeBotInstance(botInstance: BotInstance) {
        botInstances.remove(botInstance)
    }

    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    override suspend fun disableBotInstance(botInstance: BotInstance) {
        botInstances[botInstance] = false
    }

    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    override suspend fun enableBotInstance(botInstance: BotInstance) {
        botInstances[botInstance] = true
    }

    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    override suspend fun disableAllBots() {
        botInstances.keys.forEach { botInstances[it] = false }
    }

    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    override suspend fun enableAllBots() {
        botInstances.keys.forEach { botInstances[it] = true }
    }

    @JvmBlocking(suffix = "JvmBlocking")
    override suspend fun getBotInstanceStatus(botInstance: BotInstance): Boolean {
        return botInstances[botInstance] == true
    }

    @JvmBlocking(suffix = "JvmBlocking")
    override suspend fun getBotInstanceByAction(action: OneBotAction): BotInstance {
        return action.botInstance
    }

    override fun allBots(): List<BotInstance> = botInstances.keys.toList()
}
