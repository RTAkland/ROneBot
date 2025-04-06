/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/15
 */

@file:Suppress("unused")
@file:OptIn(InternalROneBotApi::class)

package cn.rtast.rob.qqbot

import cn.rtast.klogging.LogLevel
import cn.rtast.rob.BaseBotInstance
import cn.rtast.rob.annotations.InternalROneBotApi
import cn.rtast.rob.qqbot.qbot.QQBotAction
import cn.rtast.rob.qqbot.qbot.QQBotListener
import cn.rtast.rob.qqbot.util.HttpServer
import cn.rtast.rob.scheduler.BotCoroutineScheduler
import cn.rtast.rob.util.getLogger
import io.ktor.server.cio.CIOApplicationEngine
import io.ktor.server.engine.*

public class BotInstance internal constructor(
    private val port: Int,
    private val appId: String,
    private val clientSecret: String,
    private val listener: QQBotListener,
    private val logLevel: LogLevel
) : BaseBotInstance {
    override val isActionInitialized: Boolean = true

    internal val logger = getLogger("[S]").apply { setLoggingLevel(logLevel) }
    internal val action = QQBotAction(appId, clientSecret, this)
    internal lateinit var httpServer: EmbeddedServer<CIOApplicationEngine, CIOApplicationEngine.Configuration>

    /**
     * Bot实例作用于的任务调度器
     */
    public val scheduler: BotCoroutineScheduler<BotInstance> = BotCoroutineScheduler(this)

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