/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/2/27
 */

package cn.rtast.rob.event.packed

import cn.rtast.rob.actionable.GroupMessageActionable
import cn.rtast.rob.actionable.MessageActionable
import cn.rtast.rob.event.OneBotEvent
import cn.rtast.rob.event.raw.message.GroupMessage
import cn.rtast.rob.event.raw.message.PrivateMessage
import cn.rtast.rob.onebot.OneBotAction

/**
 * 群聊事件
 */
public data class GroupMessageEvent(
    override val action: OneBotAction,
    val message: GroupMessage
) : OneBotEvent, GroupMessageActionable by message

/**
 * 私聊事件
 */
public data class PrivateMessageEvent(
    override val action: OneBotAction,
    val message: PrivateMessage
) : OneBotEvent, MessageActionable by message
