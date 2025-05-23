/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/24/25, 1:25 AM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.milky.event.ws.packed

import cn.rtast.rob.milky.event.MilkyEvent
import cn.rtast.rob.milky.event.common.Friend
import cn.rtast.rob.milky.event.ws.raw.RawMessageReceiveEvent
import cn.rtast.rob.milky.milky.MilkyAction

public data class PrivateMessageEvent(
    override val action: MilkyAction,
    val event: RawMessageReceiveEvent.IncomingMessage,
    val friend: Friend
) : MilkyEvent