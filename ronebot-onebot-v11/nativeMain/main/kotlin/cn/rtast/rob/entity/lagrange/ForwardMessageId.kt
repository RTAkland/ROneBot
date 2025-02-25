/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/9/23
 */


package cn.rtast.rob.entity.lagrange

import com.google.gson.annotations.SerializedName

data class ForwardMessageId(
    val data: ForwardMessageId
) {
    data class ForwardMessageId(
        @SerializedName("message_id")
        val messageId: String,
        @SerializedName("forward_id")
        val forwardId: String,
    )
}