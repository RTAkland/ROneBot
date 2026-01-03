/*
 * Copyright © 2026 RTAkland
 * Date: 2026/1/4 01:04
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.util.ws

import cn.rtast.cfworker.client.Url
import cn.rtast.cfworker.websocket.WebsocketEventHandler
import cn.rtast.cfworker.websocket.readText
import cn.rtast.cfworker.websocket.webSocket
import cn.rtast.rob.BotInstance
import cn.rtast.rob.enums.internal.InstanceType
import cn.rtast.rob.onebot.OneBotAction
import cn.rtast.rob.onebot.OneBotListener
import cn.rtast.rob.workerApplication
import kotlin.time.Duration

public actual class WebsocketSession actual constructor() {
    private lateinit var handler: WebsocketEventHandler
    public actual suspend fun createServer(
        port: Int,
        accessToken: String,
        listener: OneBotListener,
        botInstance: BotInstance,
        path: String,
        executeDuration: Duration,
    ) {
        botInstance.action = OneBotAction(botInstance, InstanceType.Server)
        workerApplication.webSocket(path) {
            handler = this
            onOpen {
                val queryAccessToken = this@webSocket.request.Url.searchParams.get("access_token")
                val headerAccessToken = this@webSocket.request.headers.get("Authorization")
                val isValidToken =
                    queryAccessToken == accessToken || headerAccessToken == "Bearer $accessToken"
                if (!isValidToken) close(4003, "Invalid access token")
                else botInstance.messageHandler.onStart(listener, 0)
            }
            onMessage {
                processIncomingMessage(
                    botInstance,
                    listener,
                    it.readText(),
                    executeDuration,
                    botInstance.messageHandler
                )
            }
            onClose {
                botInstance.messageHandler.onClose(listener)
            }
        }
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
        handler.clients.forEach { it.send(text) }
    }
}