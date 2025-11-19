/*
 * Copyright © 2025 RTAkland
 * Date: 11/19/25, 7:00 PM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.util.ws

import cn.rtast.rob.BotInstance
import cn.rtast.rob.enums.internal.InstanceType
import cn.rtast.rob.onebot.OneBotAction
import cn.rtast.rob.onebot.OneBotListener
import kotlin.time.Duration

/**
 * 没用
 */
public actual class WebsocketSession actual constructor() {
    public actual suspend fun createServer(
        port: Int,
        accessToken: String,
        listener: OneBotListener,
        botInstance: BotInstance,
        path: String,
        executeDuration: Duration,
    ) {
        botInstance.action = OneBotAction(botInstance, InstanceType.Server)
    }

    public actual suspend fun createClient(
        address: String,
        accessToken: String,
        listener: OneBotListener,
        autoReconnect: Boolean,
        botInstance: BotInstance,
        reconnectInterval: Long,
        executeDuration: Duration,
    ) {
    }

    public actual suspend fun closeClient() {
    }

    public actual suspend fun closeServer() {
    }

    public actual suspend fun sendToServer(text: String) {
    }

    public actual suspend fun sendToClient(text: String) {
    }
}