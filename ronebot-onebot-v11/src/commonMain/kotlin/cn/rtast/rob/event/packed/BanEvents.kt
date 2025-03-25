/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/2/27
 */

package cn.rtast.rob.event.packed

import cn.rtast.rob.event.OneBotEvent
import cn.rtast.rob.event.raw.custom.RawBanEvent
import cn.rtast.rob.event.raw.custom.RawPardonBanEvent
import cn.rtast.rob.onebot.OneBotAction

/**
 * 禁言
 */
public data class BanEvent(
    override val action: OneBotAction,
    val event: RawBanEvent
) : OneBotEvent

/**
 * 解除禁言
 */
public data class PardonBanEvent(
    override val action: OneBotAction,
    val event: RawPardonBanEvent
) : OneBotEvent