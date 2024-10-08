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

internal class WsClient(
    address: String,
    private val listener: SatoriListener
) : WebSocketClient(URI(address)) {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    private val messageChannel = Channel<String>()

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
                    MessageHandler.onMessage(listener, message)
                }
            }
        }
    }
}