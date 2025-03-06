/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/7
 */

package cn.rtast.rob.kook.webhook.validator.event

import com.google.gson.annotations.SerializedName

/**
 * 未被加密的Challenge消息体
 */
internal data class ChallengeEvent(
    val s: Int,
    val d: Body,
    val encrypt: String?
) {
    data class Body(
        val type: Int,
        @SerializedName("channel_type")
        val channelType: String,
        val challenge: String,
        @SerializedName("verify_token")
        val verifyToken: String
    )
}

/**
 * Challenge验证的回复消息
 */
internal data class ChallengeResponse(
    val challenge: String,
)