/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/1
 */


package cn.rtast.rob.api.set

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class ReactionApi(
    val action: String = "set_group_reaction",
    val params: Params
) {
    @Serializable
    data class Params(
        @SerialName("group_id")
        val groupId: Long,
        @SerialName("message_id")
        val messageId: Long,
        val code: String,
        @SerialName("is_add")
        val isAdd: Boolean,
    )
}