/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/12
 */


package cn.rtast.rob.qqbot.legacy.entity.inbound

import com.google.gson.annotations.SerializedName

data class GetAccessToken(
    @SerializedName("access_token")
    val accessToken: String,
)