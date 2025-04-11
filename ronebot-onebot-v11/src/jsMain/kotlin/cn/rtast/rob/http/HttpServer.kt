/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 2025/4/11 12:45
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */


@file:OptIn(DelicateCoroutinesApi::class)

package cn.rtast.rob.http

import cn.rtast.rob.annotations.JsPlatformOnly
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import io.ktor.server.routing.*
import kotlinx.coroutines.DelicateCoroutinesApi

@JsPlatformOnly
public class HttpServer(private val port: Int, private val path: String = "/") {
    public fun start(): EmbeddedServer<CIOApplicationEngine, CIOApplicationEngine.Configuration> {
        return embeddedServer(CIO) {
            routing {
                post(path) {

                }
            }
        }
    }
}