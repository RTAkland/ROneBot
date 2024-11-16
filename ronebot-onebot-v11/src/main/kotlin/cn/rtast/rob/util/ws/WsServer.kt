/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/26
 */


package cn.rtast.rob.util.ws

import cn.rtast.rob.BotInstance
import cn.rtast.rob.enums.internal.InstanceType
import cn.rtast.rob.onebot.OneBotAction
import cn.rtast.rob.onebot.OneBotListener
import cn.rtast.rob.util.Logger
import cn.rtast.rob.util.MessageHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import org.java_websocket.WebSocket
import org.java_websocket.handshake.ClientHandshake
import org.java_websocket.server.WebSocketServer
import java.net.InetSocketAddress

internal class WsServer(
    private val port: Int,
    private val accessToken: String,
    private val listener: OneBotListener,
    messageQueueLimit: Int,
    private val botInstance: BotInstance,
    private val path: String,
) : WebSocketServer(InetSocketAddress(port)) {

    private val logger = Logger.getLogger()
    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    private val channelCoroutineScope = CoroutineScope(Dispatchers.IO)
    private val messageChannel = Channel<String>(messageQueueLimit)
    private lateinit var messageHandler: MessageHandler
    private lateinit var action: OneBotAction

    init {
        this.processMessages()
    }

    fun createAction(): OneBotAction {
        this.action = OneBotAction(botInstance, InstanceType.Server)
        this.messageHandler = MessageHandler(botInstance, this.action)
        this.action.setHandler(this.messageHandler)
        return this.action
    }

    override fun onOpen(conn: WebSocket, handshake: ClientHandshake) {
        val allHeaderKeys = mutableListOf<String>()
        handshake.iterateHttpFields().forEach { allHeaderKeys.add(it) }
        if (!allHeaderKeys.contains("Authorization")) {
            logger.warn("Websocket client's access token is not correct, disconnecting...")
            conn.close(4003, "Forbidden: Invalid or missing Authorization token")
        }
        val value = handshake.getFieldValue("Authorization")
        if (value != "Bearer $accessToken") {
            logger.warn("Websocket client's access token is not correct, disconnecting...")
            conn.close(4003, "Forbidden: Invalid or missing Authorization token")
        } else {
            // 如果设置监听的路径为`/`则表示监听所有的路径, 如果设置了其他路径
            // 表示只监听设置的路径, 连接到这个路径之外的路径则会直接关闭连接
            val clientPath = handshake.resourceDescriptor ?: "/"
            if (path == "/" || clientPath == if (path.startsWith("/")) path else "/$path") {
                logger.info("Websocket client successfully authed! (${conn.remoteSocketAddress.address})")
                coroutineScope.launch {
                    messageHandler.onOpen(listener, conn)
                }
            } else {
                logger.warn("Websocket client connected to wrong path: $clientPath | (${conn.remoteSocketAddress.address})")
                conn.close(4000, "Connect $path instead of $clientPath")
            }
        }
    }

    override fun onClose(conn: WebSocket, code: Int, reason: String, remote: Boolean) {
        coroutineScope.launch {
            messageHandler.onClose(listener, code, reason, remote, conn)
        }
    }

    override fun onMessage(conn: WebSocket, message: String) {
        channelCoroutineScope.launch {
            messageChannel.send(message)
        }
    }

    override fun onError(conn: WebSocket?, ex: Exception) {
        coroutineScope.launch {
            messageHandler.onError(listener, ex)
        }
    }

    override fun onStart() {
        coroutineScope.launch {
            messageHandler.onStart(listener, port)
        }
    }

    private fun processMessages() {
        coroutineScope.launch {
            for (message in messageChannel) {
                coroutineScope.launch {
                    messageHandler.onMessage(listener, message)
                }
            }
        }
    }
}