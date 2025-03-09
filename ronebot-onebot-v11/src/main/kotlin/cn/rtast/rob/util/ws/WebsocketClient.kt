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
import kotlinx.coroutines.launch
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import java.net.URI
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.time.Duration

internal class WebsocketClient(
    address: String,
    accessToken: String,
    private val listener: OneBotListener,
    private val autoReconnect: Boolean,
    private val botInstance: BotInstance,
    private val reconnectInterval: Long,
    private val executeDuration: Duration
) : WebSocketClient(URI(address), mapOf("Authorization" to "Bearer $accessToken")) {

    private val logger = Logger.getLogger()
    private var isConnected = false
    private val scheduler = Executors.newScheduledThreadPool(1)
    private lateinit var messageHandler: MessageHandler
    private lateinit var action: OneBotAction

    fun createAction(): OneBotAction {
        this.action = OneBotAction(botInstance, InstanceType.Client)
        this.messageHandler = MessageHandler(botInstance, this.action)
        this.action.setHandler(this.messageHandler)
        return this.action
    }

    override fun onOpen(handshakedata: ServerHandshake) {
        this.isConnected = true
        coroutineScope.launch {
            messageHandler.onOpen(listener, this@WebsocketClient)
        }
    }

    /**
     * 每次接收到消息时都会向channel中发送数据等待消费
     */
    override fun onMessage(message: String) {
        processIncomingMessage(botInstance, listener, message, executeDuration, messageHandler)
    }

    override fun onClose(code: Int, reason: String, remote: Boolean) {
        this.isConnected = false
        if (autoReconnect) startReconnect()
        coroutineScope.launch {
            messageHandler.onClose(listener, code, reason, remote, this@WebsocketClient)
        }
    }

    override fun onError(ex: Exception) {
        coroutineScope.launch {
            messageHandler.onError(listener, ex)
        }
    }

    private fun startReconnect() {
        scheduler.schedule({
            try {
                logger.info("Reconnecting...")
                reconnect()
            } catch (_: InterruptedException) {
                Thread.currentThread().interrupt()
            }
        }, reconnectInterval, TimeUnit.MILLISECONDS)
    }
}