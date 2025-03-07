/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/7
 */

package cn.rtast.rob.kook.webhook.util

import cn.rtast.rob.kook.common.util.decryptMessage
import cn.rtast.rob.kook.common.util.inflate
import cn.rtast.rob.kook.webhook.BotInstance
import cn.rtast.rob.kook.webhook.entity.VerifyToken
import cn.rtast.rob.kook.webhook.event.RawBaseEvent
import cn.rtast.rob.kook.webhook.event.RawMessageEvent
import cn.rtast.rob.kook.webhook.kook.EventSignal
import cn.rtast.rob.kook.webhook.kook.KookListener
import cn.rtast.rob.util.fromJson
import io.ktor.http.*
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
                    val decryptPacket = if (packet.encrypt != null) {
                        decryptMessage(packet.encrypt, encryptKey!!)
                    } else rawPacket
                    val verifyToken = decryptPacket.fromJson<VerifyToken>().d.verifyToken
                    if (verifyToken != this@HttpServer.verifyToken) return@post
                    this@HttpServer.dispatchMessage(packet, decryptPacket)
                    call.respondText("Received", status = HttpStatusCode.OK)
                }
            }
        }.also { it.start(wait = true) }
        return server
    }

    internal suspend fun dispatchMessage(rawEvent: RawBaseEvent, message: String) {
        when (EventSignal.fromCode(rawEvent.s)) {
            EventSignal.DispatchEventAndMessage -> {
                val event = message.fromJson<RawMessageEvent>().apply {
                    action = botInstance.action
                }
                listener.onChannelMessage(event)
            }

            EventSignal.ServerHandshakeResponse -> TODO()
            EventSignal.Heartbeat -> TODO()
            EventSignal.HeartbeatResponse -> TODO()
            EventSignal.Resume -> TODO()
            EventSignal.ReconnectResponse -> TODO()
            EventSignal.ResumeACK -> TODO()
            null -> TODO()
        }
        println(message)
    }
}