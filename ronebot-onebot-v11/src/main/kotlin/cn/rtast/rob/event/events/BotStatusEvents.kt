/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/2/27
 */

package cn.rtast.rob.event.events

import cn.rtast.rob.entity.custom.IBotOfflineEvent
import cn.rtast.rob.entity.custom.IBotOnlineEvent
import cn.rtast.rob.event.OneBotEvent
import cn.rtast.rob.onebot.OneBotAction

/**
 * Bot上线
 */
data class BotOnlineEvent(
    override val action: OneBotAction,
    val event: IBotOnlineEvent
) : OneBotEvent

/**
 * Bot离线
 */
data class BotOfflineEvent(
    override val action: OneBotAction,
    val event: IBotOfflineEvent
) : OneBotEvent