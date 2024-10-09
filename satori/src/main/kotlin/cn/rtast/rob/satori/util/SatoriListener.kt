/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/8
 */


package cn.rtast.rob.satori.util

import cn.rtast.rob.satori.entity.GroupMessage
import cn.rtast.rob.satori.entity.GroupRevokeMessage
import cn.rtast.rob.satori.entity.LoginInfo
import cn.rtast.rob.satori.entity.PrivateMessage
import cn.rtast.rob.satori.entity.PrivateRevokeMessage
import org.java_websocket.handshake.ServerHandshake

interface SatoriListener : SatoriAction {

    /**
     * Websocket连接关闭时触发此事件
     */
    suspend fun onWebsocketClose(reason: String, code: Int, isRemote: Boolean) {}

    /**
     * Websocket连接打开时触发此事件
     */
    suspend fun onWebsocketOpen(handshake: ServerHandshake) {}

    /**
     * Websocket连接出现错误时触发此事件
     */
    suspend fun onWebsocketError(e: Exception) {}

    /**
     * 在鉴权成功之后触发此事件
     */
    suspend fun onReady(loginInfo: LoginInfo) {}

    /**
     * 发送ping包之后接收到pong包触发此事件
     */
    suspend fun onPong() {}

    /**
     * 群聊消息
     */
    suspend fun onGroupMessage(message: GroupMessage) {}

    /**
     * 私聊消息
     */
    suspend fun onPrivateMessage(message: PrivateMessage) {}

    /**
     * 群聊消息被撤回
     */
    suspend fun onGroupMessageRevoke(message: GroupRevokeMessage) {}

    /**
     * 私聊消息被撤回
     */
    suspend fun onPrivateMessageRevoke(message: PrivateRevokeMessage) {}
}