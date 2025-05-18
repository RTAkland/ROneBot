/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/18/25, 10:58 PM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.milky.event.ws.packet

import cn.rtast.rob.milky.event.MilkyEvent
import cn.rtast.rob.milky.milky.MilkyAction

/**
 * websocket服务器下发的原始消息
 */
public data class RawMessageEvent(
    override val action: MilkyAction,
    val message: String
): MilkyEvent