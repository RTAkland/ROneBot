/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/8
 */


package cn.rtast.rob.satori.util

import cn.rtast.rob.satori.BotInstance
import cn.rtast.rob.satori.satori.MessageHandler
import cn.rtast.rob.satori.satori.SatoriAction
import cn.rtast.rob.satori.satori.SatoriListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import java.lang.Exception
import java.net.URI
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

internal class WsClient(
    address: String,
    private val listener: SatoriListener,
    accessToken: String,
    botInstance: BotInstance
) : WebSocketClient(URI(address)) {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    private val messageChannel = Channel<String>()
    private val scheduler = Executors.newScheduledThreadPool(2)
    private val reconnectInterval = 5000L
    private var isConnected = false
    private val messageHandler = MessageHandler(this, accessToken)
    private val action = SatoriAction(botInstance)


    init {
        processMessages()
    }

    override fun onOpen(handshakedata: ServerHandshake) {
        coroutineScope.launch {
            messageHandler.onOpen(listener, action, handshakedata)
        }
    }

    override fun onMessage(message: String) {
        coroutineScope.launch {
            messageChannel.send(message)
        }
    }

    override fun onClose(code: Int, reason: String, remote: Boolean) {
        this.isConnected = false
        startReconnect()
        coroutineScope.launch {
            messageHandler.onClose(listener, action, code, reason, remote)
        }
    }

    override fun onError(ex: Exception) {
        coroutineScope.launch {
            messageHandler.onError(listener, action, ex)
        }
    }

    private fun processMessages() {
        coroutineScope.launch {
            for (message in messageChannel) {
                coroutineScope.launch {
                    messageHandler.onMessage(listener, action, message)
                }
            }
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
}