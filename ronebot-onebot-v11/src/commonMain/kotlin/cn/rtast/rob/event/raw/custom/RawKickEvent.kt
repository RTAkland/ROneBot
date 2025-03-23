/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/24
 */


package cn.rtast.rob.event.raw.custom

import cn.rtast.rob.onebot.OneBotAction
import kotlinx.serialization.SerialName

public data class RawBotBeKickEvent(
    @SerialName("group_id")
    val groupId: Long,
    val operator: Long,
    val time: Long,
    @SerialName("user_id")
    val userId: Long,
    val action: OneBotAction
)

public data class RawMemberKickEvent(
    @SerialName("group_id")
    val groupId: Long,
    val operator: Long,
    val time: Long,
    @SerialName("user_id")
    val userId: Long,
    val action: OneBotAction
)