/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/23
 */

@file:Suppress("ClassName")
@file:OptIn(InternalROneBotApi::class)

package cn.rtast.rob.util.ws

import cn.rtast.rob.BotInstance
import cn.rtast.rob.annotations.InternalROneBotApi
import cn.rtast.rob.enums.internal.InstanceType
import cn.rtast.rob.onebot.OneBotAction
import cn.rtast.rob.onebot.OneBotListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.java_websocket.WebSocket
import org.java_websocket.handshake.ClientHandshake
import org.java_websocket.server.WebSocketServer
import java.net.InetSocketAddress
import kotlin.time.Duration

internal class _WebsocketServer(
    private val port: Int,
    private val accessToken: String,
    private val listener: OneBotListener,
    private val botInstance: BotInstance,
    private val path: String,
    private val executeDuration: Duration
) : WebSocketServer(InetSocketAddress(port)) {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    override fun onOpen(conn: WebSocket, handshake: ClientHandshake) {
        val allHeaderKeys = mutableListOf<String>()
        handshake.iterateHttpFields().forEach { allHeaderKeys.add(it) }
        val queryAccessToken = handshake.resourceDescriptor
            .split("?").getOrNull(1)?.split("&")
            ?.firstOrNull { it.startsWith("access_token=") }
            ?.split("=")?.getOrNull(1)
        val value = handshake.getFieldValue("Authorization")
        if (queryAccessToken != accessToken && value != "Bearer $accessToken") {
            botInstance.logger.warn("有Websocket客户端连接到服务器, 但是没有提供正确的AccessToken: ${conn.remoteSocketAddress.address}")
            conn.close(4003, "Forbidden: Invalid or missing Authorization token | 不正确的AccessToken或缺少AccessToken")
        } else {
            // 如果设置监听的路径为`/`则表示监听所有的路径, 如果设置了其他路径
            // 表示只监听设置的路径, 连接到这个路径之外的路径则会直接关闭连接
            val clientPath = handshake.resourceDescriptor ?: "/"
            if (path == "/" || clientPath == if (path.startsWith("/")) path else "/$path") {
                botInstance.logger.info("有Websocket客户端成功连接到服务端: (${conn.remoteSocketAddress.address})")
                coroutineScope.launch { botInstance.messageHandler.onOpen(listener) }
            } else {
                botInstance.logger.info("有Websocket客户端成功连接到服务端: $clientPath | (${conn.remoteSocketAddress.address})")
                conn.close(4000, "Connect $path instead of $clientPath")
            }
        }
    }

    override fun onClose(conn: WebSocket, code: Int, reason: String, remote: Boolean) {
        botInstance.logger.warn("Websocket客户端已从服务端断开连接: ${conn.remoteSocketAddress.address}")
        coroutineScope.launch { botInstance.messageHandler.onClose(listener) }
    }

    override fun onMessage(conn: WebSocket, message: String) {
        processIncomingMessage(botInstance, listener, message, executeDuration, botInstance.messageHandler)
    }

    override fun onError(conn: WebSocket, ex: Exception) {
        botInstance.logger.error("Websocket服务器发生错误(${conn.remoteSocketAddress.address}): ${ex.message}", ex)
        coroutineScope.launch { botInstance.messageHandler.onError(listener, ex) }
    }

    override fun onStart() {
        botInstance.action = OneBotAction(botInstance, InstanceType.Client)
        botInstance.logger.info("Websocket服务器已启动, 监听端口: $port")
        coroutineScope.launch { botInstance.messageHandler.onStart(listener, port) }
    }
}