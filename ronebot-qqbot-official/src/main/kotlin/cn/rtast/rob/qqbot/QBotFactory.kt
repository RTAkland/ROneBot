/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/11
 */


package cn.rtast.rob.qqbot

import cn.rtast.rob.BotFactory
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing

object QBotFactory : BotFactory {

    fun createServer(port: Int) {
        embeddedServer(Netty, port) {
            routing {
                get("/") {
                    call.request.headers.forEach { it, l ->
                        println(it)
                        println(l)
                    }
                    call.respondText("OK")
                }
            }
        }.start(wait = true)
    }
}