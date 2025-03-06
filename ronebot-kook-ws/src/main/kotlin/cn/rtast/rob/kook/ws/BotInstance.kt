/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/7
 */

package cn.rtast.rob.kook.ws

import cn.rtast.rob.BaseBotInstance
import cn.rtast.rob.event.listener.AbstractListener

public class BotInstance : BaseBotInstance {
    override val isActionInitialized: Boolean = true
    override val listeners: AbstractListener = object : AbstractListener(this) {}

    override suspend fun createBot(): BotInstance {
        TODO("Not yet implemented")
    }

    override suspend fun disposeBot() {
        TODO("Not yet implemented")
    }
}