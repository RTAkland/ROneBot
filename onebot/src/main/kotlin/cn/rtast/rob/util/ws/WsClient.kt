/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/26
 */


package cn.rtast.rob.util.ws

import cn.rtast.rob.BotInstance
import cn.rtast.rob.enums.internal.InstanceType
import cn.rtast.rob.util.Logger
import cn.rtast.rob.util.MessageHandler
import cn.rtast.rob.util.ob.OneBotAction
import cn.rtast.rob.util.ob.OneBotListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import java.net.URI
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

internal class WsClient(
    address: String,
    accessToken: String,
    private val listener: OneBotListener,
    private val autoReconnect: Boolean,
    messageQueueLimit: Int,
    private val botInstance: BotInstance,
    private val reconnectInterval: Long
) : WebSocketClient(URI(address), mapOf("Authorization" to "Bearer $accessToken")) {

    private val logger = Logger.getLogger()
    private var isConnected = false
    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    private val channelCoroutineScope = CoroutineScope(Dispatchers.IO)
    private val messageChannel = Channel<String>(messageQueueLimit)
    private val scheduler = Executors.newScheduledThreadPool(1)
    private lateinit var messageHandler: MessageHandler
    private lateinit var action: OneBotAction

    fun createAction(): OneBotAction {
        this.action = OneBotAction(botInstance, InstanceType.Client)
        this.messageHandler = MessageHandler(botInstance, this.action)
        this.action.setHandler(this.messageHandler)
        return this.action
    }

    init {
        this.processMessages()
    }

    override fun onOpen(handshakedata: ServerHandshake) {
        this.isConnected = true
        coroutineScope.launch {
            messageHandler.onOpen(listener, this@WsClient)
        }
    }

    /**
     * 每次接收到消息时都会向channel中发送数据等待消费
     */
    override fun onMessage(message: String) {
        channelCoroutineScope.launch {
            messageChannel.send(message)
        }
    }

    override fun onClose(code: Int, reason: String, remote: Boolean) {
        this.isConnected = false
        if (autoReconnect) startReconnect()
        coroutineScope.launch {
            messageHandler.onClose(listener, code, reason, remote, this@WsClient)
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

    /**
     * 启动一个线程用于消费管道(Channel)内的消息
     * 每次消费消息都会开一个线程用于处理这条消息
     * 消费完成之后线程会自动回到线程池等下下次启动
     */
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