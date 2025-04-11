/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 2025/4/11 12:27
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */


package cn.rtast.rob.util.js

import cn.rtast.klogging.LogLevel
import cn.rtast.rob.BotInstance
import cn.rtast.rob.annotations.JsPlatformOnly
import cn.rtast.rob.onebot.OneBotListener
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

@JsPlatformOnly
public expect suspend fun sendHttp(server: String, accessToken: String, content: String): String

@JsPlatformOnly
public expect fun closeJsHttpServer()

@JsPlatformOnly
public expect suspend fun createHttpServer(
    server: String,
    port: Int,
    accessToken: String,
    listener: OneBotListener = object : OneBotListener {},
    path: String = "/",
    messageExecuteDuration: Duration = 0.seconds,
    logLevel: LogLevel = LogLevel.INFO
): BotInstance