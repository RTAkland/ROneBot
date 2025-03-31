/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/13
 */


package cn.rtast.rob.qqbot.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class SignOutbound(
    @SerialName("plain_token")
    val plainToken: String,
    val signature: String
)