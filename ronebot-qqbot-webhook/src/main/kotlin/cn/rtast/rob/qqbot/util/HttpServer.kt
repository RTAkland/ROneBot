/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/11
 */


package cn.rtast.rob.qqbot.util

import cn.rtast.rob.qqbot.BotInstance
import cn.rtast.rob.qqbot.entity.inbound.C2CMessageCreate
import cn.rtast.rob.qqbot.entity.inbound.FriendAddEvent
import cn.rtast.rob.qqbot.entity.inbound.GroupAtMessageCreate
import cn.rtast.rob.qqbot.entity.inbound.SignInbound
import cn.rtast.rob.qqbot.entity.internal.BasePacket
import cn.rtast.rob.qqbot.entity.internal.URLVerifyContent
import cn.rtast.rob.qqbot.enums.OPCode
import cn.rtast.rob.qqbot.enums.internal.MessageDispatchType
import cn.rtast.rob.qqbot.qbot.QQBotListener
import cn.rtast.rob.util.fromJson
import cn.rtast.rob.util.toJson
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

class HttpServer(
    private val port: Int,
    private val appId: String,
    private val clientSecret: String,
    private val listener: QQBotListener,
    private val botInstance: BotInstance
) {

    fun startHttpServer(): EmbeddedServer<NettyApplicationEngine, NettyApplicationEngine.Configuration> {
        val server = embeddedServer(Netty, port) {
            routing {
                post(Regex("(.*?)")) {
                    val packet = call.receiveText()
                    val basePacket = packet.fromJson<BasePacket>()
                    when (basePacket.op) {
                        OPCode.CallbackURLVerify.opCode -> {
                            val body = packet.fromJson<SignInbound>()
                            val sign = handleValidation(body, clientSecret).toJson()
                            call.respond(sign)
                        }

                        OPCode.Dispatch.opCode -> this@HttpServer.dispatchMessage(basePacket, packet)

                        else -> println(packet)
                    }
                }

                get("/${appId}.json") {
                    // 验证下发消息Webhook地址
                    val response = URLVerifyContent(appId.toLong()).toJson()
                    call.respond(response)
                }
            }
        }.also { it.start() }
        return server
    }

    internal suspend fun dispatchMessage(basePacket: BasePacket, packet: String) {
        when (basePacket.t) {
            MessageDispatchType.GROUP_AT_MESSAGE_CREATE -> {
                val message = packet.fromJson<GroupAtMessageCreate>()
                message.d.action = botInstance.action
                listener.onGroupMessage(message.d)
            }

            MessageDispatchType.FRIEND_ADD -> {
                val event = packet.fromJson<FriendAddEvent>()
                event.d.action = botInstance.action
                listener.onFriendAdd(event.d)
            }

            MessageDispatchType.C2C_MESSAGE_CREATE -> {
                val message = packet.fromJson<C2CMessageCreate>()
                message.d.action = botInstance.action
                listener.onC2CMessage(message.d)
            }
        }
    }
}