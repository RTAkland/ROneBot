/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/26
 */


package cn.rtast.rob.api.set.message

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class RevokeMessageApi(
    val action: String = "delete_msg",
    val params: Params,
) {
    @Serializable
    data class Params(
        @SerialName("message_id")
        val messageId: Long,
    )
}