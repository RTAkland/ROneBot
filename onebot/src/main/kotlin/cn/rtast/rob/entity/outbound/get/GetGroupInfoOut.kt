/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/27
 */


package cn.rtast.rob.entity.outbound.get

import com.google.gson.annotations.SerializedName
import java.util.UUID

internal data class GetGroupInfoOut(
    val action: String = "get_group_info",
    val params: Params,
    val echo: UUID
) {
    data class Params(
        @SerializedName("group_id")
        val groupId: Long,
        @SerializedName("no_cache")
        val noCache: Boolean,
    )
}