/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/24
 */


package cn.rtast.rob.event.raw.group

import cn.rtast.rob.onebot.OneBotAction
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
public data class RawMemberBeInviteEvent(
    @SerialName("group_id")
    val groupId: Long,
    @SerialName("user_id")
    val userId: Long,
    val operator: Long,
    val time: Long,
) {
    @Transient
    lateinit var action: OneBotAction
}

public data class RawJoinRequestApproveEvent(
    val groupId: Long,
    val userId: Long,
    val operator: Long,
    val time: Long,
    val action: OneBotAction
)

public data class RawGroupMemberLeaveEvent(
    val groupId: Long,
    val userId: Long,
    val operator: Long,
    val time: Long,
    val action: OneBotAction
)