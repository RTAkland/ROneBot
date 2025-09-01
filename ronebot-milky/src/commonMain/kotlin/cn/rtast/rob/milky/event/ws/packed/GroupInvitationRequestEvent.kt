/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/21/25, 11:53 PM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.milky.event.ws.packed

import cn.rtast.rob.milky.actionable.CommonGroupEventActionable
import cn.rtast.rob.milky.actionable.RequestEventActionable
import cn.rtast.rob.milky.event.MilkyEvent
import cn.rtast.rob.milky.event.ws.raw.RawGroupInvitationRequestEvent
import cn.rtast.rob.milky.milky.MilkyAction

/**
 * 邀请自己进群
 */
public data class GroupInvitationRequestEvent(
    override val action: MilkyAction,
    val event: RawGroupInvitationRequestEvent.GroupInvitationRequest,
) : MilkyEvent, CommonGroupEventActionable by event,
    RequestEventActionable by event