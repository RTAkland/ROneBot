/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/1
 */


package cn.rtast.rob.entity.out.lagrange

import com.google.gson.annotations.SerializedName

internal data class SetEssenceMessageOut(
    val action: String ="set_essence_msg",
    val params: Params
) {
    data class Params(
        @SerializedName("message_id")
        val messageId: Long,
    )
}