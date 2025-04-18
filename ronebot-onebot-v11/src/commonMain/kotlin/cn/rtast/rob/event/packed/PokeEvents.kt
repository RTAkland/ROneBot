/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/2/27
 */

package cn.rtast.rob.event.packed

import cn.rtast.rob.actionable.OperatorActionable
import cn.rtast.rob.actionable.OperatorWithOperatedUserActionable
import cn.rtast.rob.event.OneBotEvent
import cn.rtast.rob.event.raw.group.RawPokeEvent
import cn.rtast.rob.onebot.OneBotAction

/**
 * 群聊戳一戳但是被戳一戳的是机器人的时候才会触发
 */
public data class GroupPokeSelfEvent(
    override val action: OneBotAction,
    val event: RawPokeEvent
) : OneBotEvent, OperatorWithOperatedUserActionable by event

/**
 * 群聊戳一戳
 */
public data class GroupPokeEvent(
    override val action: OneBotAction,
    val event: RawPokeEvent
) : OneBotEvent, OperatorWithOperatedUserActionable by event

/**
 * 私聊戳一戳
 */
public data class PrivatePokeEvent(
    override val action: OneBotAction,
    val event: RawPokeEvent
) : OneBotEvent, OperatorActionable by event
