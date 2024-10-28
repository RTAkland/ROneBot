/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/8
 */

package cn.rtast.rob.satori.util

import cn.rtast.rob.common.util.fromJson
import cn.rtast.rob.common.util.toJson
import cn.rtast.rob.satori.entity.BaseMessage
import cn.rtast.rob.satori.entity.GroupMessage
import cn.rtast.rob.satori.entity.GroupRevokeMessage
import cn.rtast.rob.satori.entity.LoginInfo
import cn.rtast.rob.satori.entity.PrivateMessage
import cn.rtast.rob.satori.entity.PrivateRevokeMessage
import cn.rtast.rob.satori.entity.guild.GuildAdded
import cn.rtast.rob.satori.entity.guild.GuildMemberAdded
import cn.rtast.rob.satori.entity.guild.GuildRemoved
import cn.rtast.rob.satori.entity.guild.GuildRequest
import cn.rtast.rob.satori.entity.internal.OPMessage
import cn.rtast.rob.satori.entity.out.AuthPacketOut
import cn.rtast.rob.satori.entity.out.PingPacketOut
import cn.rtast.rob.satori.enums.OPCode
import cn.rtast.rob.satori.enums.forCode
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import java.lang.Exception
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class MessageHandler internal constructor(
    private val websocket: WebSocketClient,
    private val accessToken: String
) {

    private val scheduler = Executors.newScheduledThreadPool(1)
    private lateinit var loginInfo: LoginInfo

    fun sendAuthPacket() {
        val authPacket = AuthPacketOut(body = AuthPacketOut.AuthBody(accessToken)).toJson()
        websocket.send(authPacket)
    }

    fun startHeartbeat() {
        val task = Runnable {
            val pingPacket = PingPacketOut().toJson()
            websocket.send(pingPacket)
        }
        scheduler.scheduleAtFixedRate(task, 0, 5, TimeUnit.SECONDS)
    }

    suspend fun onOpen(listener: SatoriListener, action: SatoriAction, handshake: ServerHandshake) {
        this.sendAuthPacket()
        this.startHeartbeat()
        listener.onWebsocketOpen(action, handshake)
    }

    suspend fun onMessage(listener: SatoriListener, action: SatoriAction, message: String) {
        println(message)
        val baseMessage = message.fromJson<OPMessage>()
        val opCode = baseMessage.op.forCode()
        when (opCode) {
            OPCode.EVENT -> {
                val generalMessage = message.fromJson<BaseMessage>()
                generalMessage.body.action = action
                when (generalMessage.body.type) {
                    "message-created" -> {
                        if (generalMessage.body.member != null) {
                            listener.onGroupMessage(message.fromJson<GroupMessage>().body.also { it.action = action })
                        } else {
                            listener.onPrivateMessage(message.fromJson<PrivateMessage>().body.also {
                                it.action = action
                            })
                        }
                    }

                    "message-deleted" -> {
                        if (generalMessage.body.member != null) {
                            listener.onGroupMessageRevoke(message.fromJson<GroupRevokeMessage>().body.also {
                                it.action = action
                            })
                        } else {
                            listener.onPrivateMessageRevoke(message.fromJson<PrivateRevokeMessage>().body.also {
                                it.action = action
                            })
                        }
                    }

                    "guild-removed" -> listener.onGuildRemoved(message.fromJson<GuildRemoved>().body.also {
                        it.action = action
                    })

                    "guild-request" -> listener.onGuildRequest(message.fromJson<GuildRequest>().body.also {
                        it.action = action
                    })

                    "guild-added" -> listener.onGuildAdded(message.fromJson<GuildAdded>().body.also {
                        it.action = action
                    })

                    "guild-member-added" -> listener.onGuildMemberAdded(message.fromJson<GuildMemberAdded>().body.also {
                        it.action = action
                    })
                }
            }

            OPCode.Pong -> listener.onPong(action)
            OPCode.READY -> {
                val loginInfo = message.fromJson<LoginInfo>()
                loginInfo.body.action = action
                this.loginInfo = loginInfo
                listener.onReady(loginInfo.body)
            }

            OPCode.IDENTIFY -> {}  // nothing here
            OPCode.Ping -> {}  // nothing here
        }
    }

    suspend fun onClose(listener: SatoriListener, action: SatoriAction, code: Int, reason: String, remote: Boolean) =
        listener.onWebsocketClose(action, reason, code, remote)

    suspend fun onError(listener: SatoriListener, action: SatoriAction, ex: Exception) =
        listener.onWebsocketError(action, ex)
}