/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/26
 */


package cn.rtast.rob.api.get

import com.google.gson.annotations.SerializedName
import java.util.*

internal data class GetMessageApi(
    val action: String = "get_msg",
    val params: Params,
    val echo: UUID
) {
    data class Params(
        @SerializedName("message_id")
        val groupId: Long,
    )
}