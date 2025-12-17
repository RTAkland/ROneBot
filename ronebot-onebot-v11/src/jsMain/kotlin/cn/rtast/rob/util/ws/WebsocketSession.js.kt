/*
 * Copyright © 2025 RTAkland
 * Date: 2025/12/16 17:07
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.util.ws

public actual class WebsocketSession actual constructor() {
    public actual suspend fun createServer(
        port: Int,
        accessToken: String,
        listener: cn.rtast.rob.onebot.OneBotListener,
        botInstance: cn.rtast.rob.BotInstance,
        path: String,
        executeDuration: kotlin.time.Duration,
    ) {
    }

    public actual suspend fun sendToClient(text: String) {
    }

    public actual suspend fun createClient(
        address: String,
        accessToken: String,
        listener: cn.rtast.rob.onebot.OneBotListener,
        autoReconnect: Boolean,
        botInstance: cn.rtast.rob.BotInstance,
        reconnectInterval: Long,
        executeDuration: kotlin.time.Duration,
    ) {
        TODO()
    }

    public actual suspend fun closeClient() {
        TODO()
    }

    public actual suspend fun closeServer() {
        TODO()
    }

    public actual suspend fun sendToServer(text: String) {
        TODO()
    }
}