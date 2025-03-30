/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/23
 */

@file:Suppress("unused")

package cn.rtast.rob.util.ws

import cn.rtast.rob.BotInstance
import cn.rtast.rob.onebot.OneBotListener
import kotlin.time.Duration

public expect class WebsocketSession() {
    /**
     * 创建服务端
     */
    public suspend fun createServer(
        port: Int,
        accessToken: String,
        listener: OneBotListener,
        botInstance: BotInstance,
        path: String,
        executeDuration: Duration
    )

    /**
     * 创建客户端
     */
    public suspend fun createClient(
        address: String,
        accessToken: String,
        listener: OneBotListener,
        autoReconnect: Boolean,
        botInstance: BotInstance,
        reconnectInterval: Long,
        executeDuration: Duration
    )

    /**
     * 关闭链接
     */
    public suspend fun closeClient()

    /**
     * 关闭服务器
     */
    public suspend fun closeServer()

    /**
     * 向OneBot Ws服务器发送消息
     */
    public suspend fun sendToServer(text: String)

    /**
     * 向OneBot Ws客户端发送消息
     */
    public suspend fun sendToClient(text: String)
}