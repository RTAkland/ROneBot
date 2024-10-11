/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/8
 */


package cn.rtast.rob.satori

import cn.rtast.rob.common.BotFactory
import cn.rtast.rob.satori.util.SatoriListener
import cn.rtast.rob.satori.util.WsClient
import org.java_websocket.client.WebSocketClient

object RSatoriFactory : BotFactory {

    internal lateinit var client: WebSocketClient
    internal lateinit var token: String

    fun createClient(address: String, token: String, listenSelf: Boolean, listener: SatoriListener) {
        this.token = token
        client = WsClient("$address/v1/events", listenSelf, listener).also { it.connect() }
    }
}