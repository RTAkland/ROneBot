/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/13
 */


package cn.rtast.rob.qqbot.entity.outbound

import com.google.gson.annotations.SerializedName

internal data class SignOutbound(
    @SerializedName("plain_token")
    val plainToken: String,
    val signature: String
)