/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 2025/4/11 12:27
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.util.js

import cn.rtast.klogging.LogLevel
import cn.rtast.rob.BotInstance
import cn.rtast.rob.onebot.OneBotListener
import kotlin.time.Duration


/**
 * 用不到
 */
public actual suspend fun sendHttp(server: String, accessToken: String, content: String): String {
    TODO("Not yet implemented")
}

/**
 * 用不到
 */
public actual suspend fun createHttpServer(
    server: String,
    port: Int,
    accessToken: String,
    listener: OneBotListener,
    path: String,
    messageExecuteDuration: Duration,
    logLevel: LogLevel
): BotInstance {
    TODO("Not yet implemented")
}

/**
 * 用不到
 */
public actual fun closeJsHttpServer() {
    TODO("Not yet implemented")
}