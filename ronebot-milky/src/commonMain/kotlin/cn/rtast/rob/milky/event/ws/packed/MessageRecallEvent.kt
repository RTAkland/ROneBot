/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/21/25, 11:25 PM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.milky.event.ws.packed

import cn.rtast.rob.milky.event.MilkyEvent
import cn.rtast.rob.milky.event.ws.raw.RawMessageRecallEvent
import cn.rtast.rob.milky.milky.MilkyAction

/**
 * 撤回消息
 */
public data class MessageRecallEvent(
    override val action: MilkyAction,
    val event: RawMessageRecallEvent.MessageRecall
) : MilkyEvent