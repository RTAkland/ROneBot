/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/2/27
 */

package cn.rtast.rob.event.packed

import cn.rtast.rob.actionable.RequestEventActionable
import cn.rtast.rob.event.OneBotEvent
import cn.rtast.rob.event.raw.request.JoinGroupRequestEvent
import cn.rtast.rob.onebot.OneBotAction

/**
 * 请求加群事件
 */
public data class RequestJoinGroupEvent(
    override val action: OneBotAction,
    val event: JoinGroupRequestEvent
) : OneBotEvent, RequestEventActionable by event