/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/2/27
 */

package cn.rtast.rob.event.events

import cn.rtast.rob.entity.custom.IBotBeKickEvent
import cn.rtast.rob.entity.custom.IMemberKickEvent
import cn.rtast.rob.event.OneBotEvent
import cn.rtast.rob.onebot.OneBotAction

/**
 * 成员被踢出群聊
 */
data class MemberKickEvent(
    override val action: OneBotAction,
    val event: IMemberKickEvent
) : OneBotEvent

/**
 * Bot被踢出群聊
 */
data class BotBeKickEvent(
    override val action: OneBotAction,
    val event: IBotBeKickEvent
) : OneBotEvent