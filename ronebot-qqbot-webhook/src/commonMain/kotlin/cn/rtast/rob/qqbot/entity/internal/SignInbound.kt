/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2025/03/31
 */

package cn.rtast.rob.qqbot.entity.internal

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class SignInbound(
    val d: Body,
    val op: Int
) {
    @Serializable
    data class Body(
        @SerialName("plain_token")
        val plainToken: String,
        @SerialName("event_ts")
        val eventTs: String,
    )
}