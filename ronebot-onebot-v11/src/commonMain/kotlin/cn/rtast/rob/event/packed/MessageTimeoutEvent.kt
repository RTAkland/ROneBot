/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/10
 */

package cn.rtast.rob.event.packed

import cn.rtast.rob.event.OneBotEvent
import cn.rtast.rob.onebot.OneBotAction

/**
 * 消息执行超时
 * @param message 原始消息信息
 */
public data class MessageTimeoutEvent(
    override val action: OneBotAction,
    val message: String
) : OneBotEvent