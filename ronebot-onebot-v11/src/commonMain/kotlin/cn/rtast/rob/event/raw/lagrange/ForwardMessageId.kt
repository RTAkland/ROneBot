/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/9/23
 */


package cn.rtast.rob.event.raw.lagrange

import com.google.gson.annotations.SerializedName

public data class ForwardMessageId(
    val data: ForwardMessageId
) {
    public data class ForwardMessageId(
        @SerializedName("message_id")
        val messageId: String,
        @SerializedName("forward_id")
        val forwardId: String,
    )
}