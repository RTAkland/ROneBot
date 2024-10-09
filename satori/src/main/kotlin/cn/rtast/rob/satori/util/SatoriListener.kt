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

    suspend fun onWebsocketClose(reason: String, code: Int, isRemote: Boolean) {}

    suspend fun onWebsocketOpen(handshake: ServerHandshake) {}

    suspend fun onWebsocketError(e: Exception) {}

    /**
     * 在鉴权成功之后触发此事件
     */
    suspend fun onReady(loginInfo: LoginInfo) {}

    /**
     * 发送ping包之后接收到pong包触发此事件
     */
    suspend fun onPong() {}

    suspend fun onGroupMessage(message: GroupMessage) {}

    suspend fun onPrivateMessage(message: PrivateMessage) {}

    suspend fun onGroupMessageRevoke(message: GroupRevokeMessage) {}

    suspend fun onPrivateMessageRevoke(message: PrivateRevokeMessage) {}
}