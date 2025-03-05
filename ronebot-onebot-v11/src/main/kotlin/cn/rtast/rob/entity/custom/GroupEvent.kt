/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/24
 */


package cn.rtast.rob.entity.custom

import cn.rtast.rob.onebot.OneBotAction
import com.google.gson.annotations.SerializedName

public data class IMemberBeInviteEvent(
    @SerializedName("group_id")
    val groupId: Long,
    @SerializedName("user_id")
    val userId: Long,
    val operator: Long,
    val time: Long,
    val action: OneBotAction
)

public data class IJoinRequestApproveEvent(
    @SerializedName("group_id")
    val groupId: Long,
    @SerializedName("user_id")
    val userId: Long,
    val operator: Long,
    val time: Long,
    val action: OneBotAction
)

public data class IGroupMemberLeaveEvent(
    @SerializedName("group_id")
    val groupId: Long,
    @SerializedName("user_id")
    val userId: Long,
    val operator: Long,
    val time: Long,
    val action: OneBotAction
)