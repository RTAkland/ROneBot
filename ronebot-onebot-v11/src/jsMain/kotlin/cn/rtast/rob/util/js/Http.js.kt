/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 2025/4/11 12:34
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

@file:OptIn(InternalROneBotApi::class)

package cn.rtast.rob.util.js

import cn.rtast.klogging.LogLevel
import cn.rtast.rob.BotInstance
import cn.rtast.rob.OneBotFactory.Companion.botManager
import cn.rtast.rob.annotations.InternalROneBotApi
import cn.rtast.rob.annotations.JsPlatformOnly
import cn.rtast.rob.common.http.JsHttp
import cn.rtast.rob.entity.Packet
import cn.rtast.rob.enums.internal.InstanceType
import cn.rtast.rob.http.HttpServer
import cn.rtast.rob.onebot.OneBotListener
import cn.rtast.rob.util.fromJson
import cn.rtast.rob.util.toJson
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

@JsPlatformOnly
public actual suspend fun sendHttp(server: String, accessToken: String, content: String): String {
    val extractedPayload = content.fromJson<Packet>().params.toJson()
    return JsHttp.post(server, extractedPayload, mapOf("Authorization" to "Bearer $accessToken"), null)
}

@JsPlatformOnly
public var httpServer: EmbeddedServer<CIOApplicationEngine, CIOApplicationEngine.Configuration>? = null

@JsPlatformOnly
public actual suspend fun createHttpServer(
    server: String,
    port: Int,
    accessToken: String,
    listener: OneBotListener,
    path: String,
    messageExecuteDuration: Duration,
    logLevel: LogLevel
): BotInstance {
    httpServer = HttpServer(port, path).start()
    return BotInstance(
        server, accessToken, listener,
        false, port, InstanceType.HttpServer,
        path, 0.seconds, messageExecuteDuration,
        logLevel, true
    ).apply {
        botManager.addBotInstance(this)
        createBot()
    }
}

public actual fun closeJsHttpServer() {
    httpServer?.stop()
    httpServer = null
}