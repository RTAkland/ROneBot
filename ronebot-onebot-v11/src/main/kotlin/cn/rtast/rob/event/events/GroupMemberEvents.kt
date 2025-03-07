/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/2/27
 */

package cn.rtast.rob.event.events

import cn.rtast.rob.entity.custom.RawGroupMemberLeaveEvent
import cn.rtast.rob.entity.custom.RawJoinRequestApproveEvent
import cn.rtast.rob.entity.custom.RawMemberBeInviteEvent
import cn.rtast.rob.event.OneBotEvent
import cn.rtast.rob.onebot.OneBotAction

/**
 * 成员退出群聊
 */
public data class GroupMemberLeaveEvent(
    override val action: OneBotAction,
    val event: RawGroupMemberLeaveEvent
) : OneBotEvent

/**
 * 被群聊邀请
 */
public data class GroupBeInviteEvent(
    override val action: OneBotAction,
    val event: RawMemberBeInviteEvent
) : OneBotEvent

/**
 * 管理员同意成员加群请求
 */
public data class GroupMemberApproveEvent(
    override val action: OneBotAction,
    val event: RawJoinRequestApproveEvent
) : OneBotEvent
