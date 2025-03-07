/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/7
 */

package cn.rtast.rob.kook.webhook.entity

import com.google.gson.annotations.SerializedName

public data class VerifyToken(
    val d: Body
) {
    public data class Body(
        @SerializedName("verify_token")
        val verifyToken: String
    )
}