/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/26
 */


package cn.rtast.rob.util.ws

import cn.rtast.rob.util.ob.MessageHandler
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
    messageQueueLimit: Int
) : WebSocketClient(URI(address), mapOf("Authorization" to "Bearer $accessToken")) {

    private val reconnectInterval = 5000L
    private var isConnected = false
    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    private val channelCoroutineScope = CoroutineScope(Dispatchers.IO)
    private val messageChannel = Channel<String>(messageQueueLimit)
    private val scheduler = Executors.newScheduledThreadPool(1)

    init {
        this.processMessages()
    }

    override fun onOpen(handshakedata: ServerHandshake) {
        this.isConnected = true
        coroutineScope.launch {
            MessageHandler.onOpen(listener, this@WsClient)
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
            MessageHandler.onClose(listener, code, reason, remote, this@WsClient)
        }
    }

    override fun onError(ex: Exception) {
        coroutineScope.launch {
            MessageHandler.onError(listener, ex)
        }
    }

    private fun startReconnect() {
        scheduler.schedule({
            try {
                println("Reconnecting...")
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
                    MessageHandler.onMessage(listener, message)
                }
            }
        }
    }
}