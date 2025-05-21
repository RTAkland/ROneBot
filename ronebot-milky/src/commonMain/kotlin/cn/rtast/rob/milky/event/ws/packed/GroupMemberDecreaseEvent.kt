/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/22/25, 12:14 AM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.milky.event.ws.packed

import cn.rtast.rob.milky.event.MilkyEvent
import cn.rtast.rob.milky.event.ws.raw.RawGroupMemberDecreaseEvent
import cn.rtast.rob.milky.milky.MilkyAction

/**
 * 群成员减少
 */
public data class GroupMemberDecreaseEvent(
    override val action: MilkyAction,
    val event: RawGroupMemberDecreaseEvent.GroupMemberDecrease
) : MilkyEvent