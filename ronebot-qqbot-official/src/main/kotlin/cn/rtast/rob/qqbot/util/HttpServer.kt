/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/11
 */


package cn.rtast.rob.qqbot.util

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
    private val publicKey = derivePublicKeyFromSecret(clientSecret)

    fun startHttpServer() {
        embeddedServer(Netty, port) {
            routing {
                post(Regex("(.*?)")) {
                    val sign = call.request.headers["X-Signature-Ed25519"]!!
                    val timestamp = call.request.headers["X-Signature-Timestamp"]!!
                    val body = call.receiveText()
                    println(body)
                    val isValid = verifySignature(publicKey, sign, timestamp, body)
                    if (isValid) {
                        call.respondText("{\"op\":13}")
                    } else {
                        call.respond(HttpStatusCode.Unauthorized)
                    }
                }
            }
        }.start(wait = true)
    }
}