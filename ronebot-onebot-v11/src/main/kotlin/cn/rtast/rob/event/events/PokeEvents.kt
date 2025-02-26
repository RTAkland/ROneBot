/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/2/27
 */

package cn.rtast.rob.event.events

import cn.rtast.rob.entity.lagrange.PokeEvent
import cn.rtast.rob.event.OneBotEvent
import cn.rtast.rob.onebot.OneBotAction

/**
 * 群聊戳一戳但是被戳一戳的是机器人的时候才会触发
 */
data class GroupPokeSelfEvent(
    override val action: OneBotAction,
    val event: PokeEvent
) : OneBotEvent

/**
 * 群聊戳一戳
 */
data class GroupPokeEvent(
    override val action: OneBotAction,
    val event: PokeEvent
) : OneBotEvent

/**
 * 私聊戳一戳
 */
data class PrivatePokeEvent(
    override val action: OneBotAction,
    val event: PokeEvent
) : OneBotEvent
