/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/6
 */

package cn.rtast.rob.kook.webhook

import cn.rtast.rob.BaseBotInstance
import cn.rtast.rob.kook.webhook.kook.KookAction
import cn.rtast.rob.kook.webhook.kook.KookListener
import cn.rtast.rob.kook.webhook.util.HttpServer
import io.ktor.server.engine.*
import io.ktor.server.netty.*

public class BotInstance(
    private val port: Int,
    private val token: String,
    private val verifyToken: String,
    private val encryptKey: String?,
    private val listener: KookListener,
) : BaseBotInstance {
    override val isActionInitialized: Boolean = true
    internal val action: KookAction = KookAction(this)
    internal lateinit var httpServer: EmbeddedServer<NettyApplicationEngine, NettyApplicationEngine.Configuration>

    override suspend fun createBot(): BotInstance {
        httpServer = HttpServer(port, token, verifyToken, encryptKey, listener, this).startHttpServer()
        return this
    }

    override suspend fun disposeBot() {
        httpServer.stop()
    }
}