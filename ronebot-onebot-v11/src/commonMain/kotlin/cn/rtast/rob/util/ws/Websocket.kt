/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/23
 */

package cn.rtast.rob.util.ws

import cn.rtast.rob.BotInstance
import cn.rtast.rob.onebot.OneBotListener
import kotlin.time.Duration

public expect suspend fun createClient(
    address: String,
    accessToken: String,
    listener: OneBotListener,
    autoReconnect: Boolean,
    botInstance: BotInstance,
    reconnectInterval: Long,
    executeDuration: Duration
): WebsocketSession

public expect suspend fun createServer(
    port: Int,
    accessToken: String,
    listener: OneBotListener,
    botInstance: BotInstance,
    path: String,
    executeDuration: Duration
): WebsocketSession