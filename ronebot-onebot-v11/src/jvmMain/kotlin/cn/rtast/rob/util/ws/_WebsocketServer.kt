/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/23
 */

@file:Suppress("ClassName")
@file:OptIn(InternalROBApi::class)

package cn.rtast.rob.util.ws

import cn.rtast.rob.BotInstance
import cn.rtast.rob.annotations.InternalROBApi
import cn.rtast.rob.enums.internal.InstanceType
import cn.rtast.rob.logger
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
            logger.warn("Websocket client's access token is not correct, disconnecting...")
            conn.close(4003, "Forbidden: Invalid or missing Authorization token")
        } else {
            // 如果设置监听的路径为`/`则表示监听所有的路径, 如果设置了其他路径
            // 表示只监听设置的路径, 连接到这个路径之外的路径则会直接关闭连接
            val clientPath = handshake.resourceDescriptor ?: "/"
            if (path == "/" || clientPath == if (path.startsWith("/")) path else "/$path") {
                logger.info("Websocket client successfully authed! (${conn.remoteSocketAddress.address})")
                coroutineScope.launch {
                    botInstance.messageHandler.onOpen(listener, conn.remoteSocketAddress.address.toString())
                }
            } else {
                logger.info("Websocket client connected to wrong path: $clientPath | (${conn.remoteSocketAddress.address})")
                conn.close(4000, "Connect $path instead of $clientPath")
            }
        }
    }

    override fun onClose(conn: WebSocket, code: Int, reason: String, remote: Boolean) {
        coroutineScope.launch {
            botInstance.messageHandler.onClose(listener, conn.remoteSocketAddress.address.toString())
        }
    }

    override fun onMessage(conn: WebSocket, message: String) {
        processIncomingMessage(botInstance, listener, message, executeDuration, botInstance.messageHandler)
    }

    override fun onError(conn: WebSocket?, ex: Exception) {
        coroutineScope.launch {
            botInstance.messageHandler.onError(listener, ex)
        }
    }

    override fun onStart() {
        botInstance.action = OneBotAction(botInstance, InstanceType.Client)
        coroutineScope.launch {
            botInstance.messageHandler.onStart(listener, port)
        }
    }
}