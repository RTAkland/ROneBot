/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/23
 */

package cn.rtast.rob.util.ws

import cn.rtast.rob.BotInstance
import cn.rtast.rob.enums.internal.InstanceType
import cn.rtast.rob.exceptions.PlatformNotSupportedException
import cn.rtast.rob.exceptions.WebsocketProtocolNotSupportedException
import cn.rtast.rob.onebot.OneBotAction
import cn.rtast.rob.onebot.OneBotListener
import cn.rtast.rob.util.MessageHandler
import io.ktor.client.*
import io.ktor.client.plugins.websocket.*
import io.ktor.client.request.*
import io.ktor.server.application.install
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import io.ktor.server.request.uri
import io.ktor.server.routing.routing
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlin.time.Duration
import io.ktor.client.engine.cio.CIO as ClientCIO
import io.ktor.client.plugins.websocket.WebSockets as ClientWebsocket
import io.ktor.server.cio.CIO as ServerCIO

internal suspend fun DefaultWebSocketSession.processingMessage(
    address: String,
    botInstance: BotInstance,
    listener: OneBotListener,
    executeDuration: Duration,
    messageHandler: MessageHandler
) {
    messageHandler.onOpen(listener, address)
    for (frame in incoming) {
        frame as? Frame.Text ?: continue
        processIncomingMessage(botInstance, listener, frame.readText(), executeDuration, messageHandler)
    }
    messageHandler.onClose(listener, address)
}

public actual class WebsocketSession {
    private lateinit var clientSession: DefaultClientWebSocketSession
    private lateinit var serverSession: DefaultWebSocketServerSession
    private lateinit var server: EmbeddedServer<CIOApplicationEngine, CIOApplicationEngine.Configuration>

    public actual suspend fun createServer(
        port: Int,
        accessToken: String,
        listener: OneBotListener,
        botInstance: BotInstance,
        path: String,
        executeDuration: Duration
    ) {
        botInstance.action = OneBotAction(botInstance, InstanceType.Client)
        val server = embeddedServer(ServerCIO, port = port) {
            install(WebSockets)
            routing {
                webSocket("/") {
                    serverSession = this
                    botInstance.messageHandler.onStart(listener, port)
                    processingMessage(
                        call.request.uri.toString(),
                        botInstance,
                        listener,
                        executeDuration,
                        botInstance.messageHandler
                    )
                }
            }
        }
        this@WebsocketSession.server = server
        server.start(wait = true)
        throw PlatformNotSupportedException("当前平台不支持Websocket服务端, 仅支持Websocket客户端")
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
        if (address.startsWith("wss://")) throw WebsocketProtocolNotSupportedException("当前平台仅支持ws协议不支持TLS websocket协议")
        val client = HttpClient(ClientCIO) {
            install(ClientWebsocket)
        }
        botInstance.action = OneBotAction(botInstance, InstanceType.Client)
        client.webSocket(address, request = {
            header("Authorization", "Bearer $accessToken")
        }) {
            clientSession = this
            processingMessage(
                call.request.url.toString(),
                botInstance,
                listener,
                executeDuration,
                botInstance.messageHandler
            )
        }
    }

    public actual suspend fun sendToServer(text: String) {
        clientSession.send(text)
    }

    public actual suspend fun sendToClient(text: String) {
        serverSession.send(text)
    }

    public actual suspend fun closeClient() {
        clientSession.close()
    }

    public actual suspend fun closeServer() {
        server.stop()
    }
}