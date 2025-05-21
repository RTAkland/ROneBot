/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/22/25, 12:30 AM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.milky.event.ws.packed

import cn.rtast.rob.milky.event.MilkyEvent
import cn.rtast.rob.milky.event.ws.raw.RawGroupNudgeEvent
import cn.rtast.rob.milky.milky.MilkyAction

/**
 * 群戳一戳
 */
public data class GroupNudgeEvent(
    override val action: MilkyAction,
    val event: RawGroupNudgeEvent.GroupNudge
) : MilkyEvent