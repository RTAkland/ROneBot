/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/27
 */


package cn.rtast.rob.entity.out

import com.google.gson.annotations.SerializedName

internal data class GetStrangerInfoOut(
    val action: String = "get_stranger_info",
    val params: Params,
) {
    data class Params(
        @SerializedName("user_id")
        val userId: Long,
        @SerializedName("no_cache")
        val noCache: Boolean,
    )
}