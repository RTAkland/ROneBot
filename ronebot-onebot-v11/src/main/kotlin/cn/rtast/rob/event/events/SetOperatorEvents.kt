/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/2/27
 */

package cn.rtast.rob.event.events

import cn.rtast.rob.entity.custom.RawSetOperatorEvent
import cn.rtast.rob.entity.custom.RawUnsetOperatorEvent
import cn.rtast.rob.event.OneBotEvent
import cn.rtast.rob.onebot.OneBotAction

/**
 * 设置管理员权限
 */
public data class SetOperatorEvent(
    override val action: OneBotAction,
    val event: RawSetOperatorEvent
) : OneBotEvent

/**
 * 取消管理员权限
 */
public data class UnsetOperatorEvent(
    override val action: OneBotAction,
    val event: RawUnsetOperatorEvent
) : OneBotEvent