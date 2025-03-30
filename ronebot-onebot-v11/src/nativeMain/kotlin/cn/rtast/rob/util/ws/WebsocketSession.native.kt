/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/23
 */

@file:OptIn(InternalROBApi::class)

package cn.rtast.rob.util.ws

import cn.rtast.rob.BotInstance
import cn.rtast.rob.annotations.InternalROBApi
import cn.rtast.rob.commonCoroutineScope
import cn.rtast.rob.enums.internal.InstanceType
import cn.rtast.rob.exceptions.WebsocketProtocolNotSupportedException
import cn.rtast.rob.logger
import cn.rtast.rob.onebot.OneBotAction
import cn.rtast.rob.onebot.OneBotListener
import cn.rtast.rob.util.MessageHandler
import io.ktor.client.*
import io.ktor.client.plugins.websocket.*
import io.ktor.client.request.*
import io.ktor.server.application.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration
import io.ktor.client.engine.cio.CIO as ClientCIO
import io.ktor.client.plugins.websocket.WebSockets as ClientWebsocket
import io.ktor.server.cio.CIO as ServerCIO
import io.ktor.server.websocket.WebSockets as ServerWebsocket

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
    private lateinit var client: HttpClient
    private lateinit var clientSession: DefaultClientWebSocketSession
    private lateinit var serverSession: DefaultWebSocketServerSession
    private lateinit var server: EmbeddedServer<CIOApplicationEngine, CIOApplicationEngine.Configuration>

    private suspend fun connectClient(
        address: String,
        accessToken: String,
        botInstance: BotInstance,
        listener: OneBotListener,
        executeDuration: Duration
    ) {
        client.webSocket(address, request = {
            header("Authorization", "Bearer $accessToken")
        }) {
            logger.info("Websocket client connected to server $address")
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

    public actual suspend fun createServer(
        port: Int,
        accessToken: String,
        listener: OneBotListener,
        botInstance: BotInstance,
        path: String,
        executeDuration: Duration
    ) {
        botInstance.action = OneBotAction(botInstance, InstanceType.Client)
        commonCoroutineScope.launch {
            val server = embeddedServer(ServerCIO, port = port) {
                install(ServerWebsocket)
                routing {
                    webSocket("/") {
                        serverSession = this
                        botInstance.messageHandler.onStart(listener, port)
                        processingMessage(
                            call.request.uri,
                            botInstance,
                            listener,
                            executeDuration,
                            botInstance.messageHandler
                        )
                    }
                }
            }
            this@WebsocketSession.server = server
            logger.info("Websocket server started on $port")
            server.start(wait = true)
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
        if (address.startsWith("wss://")) throw WebsocketProtocolNotSupportedException("当前平台仅支持ws协议不支持TLS websocket协议")
        client = HttpClient(ClientCIO) {
            install(ClientWebsocket)
        }
        commonCoroutineScope.launch {
            botInstance.action = OneBotAction(botInstance, InstanceType.Client)
            while (true) {
                try {
                    connectClient(address, accessToken, botInstance, listener, executeDuration)
                } catch (e: Exception) {
                    e.printStackTrace()
                    delay(5000L)
                    logger.info("Websocket connection closed... Reconnecting...")
                    connectClient(address, accessToken, botInstance, listener, executeDuration)
                }
            }
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