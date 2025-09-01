/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/22/25, 12:16 AM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.milky.event.ws.packed

import cn.rtast.rob.milky.actionable.CommonGroupEventActionable
import cn.rtast.rob.milky.event.MilkyEvent
import cn.rtast.rob.milky.event.ws.raw.RawGroupNameChangeEvent
import cn.rtast.rob.milky.milky.MilkyAction

/**
 * 群名称变更
 */
public data class GroupNameChangeEvent(
    override val action: MilkyAction,
    val event: RawGroupNameChangeEvent.GroupNameChange
) : MilkyEvent, CommonGroupEventActionable by event