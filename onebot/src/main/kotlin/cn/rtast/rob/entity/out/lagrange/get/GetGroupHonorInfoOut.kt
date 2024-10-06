/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/2
 */


package cn.rtast.rob.entity.out.lagrange.get

import cn.rtast.rob.enums.internal.MessageEchoType
import com.google.gson.annotations.SerializedName

internal data class GetGroupHonorInfoOut(
    val action: String = "get_group_honor_info",
    val echo: MessageEchoType = MessageEchoType.GetGroupHonorInfo,
    val params: Params
) {
    data class Params(
        @SerializedName("group_id")
        val groupId: Long,
        val type: String
    )
}