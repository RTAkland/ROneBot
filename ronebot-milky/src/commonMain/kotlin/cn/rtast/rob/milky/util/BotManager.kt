///*
// * Copyright © 2025 RTAkland & 小满1221
// * Date: 5/19/25, 3:33 AM
// * Open Source Under Apache-2.0 License
// * https://www.apache.org/licenses/LICENSE-2.0
// */
//
//package cn.rtast.rob.milky.util
//
//import cn.rtast.rob.milky.BotInstance
//import cn.rtast.rob.milky.milky.MilkyAction
//import cn.rtast.rob.util.IBotManager
//import love.forte.plugin.suspendtrans.annotation.JvmAsync
//import love.forte.plugin.suspendtrans.annotation.JvmBlocking
//
//public class BotManager : IBotManager<List<BotInstance>, BotInstance, MilkyAction> {
//
//    internal val botInstances = mutableMapOf<BotInstance, Boolean>()
//
//    override suspend operator fun get(id: IBotManager.ID): BotInstance? {
//        botInstances.forEach {
//            if (it.key.action.getLoginInfo().userId == id.id.toLong()) {
//                return it.key
//            }
//        }
//        return null
//    }
//
//    @JvmAsync(suffix = "JvmAsync")
//    @JvmBlocking(suffix = "JvmBlocking")
//    override suspend fun addBotInstance(botInstance: BotInstance) {
//        botInstances[botInstance] = true
//    }
//
//    @JvmAsync(suffix = "JvmAsync")
//    @JvmBlocking(suffix = "JvmBlocking")
//    override suspend fun removeBotInstance(botInstance: BotInstance) {
//        botInstances.remove(botInstance)
//    }
//
//    @JvmAsync(suffix = "JvmAsync")
//    @JvmBlocking(suffix = "JvmBlocking")
//    override suspend fun disableBotInstance(botInstance: BotInstance) {
//        botInstances[botInstance] = false
//    }
//
//    @JvmAsync(suffix = "JvmAsync")
//    @JvmBlocking(suffix = "JvmBlocking")
//    override suspend fun enableBotInstance(botInstance: BotInstance) {
//        botInstances[botInstance] = true
//    }
//
//    @JvmAsync(suffix = "JvmAsync")
//    @JvmBlocking(suffix = "JvmBlocking")
//    override suspend fun disableAllBots() {
//        botInstances.keys.forEach { botInstances[it] = false }
//    }
//
//    @JvmAsync(suffix = "JvmAsync")
//    @JvmBlocking(suffix = "JvmBlocking")
//    override suspend fun enableAllBots() {
//        botInstances.keys.forEach { botInstances[it] = true }
//    }
//
//    @JvmBlocking(suffix = "JvmBlocking")
//    override suspend fun getBotInstanceStatus(botInstance: BotInstance): Boolean {
//        return botInstances[botInstance] == true
//    }
//
//    @JvmBlocking(suffix = "JvmBlocking")
//    override suspend fun getBotInstanceByAction(action: OneBotAction): BotInstance {
//        return action.botInstance
//    }
//
//    override fun allBots(): List<BotInstance> = botInstances.keys.toList()
//}
