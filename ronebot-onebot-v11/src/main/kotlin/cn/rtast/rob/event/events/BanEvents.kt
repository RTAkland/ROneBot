/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/2/27
 */

package cn.rtast.rob.event.events

import cn.rtast.rob.entity.custom.IBanEvent
import cn.rtast.rob.entity.custom.IPardonBanEvent
import cn.rtast.rob.event.OneBotEvent
import cn.rtast.rob.onebot.OneBotAction

/**
 * 禁言
 */
data class BanEvent(
    override val action: OneBotAction,
    val event: IBanEvent
) : OneBotEvent

/**
 * 解除禁言
 */
data class PardonBanEvent(
    override val action: OneBotAction,
    val event: IPardonBanEvent
) : OneBotEvent