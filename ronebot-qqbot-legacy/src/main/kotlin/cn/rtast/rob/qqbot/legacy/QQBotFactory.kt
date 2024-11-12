/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/12
 */


package cn.rtast.rob.qqbot.legacy

import cn.rtast.rob.BotFactory

object QQBotFactory : BotFactory {

    suspend fun createClient(appId: String, clientSecret: String): BotInstance {
        return BotInstance(appId, clientSecret).createBot()
    }
}