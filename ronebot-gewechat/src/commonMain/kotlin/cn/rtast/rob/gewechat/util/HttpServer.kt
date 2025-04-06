/*
 * Copyright © 2025 RTAkland
 * Date: 2025/4/6 09:15
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.gewechat.util

import cn.rtast.rob.gewechat.BotInstance
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import io.ktor.server.routing.*

public class HttpServer internal constructor(
    private val port: Int,
    private val botInstance: BotInstance
) {
    public fun startServer(): EmbeddedServer<CIOApplicationEngine, CIOApplicationEngine.Configuration> {
        val server = embeddedServer(CIO, port) {
            routing {
                post(Regex("(.*?)")) {
                    println(call.request)
                }
            }
        }.also { it.start(wait = true) }
        return server
    }
}