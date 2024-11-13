/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/11
 */


package cn.rtast.rob.qqbot.util

import cn.rtast.rob.qqbot.entity.inbound.SignInbound
import cn.rtast.rob.util.fromJson
import io.ktor.http.HttpStatusCode
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.request.receiveText
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.post
import io.ktor.server.routing.routing

class HttpServer(
    private val port: Int,
    private val clientSecret: String
) {
    private val seed = derivePublicKeyFromSecret(clientSecret)

    fun startHttpServer() {
        embeddedServer(Netty, port) {
            routing {
                post(Regex("(.*?)")) {
                    val sign = call.request.headers["X-Signature-Ed25519"]!!
                    val timestamp = call.request.headers["X-Signature-Timestamp"]!!
                    println(call.receiveText())
                    val body = call.receiveText().fromJson<SignInbound>()
                    val isValid = verifySignature(seed, sign, timestamp, body.d.plainToken)
                    if (isValid) {
                        call.respondText("{\"op\":12}")
                    } else {
                        call.respond(HttpStatusCode.Unauthorized)
                    }
                }
            }
        }.start(wait = true)
    }
}