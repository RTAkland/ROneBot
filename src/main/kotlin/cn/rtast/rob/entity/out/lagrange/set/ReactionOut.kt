/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/1
 */


package cn.rtast.rob.entity.out.lagrange.set

import com.google.gson.annotations.SerializedName

internal data class ReactionOut(
    val action: String = "set_group_reaction",
    val params: Params
) {
    data class Params(
        @SerializedName("group_id")
        val groupId: Long,
        @SerializedName("message_id")
        val messageId: Long,
        val code: String,
        @SerializedName("is_add")
        val isAdd: Boolean,
    )
}