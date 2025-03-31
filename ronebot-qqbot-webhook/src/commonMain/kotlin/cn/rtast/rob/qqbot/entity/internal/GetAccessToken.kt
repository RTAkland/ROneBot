/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/16
 */


package cn.rtast.rob.qqbot.entity.internal

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class GetAccessTokenPayload(
    val appId: String,
    val clientSecret: String,
)

@Serializable
internal data class GetAccessTokenResponse(
    @SerialName("access_token")
    val accessToken: String,
)