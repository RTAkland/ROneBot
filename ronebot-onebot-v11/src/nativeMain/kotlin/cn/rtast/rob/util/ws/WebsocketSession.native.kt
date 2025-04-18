/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/23
 */

@file:OptIn(InternalROneBotApi::class)

package cn.rtast.rob.util.ws

import cn.rtast.rob.BotInstance
import cn.rtast.rob.annotations.InternalROneBotApi
import cn.rtast.rob.commonCoroutineScope
import cn.rtast.rob.enums.internal.InstanceType
import cn.rtast.rob.onebot.OneBotAction
import cn.rtast.rob.onebot.OneBotListener
import cn.rtast.rob.util.MessageHandler
import io.ktor.client.*
import io.ktor.client.plugins.websocket.*
import io.ktor.client.request.*
import io.ktor.http.*
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
import io.ktor.client.plugins.websocket.WebSockets as ClientWebsocket
import io.ktor.server.cio.CIO as ServerCIO
import io.ktor.server.websocket.WebSockets as ServerWebsocket

internal suspend fun DefaultWebSocketSession.processingMessage(
    botInstance: BotInstance,
    listener: OneBotListener,
    executeDuration: Duration,
    messageHandler: MessageHandler
) {
    messageHandler.onOpen(listener)
    for (frame in incoming) {
        frame as? Frame.Text ?: continue
        processIncomingMessage(botInstance, listener, frame.readText(), executeDuration, messageHandler)
    }
    messageHandler.onClose(listener)
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
            botInstance.logger.info("已连接到Websocket服务器 $address")
            clientSession = this
            processingMessage(
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
                    webSocket(path) {
                        val queryAccessToken = call.request.queryParameters["access_token"]
                        val incomingAuthorizationHeader = call.request.headers[HttpHeaders.Authorization]
                        val isValidToken =
                            queryAccessToken == accessToken || incomingAuthorizationHeader == "Bearer $accessToken"
                        if (!isValidToken) {
                            botInstance.logger.warn("Websocket客户端连接失败, 无效或缺少AccessToken: ${call.request.uri}")
                            close(
                                CloseReason(
                                    4003,
                                    "Forbidden: Invalid or missing Authorization token | 不正确的AccessToken或缺少AccessToken"
                                )
                            )
                        } else {
                            botInstance.logger.info("Websocket客户端已连接到服务器: ${this.call.request.uri}")
                            serverSession = this
                            botInstance.messageHandler.onStart(listener, port)
                            processingMessage(
                                botInstance,
                                listener,
                                executeDuration,
                                botInstance.messageHandler
                            )
                        }

                        botInstance.logger.warn("Websocket客户端已断开连接: ${this.call.request.uri}")
                    }
                }
            }
            this@WebsocketSession.server = server
            botInstance.logger.info("Websocket服务器已运行在: $port")
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
        client = HttpClient(getClientEngine()) { install(ClientWebsocket) }
        commonCoroutineScope.launch {
            botInstance.action = OneBotAction(botInstance, InstanceType.Client)
            while (true) {
                try {
                    connectClient(address, accessToken, botInstance, listener, executeDuration)
                    botInstance.logger.info("正在重连至服务器... $address")
                } catch (e: Exception) {
                    e.printStackTrace()
                    delay(5000L)
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