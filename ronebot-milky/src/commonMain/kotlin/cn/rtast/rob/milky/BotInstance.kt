/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/18/25, 2:54 AM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

@file:OptIn(InternalROneBotApi::class)

package cn.rtast.rob.milky

import cn.rtast.klogging.LogLevel
import cn.rtast.rob.BaseBotInstance
import cn.rtast.rob.annotations.InternalROneBotApi
import cn.rtast.rob.milky.milky.MilkyAction
import cn.rtast.rob.milky.milky.MilkyListener
import cn.rtast.rob.milky.util.connectToEventEndpoint
import cn.rtast.rob.milky.util.http.clientEngine
import cn.rtast.rob.scheduler.BotCoroutineScheduler
import cn.rtast.rob.util.getLogger
import io.ktor.client.*
import io.ktor.client.plugins.websocket.*

/**
 * Bot实例
 */
public class BotInstance internal constructor(
    public val address: String,
    public val accessToken: String?,
    public val listener: MilkyListener,
    public val logLevel: LogLevel
) : BaseBotInstance {
    public val action: MilkyAction = MilkyAction(this)
    internal val logger = getLogger("[C]").apply { setLoggingLevel(logLevel) }

    @InternalROneBotApi
    public val httpClient: HttpClient = HttpClient(clientEngine) {
        install(WebSockets)
    }

    public val scheduler: BotCoroutineScheduler<BotInstance> = BotCoroutineScheduler(this)

    override suspend fun createBot(): BotInstance {
//        connectToEventEndpoint()
        return this
    }

    override suspend fun disposeBot() {
    }
}