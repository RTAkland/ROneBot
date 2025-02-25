/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/2/3
 */


package cn.rtast.rob.entity.lagrange

import cn.rtast.rob.enums.RKeyType
import com.google.gson.annotations.SerializedName

data class GetRKey(
    val data: RKeys
) {
    data class RKeys(
        @SerializedName("rkeys")
        val rKeys: List<RKey>,
    )

    data class RKey(
        val type: RKeyType,
        @SerializedName("rkey")
        val rKey: String,
        @SerializedName("create_at")
        val createAt: Long,
        val ttl: Long,
    )
}