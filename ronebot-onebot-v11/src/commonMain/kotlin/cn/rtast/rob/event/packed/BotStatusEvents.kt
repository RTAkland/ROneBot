/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/2/27
 */

package cn.rtast.rob.event.packed

import cn.rtast.rob.event.OneBotEvent
import cn.rtast.rob.event.raw.custom.RawBotOfflineEvent
import cn.rtast.rob.event.raw.custom.RawBotOnlineEvent
import cn.rtast.rob.onebot.OneBotAction

/**
 * Bot上线
 */
public data class BotOnlineEvent(
    override val action: OneBotAction,
    val event: RawBotOnlineEvent
) : OneBotEvent

/**
 * Bot离线
 */
public data class BotOfflineEvent(
    override val action: OneBotAction,
    val event: RawBotOfflineEvent
) : OneBotEvent