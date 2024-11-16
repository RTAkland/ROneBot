/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/16
 */


package cn.rtast.rob.qqbot.entity.internal

import com.google.gson.annotations.SerializedName

internal data class GetAccessTokenPayload(
    val appId: String,
    val clientSecret: String,
)

internal data class GetAccessTokenResponse(
    @SerializedName("access_token")
    val accessToken: String,
)