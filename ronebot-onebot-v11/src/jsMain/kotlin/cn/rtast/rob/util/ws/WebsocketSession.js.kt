/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 2025/4/11 13:30
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.util.ws

import cn.rtast.rob.BotInstance
import cn.rtast.rob.onebot.OneBotListener
import kotlin.time.Duration

public actual class WebsocketSession {

    public actual suspend fun createServer(
        port: Int,
        accessToken: String,
        listener: OneBotListener,
        botInstance: BotInstance,
        path: String,
        executeDuration: Duration
    ) {
        TODO()
    }

    public actual suspend fun createClient(
        address: String,
        accessToken: String,
        listener: OneBotListener,
        autoReconnect: Boolean,
        botInstance: BotInstance,
        reconnectInterval: Long,
        executeDuration: Duration
    ) {
        TODO()
    }

    public actual suspend fun sendToServer(text: String) {
        TODO()
    }

    public actual suspend fun sendToClient(text: String) {
        TODO()
    }

    public actual suspend fun closeServer() {
        TODO()
    }

    public actual suspend fun closeClient() {
        TODO()
    }
}