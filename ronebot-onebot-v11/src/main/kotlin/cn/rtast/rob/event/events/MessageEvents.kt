/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/2/27
 */

package cn.rtast.rob.event.events

import cn.rtast.rob.entity.GroupMessage
import cn.rtast.rob.entity.PrivateMessage
import cn.rtast.rob.event.OneBotEvent
import cn.rtast.rob.onebot.OneBotAction

/**
 * 群聊事件
 */
public data class GroupMessageEvent(
    override val action: OneBotAction,
    val message: GroupMessage
) : OneBotEvent

/**
 * 私聊事件
 */
public data class PrivateMessageEvent(
    override val action: OneBotAction,
    val message: PrivateMessage
) : OneBotEvent
