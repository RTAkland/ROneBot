/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/9/23
 */


package cn.rtast.rob.event.raw.lagrange

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class ForwardMessageId(
    val data: ForwardMessageId
) {
    @Serializable
    public data class ForwardMessageId(
        @SerialName("message_id")
        val messageId: String,
        @SerialName("forward_id")
        val forwardId: String,
    )
}