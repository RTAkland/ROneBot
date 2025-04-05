/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/23
 */

/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/23
 */

package cn.rtast.rob.util.ws

import cn.rtast.rob.BotInstance
import cn.rtast.rob.onebot.OneBotListener
import kotlin.time.Duration

public actual class WebsocketSession {

    private lateinit var server: _WebsocketServer
    private lateinit var client: _WebsocketClient

    public actual suspend fun createServer(
        port: Int,
        accessToken: String,
        listener: OneBotListener,
        botInstance: BotInstance,
        path: String,
        executeDuration: Duration
    ) {
        botInstance.action
        server = _WebsocketServer(port, accessToken, listener, botInstance, path, executeDuration).apply {
            start()
        }
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
        client = _WebsocketClient(
            address,
            accessToken,
            listener,
            autoReconnect,
            botInstance,
            reconnectInterval,
            executeDuration
        ).apply {
            connectBlocking()
        }
    }

    public actual suspend fun sendToServer(text: String) {
        client.send(text)
    }

    public actual suspend fun sendToClient(text: String) {
        server.connections.forEach {
            it.send(text)
        }
    }

    public actual suspend fun closeServer() {
        server.stop()
        System.gc()
    }

    public actual suspend fun closeClient() {
        client.close()
        System.gc()
    }
}