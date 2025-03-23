/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/26
 */


package cn.rtast.rob.api.set

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class SendLikeApi(
    val action: String = "send_like",
    val params: Params,
) {
    @Serializable
    data class Params(
        @SerialName("user_id")
        val userId: Long,
        val times: Int,
    )
}