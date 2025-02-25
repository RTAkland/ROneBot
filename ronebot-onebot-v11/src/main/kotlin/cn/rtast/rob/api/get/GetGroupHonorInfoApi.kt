/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/2
 */


package cn.rtast.rob.api.get

import com.google.gson.annotations.SerializedName
import java.util.*

internal data class GetGroupHonorInfoApi(
    val action: String = "get_group_honor_info",
    val echo: UUID,
    val params: Params
) {
    data class Params(
        @SerializedName("group_id")
        val groupId: Long,
        val type: String
    )
}