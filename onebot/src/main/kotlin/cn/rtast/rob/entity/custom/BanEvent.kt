/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/24
 */


package cn.rtast.rob.entity.custom

import cn.rtast.rob.util.ob.OneBotAction
import com.google.gson.annotations.SerializedName

data class BanEvent(
    @SerializedName("group_id")
    val groupId: Long,
    val operator: Long,
    val duration: Int,
    val time: Long,
    @SerializedName("user_id")
    val userId: Long,
    val action: OneBotAction
)

data class PardonEvent(
    @SerializedName("group_id")
    val groupId: Long,
    val operator: Long,
    val duration: Int,
    val time: Long,
    @SerializedName("user_id")
    val userId: Long,
    val action: OneBotAction
)