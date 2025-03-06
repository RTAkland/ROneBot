/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/6
 */

package cn.rtast.rob.kook.webhook

import cn.rtast.rob.BotFactory
import cn.rtast.rob.kook.webhook.bot.BotManager
import cn.rtast.rob.kook.webhook.kook.KookListener

public object KookBotFactory : BotFactory {
    override var totalCommandExecutionTimes: Int = 0
    override var privateCommandExecutionTimes: Int = 0
    override var groupCommandExecutionTimes: Int = 0

    public val botManager: BotManager = BotManager()

    public suspend fun createKookBot(
        port: Int,
        token: String,
        verifyToken: String,
        encryptKey: String? = null,
        listener: KookListener = object : KookListener {},
    ): BotInstance {
        val botInstance = BotInstance(port, token, verifyToken, encryptKey, listener).apply { createBot() }
        botManager.addBotInstance(botInstance)
        return botInstance
    }
}