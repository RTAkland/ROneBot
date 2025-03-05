/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/2/27
 */

package cn.rtast.rob.event.events

import cn.rtast.rob.event.OneBotEvent
import cn.rtast.rob.onebot.OneBotAction

/**
 * 全局事件，任何OneBot实现下发的事件
 * 的原始信息都会在这里
 */
public data class RawEvent(
    override val action: OneBotAction,
    val rawMessage: String
) : OneBotEvent