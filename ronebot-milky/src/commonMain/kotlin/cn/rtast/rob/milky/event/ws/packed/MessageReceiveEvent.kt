/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/21/25, 11:24 PM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.milky.event.ws.packed

import cn.rtast.rob.milky.actionable.CommonGroupEventActionable
import cn.rtast.rob.milky.actionable.GroupEssenceActionable
import cn.rtast.rob.milky.actionable.MessageActionable
import cn.rtast.rob.milky.event.MilkyEvent
import cn.rtast.rob.milky.event.ws.raw.RawMessageReceiveEvent
import cn.rtast.rob.milky.milky.MilkyAction

/**
 * 接收消息
 */
public data class MessageReceiveEvent(
    override val action: MilkyAction,
    val event: RawMessageReceiveEvent.IncomingMessage,
) : MilkyEvent, MessageActionable by event,
    CommonGroupEventActionable by event,
    GroupEssenceActionable by event