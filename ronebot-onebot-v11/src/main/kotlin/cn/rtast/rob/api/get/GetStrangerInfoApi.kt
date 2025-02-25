/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/27
 */


package cn.rtast.rob.api.get

import com.google.gson.annotations.SerializedName
import java.util.*

internal data class GetStrangerInfoApi(
    val action: String = "get_stranger_info",
    val params: Params,
    val echo: UUID
) {
    data class Params(
        @SerializedName("user_id")
        val userId: Long,
        @SerializedName("no_cache")
        val noCache: Boolean,
    )
}