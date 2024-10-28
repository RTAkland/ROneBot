/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/28
 */


package cn.rtast.rob.satori

import cn.rtast.rob.satori.util.SatoriListener
import cn.rtast.rob.satori.util.WsClient
import org.java_websocket.client.WebSocketClient

class BotInstance internal constructor(
    private val address: String,
    private val listener: SatoriListener,
    private val accessToken: String
) {

    internal lateinit var websocket: WebSocketClient

    fun createBot(): BotInstance {
        websocket = WsClient(address, listener, accessToken, this).also { it.connectBlocking() }
        return this
    }
}