/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/2
 */


package cn.rtast.rob.entity.out.lagrange.get

import com.google.gson.annotations.SerializedName
import java.util.UUID

internal data class GetGroupHonorInfoOut(
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