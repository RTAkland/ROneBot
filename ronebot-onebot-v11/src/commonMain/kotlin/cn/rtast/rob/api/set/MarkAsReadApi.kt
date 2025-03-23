/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/1
 */


package cn.rtast.rob.api.set

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class MarkAsReadApi(
    val action: String = "mark_msg_as_read",
    val params: Params
) {
    @Serializable
    data class Params(
        @SerialName("message_id")
        val messageId: Long,
    )
}