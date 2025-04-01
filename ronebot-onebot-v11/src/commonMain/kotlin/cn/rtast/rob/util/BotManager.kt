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
import kotlin.jvm.JvmSynthetic


public class BotManager : IBotManager<List<BotInstance>, BotInstance, OneBotAction> {

    @JvmSynthetic
    internal val botInstances = mutableMapOf<BotInstance, Boolean>()

    @JvmBlocking(suffix = "JvmBlocking")
    @JvmAsync(suffix = "JvmAsync")
    override suspend operator fun get(id: IBotManager.ID): BotInstance? {
        botInstances.forEach {
            if (it.key.action.getLoginInfo().userId == id.id.toLong()) {
                return it.key
            }
        }
        return null
    }

    @JvmBlocking(suffix = "JvmBlocking")
    @JvmAsync(suffix = "JvmAsync")
    override suspend fun addBotInstance(botInstance: BotInstance) {
        botInstances[botInstance] = true
    }

    @JvmBlocking(suffix = "JvmBlocking")
    @JvmAsync(suffix = "JvmAsync")
    override suspend fun removeBotInstance(botInstance: BotInstance) {
        botInstances.remove(botInstance)
    }

    @JvmBlocking(suffix = "JvmBlocking")
    @JvmAsync(suffix = "JvmAsync")
    override suspend fun disableBotInstance(botInstance: BotInstance) {
        botInstances[botInstance] = false
    }

    @JvmBlocking(suffix = "JvmBlocking")
    @JvmAsync(suffix = "JvmAsync")
    override suspend fun enableBotInstance(botInstance: BotInstance) {
        botInstances[botInstance] = true
    }

    @JvmBlocking(suffix = "JvmBlocking")
    @JvmAsync(suffix = "JvmAsync")
    override suspend fun disableAllBots() {
        botInstances.keys.forEach { botInstances[it] = false }
    }

    @JvmBlocking(suffix = "JvmBlocking")
    @JvmAsync(suffix = "JvmAsync")
    override suspend fun enableAllBots() {
        botInstances.keys.forEach { botInstances[it] = true }
    }

    @JvmBlocking(suffix = "JvmBlocking")
    @JvmAsync(suffix = "JvmAsync")
    override suspend fun getBotInstanceStatus(botInstance: BotInstance): Boolean {
        return botInstances[botInstance] == true
    }

    @JvmBlocking(suffix = "JvmBlocking")
    @JvmAsync(suffix = "JvmAsync")
    override suspend fun getBotInstanceByAction(action: OneBotAction): BotInstance {
        return action.botInstance
    }

    override fun allBots(): List<BotInstance> = botInstances.keys.toList()
}
