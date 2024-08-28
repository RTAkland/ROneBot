/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/27
 */


package cn.rtast.rob.entity.out

import cn.rtast.rob.enums.HonorType
import com.google.gson.annotations.SerializedName

internal data class GetGroupHonorInfoOut(
    val action: String = "get_group_honor_info",
    val params: Params,
) {
    data class Params(
        @SerializedName("group_id")
        val groupId: Long,
        val type: HonorType,
    )
}