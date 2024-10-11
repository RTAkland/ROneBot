/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/8
 */


package cn.rtast.rob.satori.util

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
    private val listenSelf: Boolean,
    private val listener: SatoriListener
) : WebSocketClient(URI(address)) {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    private val messageChannel = Channel<String>()
    private val scheduler = Executors.newScheduledThreadPool(2)
    private val reconnectInterval = 5000L
    private var isConnected = false


    init {
        processMessages()
    }

    override fun onOpen(handshakedata: ServerHandshake) {
        coroutineScope.launch {
            MessageHandler.onOpen(listener, handshakedata)
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
            MessageHandler.onClose(listener, code, reason, remote)
        }
    }

    override fun onError(ex: Exception) {
        coroutineScope.launch {
            MessageHandler.onError(listener, ex)
        }
    }

    private fun processMessages() {
        coroutineScope.launch {
            for (message in messageChannel) {
                coroutineScope.launch {
                    MessageHandler.onMessage(listener, listenSelf, message)
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