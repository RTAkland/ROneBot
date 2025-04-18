/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 2025/4/18 20:04
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.onebot.v12.ws

import io.ktor.server.cio.CIO
import io.ktor.server.cio.CIOApplicationEngine
import io.ktor.server.engine.EmbeddedServer
import io.ktor.server.engine.embeddedServer
import io.ktor.server.routing.routing
import io.ktor.server.websocket.webSocket

internal class WebsocketServer {
    fun createServer(port: Int, accessToken: String): EmbeddedServer<CIOApplicationEngine, CIOApplicationEngine.Configuration> {
        return embeddedServer(CIO, port) {
            routing {
                webSocket {  }
            }
        }
    }
}