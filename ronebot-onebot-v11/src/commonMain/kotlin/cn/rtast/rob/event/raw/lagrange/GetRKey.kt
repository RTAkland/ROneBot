/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/2/3
 */


package cn.rtast.rob.event.raw.lagrange

import cn.rtast.rob.enums.RKeyType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class GetRKey(
    val data: RKeys
) {
    @Serializable
    public data class RKeys(
        @SerialName("rkeys")
        val rKeys: List<RKey>,
    )

    @Serializable
    public data class RKey(
        val type: RKeyType,
        @SerialName("rkey")
        val rKey: String,
        @SerialName("create_at")
        val createAt: Long,
        val ttl: Long,
    )
}