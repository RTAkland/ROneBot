/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/2/27
 */

package cn.rtast.rob.event.events

import cn.rtast.rob.entity.custom.IGroupMemberLeaveEvent
import cn.rtast.rob.entity.custom.IJoinRequestApproveEvent
import cn.rtast.rob.entity.custom.IMemberBeInviteEvent
import cn.rtast.rob.event.OneBotEvent
import cn.rtast.rob.onebot.OneBotAction

/**
 * 成员退出群聊
 */
data class GroupMemberLeaveEvent(
    override val action: OneBotAction,
    val event: IGroupMemberLeaveEvent
) : OneBotEvent

/**
 * 被群聊邀请
 */
data class GroupBeInviteEvent(
    override val action: OneBotAction,
    val event: IMemberBeInviteEvent
) : OneBotEvent

/**
 * 管理员同意成员加群请求
 */
data class GroupMemberApproveEvent(
    override val action: OneBotAction,
    val event: IJoinRequestApproveEvent
) : OneBotEvent
