/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/2/27
 */

package cn.rtast.rob.event.events

import cn.rtast.rob.entity.custom.ISetOperatorEvent
import cn.rtast.rob.entity.custom.IUnsetOperatorEvent
import cn.rtast.rob.event.OneBotEvent
import cn.rtast.rob.onebot.OneBotAction

/**
 * 设置管理员权限
 */
data class SetOperatorEvent(
    override val action: OneBotAction,
    val event: ISetOperatorEvent
) : OneBotEvent

/**
 * 取消管理员权限
 */
data class UnsetOperatorEvent(
    override val action: OneBotAction,
    val event: IUnsetOperatorEvent
) : OneBotEvent