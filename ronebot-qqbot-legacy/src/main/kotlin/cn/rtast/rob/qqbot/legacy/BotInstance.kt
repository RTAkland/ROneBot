/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/12
 */


package cn.rtast.rob.qqbot.legacy

import cn.rtast.rob.BaseBotInstance

class BotInstance internal constructor(
    private val appId: String,
    private val clientSecret: String
): BaseBotInstance {
    override val isActionInitialized = true

    override suspend fun createBot(): BotInstance {
        TODO()
    }

    override suspend fun disposeBot() {
    }
}