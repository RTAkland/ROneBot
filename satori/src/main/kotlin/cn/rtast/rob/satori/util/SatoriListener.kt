/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/8
 */


package cn.rtast.rob.satori.util

import cn.rtast.rob.satori.entity.LoginInfo

interface SatoriListener: SatoriAction {
    /**
     * 在鉴权成功之后触发此事件
     */
    suspend fun onReady(loginInfo: LoginInfo)

    /**
     * 发送ping包之后接收到pong包触发此事件
     */
    suspend fun onPong()

    suspend fun onGroupMessage()

    suspend fun onPrivateMessage()

    suspend fun onChannelMessage()
}