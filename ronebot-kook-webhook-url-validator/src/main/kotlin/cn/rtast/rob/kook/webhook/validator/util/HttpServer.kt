/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/7
 */


package cn.rtast.rob.kook.webhook.validator.util

import cn.rtast.rob.kook.common.util.decryptMessage
import cn.rtast.rob.kook.common.util.inflate
import cn.rtast.rob.kook.webhook.validator.event.ChallengeEvent
import cn.rtast.rob.kook.webhook.validator.event.ChallengeResponse
import cn.rtast.rob.util.fromJson
import cn.rtast.rob.util.toJson
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

public class HttpServer(
    private val port: Int,
    private val encryptKey: String?,
) {

    public fun startHttpServer(): EmbeddedServer<NettyApplicationEngine, NettyApplicationEngine.Configuration> {
        val server = embeddedServer(Netty, port) {
            routing {
                post(Regex("(.*?)")) {
                    val rawPacket = call.receiveStream().inflate()
                    val packet = rawPacket.fromJson<ChallengeEvent>()
                    val challenge = if (packet.encrypt != null) {
                        decryptMessage(packet.encrypt, encryptKey!!).fromJson<ChallengeEvent>().d.challenge
                    } else packet.d.challenge
                    println("收到Kook服务器下发验证请求, 请在Kook内查看是否成功, 如果成功后即可关闭本程序")
                    call.respondText(ChallengeResponse(challenge).toJson())
                }
            }
        }.also {
            it.start(wait = true)
        }
        return server
    }
}