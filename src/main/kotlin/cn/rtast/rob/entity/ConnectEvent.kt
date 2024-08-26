/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/26
 */


package cn.rtast.rob.entity

import com.google.gson.annotations.SerializedName

data class ConnectEvent(
    @SerializedName("self_id")
    var selfId: String,
    val time: Long,
)