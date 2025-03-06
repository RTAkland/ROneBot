/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/7
 */

package cn.rtast.rob.kook.webhook.util

import cn.rtast.rob.kook.common.util.decryptMessage
import cn.rtast.rob.kook.common.util.inflate
import cn.rtast.rob.kook.webhook.BotInstance
import cn.rtast.rob.kook.webhook.event.ChallengeEvent
import cn.rtast.rob.kook.webhook.event.ChallengeResponse
import cn.rtast.rob.kook.webhook.event.RawBaseEvent
import cn.rtast.rob.kook.webhook.kook.KookListener
import cn.rtast.rob.kook.webhook.kook.MessageSignal
import cn.rtast.rob.util.fromJson
import cn.rtast.rob.util.toJson
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

public class HttpServer(
    private val port: Int,
    private val token: String,
    private val verifyToken: String,
    private val encryptKey: String?,
    private val listener: KookListener,
    private val botInstance: BotInstance
) {

    public fun startHttpServer(): EmbeddedServer<NettyApplicationEngine, NettyApplicationEngine.Configuration> {
        val server = embeddedServer(Netty, port) {
            routing {
                post(Regex("(.*?)")) {
                    val rawPacket = call.receiveStream().inflate()
                    val packet = rawPacket.fromJson<RawBaseEvent>()
                    when (packet.s) {
                        MessageSignal.Challenge.s -> {
                            val packet = rawPacket.fromJson<ChallengeEvent>()
                            val challenge = if (packet.encrypt != null) {
                                decryptMessage(packet.encrypt, encryptKey!!).fromJson<ChallengeEvent>().d.challenge
                            } else packet.d.challenge
                            println(ChallengeResponse(challenge).toJson())
                            call.respondText(ChallengeResponse(challenge).toJson())
                        }
                    }
                }
            }
        }.also { it.start(wait = true) }
        return server
    }
}