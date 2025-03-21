/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/27
 */


package cn.rtast.rob.api.get

import com.google.gson.annotations.SerializedName
import java.util.*

internal data class GetGroupMemberListApi(
    val action: String = "get_group_member_list",
    val params: Params,
    val echo: UUID
) {
    data class Params(
        @SerializedName("group_id")
        val groupId: Long,
    )
}