/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/11
 */


@file:OptIn(InternalROneBotApi::class)

package cn.rtast.rob.qqbot.util

import cn.rtast.rob.annotations.InternalROneBotApi
import cn.rtast.rob.qqbot.BotInstance
import cn.rtast.rob.qqbot.QBotFactory
import cn.rtast.rob.qqbot.entity.HttpCallbackACK
import cn.rtast.rob.qqbot.entity.inbound.*
import cn.rtast.rob.qqbot.entity.internal.BasePacket
import cn.rtast.rob.qqbot.entity.internal.SignInbound
import cn.rtast.rob.qqbot.entity.internal.URLVerifyContent
import cn.rtast.rob.qqbot.enums.OPCode
import cn.rtast.rob.qqbot.enums.internal.MessageDispatchType
import cn.rtast.rob.qqbot.qbot.QQBotListener
import cn.rtast.rob.util.fromJson
import cn.rtast.rob.util.toJson
import io.ktor.server.cio.CIO
import io.ktor.server.cio.CIOApplicationEngine
import io.ktor.server.engine.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

public class HttpServer(
    private val port: Int,
    private val appId: String,
    private val clientSecret: String,
    private val listener: QQBotListener,
    private val botInstance: BotInstance
) {

    public fun startHttpServer(): EmbeddedServer<CIOApplicationEngine, CIOApplicationEngine.Configuration> {
        val server = embeddedServer(CIO, port) {
            routing {
                post(Regex("(.*?)")) {
                    val packet = call.receiveText()
                    botInstance.logger.debug(packet)
                    val basePacket = packet.fromJson<BasePacket>()
                    when (basePacket.op) {
                        OPCode.CallbackURLVerify.opCode -> {
                            val body = packet.fromJson<SignInbound>()
                            val sign = handleValidation(body, clientSecret).toJson()
                            call.respond(sign)
                        }

                        OPCode.Dispatch.opCode -> call.respond(this@HttpServer.dispatchMessage(basePacket, packet))

                        else -> println(packet)
                    }
                }

                get("/${appId}.json") {
                    // 验证下发消息Webhook地址
                    val response = URLVerifyContent(appId.toLong()).toJson()
                    call.respond(response)
                }
            }
        }.also { it.start(wait = true) }
        return server
    }

    internal suspend fun dispatchMessage(basePacket: BasePacket, packet: String): String {
        when (basePacket.t) {
            MessageDispatchType.GROUP_AT_MESSAGE_CREATE -> {
                val message = packet.fromJson<GroupAtMessageCreateEvent>()
                message.d.action = botInstance.action
                QBotFactory.commandManager.handleGroup(message)
                listener.onGroupMessage(message)
            }

            MessageDispatchType.FRIEND_ADD -> {
                val event = packet.fromJson<FriendAddEvent>()
                event.d.action = botInstance.action
                listener.onFriendAdd(event)
            }

            MessageDispatchType.C2C_MESSAGE_CREATE -> {
                val message = packet.fromJson<C2CMessageCreateEvent>()
                message.d.action = botInstance.action
                QBotFactory.commandManager.handlePrivate(message)
                listener.onC2CMessage(message)
            }

            MessageDispatchType.FRIEND_DEL -> {
                val event = packet.fromJson<FriendDelEvent>()
                event.d.action = botInstance.action
                listener.onFriendDelete(event)
            }

            MessageDispatchType.GROUP_DEL_ROBOT -> {
                val event = packet.fromJson<GroupDeleteRobotEvent>()
                event.d.action = botInstance.action
                listener.onGroupDeleteRobot(event)
            }

            MessageDispatchType.GROUP_ADD_ROBOT -> {
                val event = packet.fromJson<GroupAddRobotEvent>()
                event.d.action = botInstance.action
                listener.onGroupAddRobot(event)
            }

            MessageDispatchType.C2C_MSG_RECEIVE -> {
                val event = packet.fromJson<C2CMessageReceiveEvent>()
                event.d.action = botInstance.action
                listener.onC2CMessageReceiveEvent(event)
            }

            MessageDispatchType.C2C_MSG_REJECT -> {
                val event = packet.fromJson<C2CMessageRejectEvent>()
                event.d.action = botInstance.action
                listener.onC2CMessageRejectEvent(event)
            }

            MessageDispatchType.GROUP_MSG_REJECT -> {
                val event = packet.fromJson<GroupMessageRejectEvent>()
                event.d.action = botInstance.action
                listener.onGroupMessageRejectEvent(event)
            }

            MessageDispatchType.GROUP_MSG_RECEIVE -> {
                val event = packet.fromJson<GroupMessageReceiveEvent>()
                event.d.action = botInstance.action
                listener.onGroupMessageReceiveEvent(event)
            }
        }
        return HttpCallbackACK().toJson()
    }
}