/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/8
 */


package cn.rtast.rob.satori.satori

import cn.rtast.rob.satori.entity.*
import cn.rtast.rob.satori.entity.guild.events.GuildAdded
import cn.rtast.rob.satori.entity.guild.events.GuildMemberAdded
import cn.rtast.rob.satori.entity.guild.events.GuildRemoved
import cn.rtast.rob.satori.entity.guild.events.GuildRequest
import org.java_websocket.handshake.ServerHandshake

interface SatoriListener {

    /**
     * Websocket连接关闭时触发此事件
     */
    suspend fun onWebsocketClose(action: SatoriAction, reason: String, code: Int, isRemote: Boolean) {}

    /**
     * Websocket连接打开时触发此事件
     */
    suspend fun onWebsocketOpen(action: SatoriAction, handshake: ServerHandshake) {}

    /**
     * Websocket连接出现错误时触发此事件
     */
    suspend fun onWebsocketError(action: SatoriAction, e: Exception) {}

    /**
     * 在鉴权成功之后触发此事件
     */
    suspend fun onReady(loginInfo: LoginInfo.Body) {}

    /**
     * 发送ping包之后接收到pong包触发此事件
     */
    suspend fun onPong(action: SatoriAction) {}

    /**
     * 群聊消息
     */
    suspend fun onGroupMessage(message: GuildMessage.Message) {}

    /**
     * 私聊消息
     */
    suspend fun onPrivateMessage(message: PrivateMessage.Message) {}

    /**
     * 群聊消息被撤回
     */
    suspend fun onGroupMessageRevoke(message: GroupRevokeMessage.RevokeMessage) {}

    /**
     * 私聊消息被撤回
     */
    suspend fun onPrivateMessageRevoke(message: PrivateRevokeMessage.RevokeMessage) {}

    /**
     * 被群聊踢出时
     */
    suspend fun onGuildRemoved(event: GuildRemoved.GuildRemoved) {}

    /**
     * 在被邀请加群时
     */
    suspend fun onGuildRequest(event: GuildRequest.GuildRequest) {}

    /**
     * 在Bot自身加入群聊时
     */
    suspend fun onGuildAdded(event: GuildAdded.GuildAdded) {}

    /**
     * 在新成员加入群聊时
     */
    suspend fun onGuildMemberAdded(event: GuildMemberAdded.GuildMemberAdded) {}
}