/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/28
 */


package cn.rtast.rob.satori

import cn.rtast.rob.BaseBotInstance
import cn.rtast.rob.event.listener.AbstractListener
import cn.rtast.rob.satori.enums.Platforms
import cn.rtast.rob.satori.satori.SatoriListener
import cn.rtast.rob.satori.util.WsClient
import org.java_websocket.client.WebSocketClient

public class BotInstance internal constructor(
    private val address: String,
    private val listener: SatoriListener,
    userId: String,
    private val accessToken: String,
    internal val botPlatforms: Platforms
) : BaseBotInstance {

    override val listeners: AbstractListener = object : AbstractListener(this) {}

    internal lateinit var websocket: WebSocketClient
    internal val apiAddress = address
    internal val apiAccessToken = accessToken
    internal val botUserId = userId
    override val isActionInitialized: Boolean get() = websocket.isOpen

    override suspend fun createBot(): BotInstance {
        websocket = WsClient("$address/v1/events", listener, accessToken, this).also { it.connectBlocking() }
        return this
    }

    override suspend fun disposeBot() {
        websocket.close()
        System.gc()
    }
}