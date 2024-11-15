/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/15
 */


package cn.rtast.rob.qqbot

import cn.rtast.rob.BaseBotInstance
import cn.rtast.rob.qqbot.qbot.QQBotAction
import cn.rtast.rob.qqbot.qbot.QQBotListener
import cn.rtast.rob.qqbot.util.HttpServer
import io.ktor.server.engine.*
import io.ktor.server.netty.*

class BotInstance internal constructor(
    private val port: Int,
    private val appId: String,
    private val clientSecret: String,
    private val listener: QQBotListener,
) : BaseBotInstance {
    override val isActionInitialized = true

    internal val action = QQBotAction(appId, clientSecret, this)
    internal lateinit var httpServer: EmbeddedServer<NettyApplicationEngine, NettyApplicationEngine.Configuration>

    override suspend fun createBot(): BotInstance {
        httpServer = HttpServer(port, appId, clientSecret, listener, this).startHttpServer()
        return this
    }

    override suspend fun disposeBot() {
        if (::httpServer.isInitialized) {
            httpServer.stop()
        }
    }
}