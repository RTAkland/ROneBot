/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/2/27
 */

package cn.rtast.rob.event.events

import cn.rtast.rob.entity.RawGroupRevokeMessage
import cn.rtast.rob.entity.RawPrivateRevokeMessage
import cn.rtast.rob.event.OneBotEvent
import cn.rtast.rob.onebot.OneBotAction

/**
 * 群聊消息撤回
 */
public data class GroupMessageRevokeEvent(
    override val action: OneBotAction,
    val event: RawGroupRevokeMessage
) : OneBotEvent

/**
 * 私聊消息撤回
 */
public data class PrivateMessageRevokeEvent(
    override val action: OneBotAction,
    val event: RawPrivateRevokeMessage
) : OneBotEvent