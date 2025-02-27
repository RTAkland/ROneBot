/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/2/27
 */

package cn.rtast.rob.event.events

import cn.rtast.rob.entity.AddFriendRequestEvent
import cn.rtast.rob.event.OneBotEvent
import cn.rtast.rob.onebot.OneBotAction

/**
 * 加好友事件
 */
data class AddFriendEvent(
    override val action: OneBotAction,
    val event: AddFriendRequestEvent
) : OneBotEvent