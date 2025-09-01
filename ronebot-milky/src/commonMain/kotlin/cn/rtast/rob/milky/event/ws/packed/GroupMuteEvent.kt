/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/22/25, 12:22 AM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.milky.event.ws.packed

import cn.rtast.rob.milky.actionable.CommonGroupEventActionable
import cn.rtast.rob.milky.event.MilkyEvent
import cn.rtast.rob.milky.event.ws.raw.RawGroupMuteEvent
import cn.rtast.rob.milky.milky.MilkyAction

/**
 * 成员禁言状态变更
 */
public data class GroupMuteEvent(
    override val action: MilkyAction,
    val event: RawGroupMuteEvent.GroupMute
) : MilkyEvent, CommonGroupEventActionable by event