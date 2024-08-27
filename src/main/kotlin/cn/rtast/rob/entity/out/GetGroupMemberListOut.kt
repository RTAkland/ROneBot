/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/27
 */


package cn.rtast.rob.entity.out

import com.google.gson.annotations.SerializedName

data class GetGroupMemberListOut(
    val action: String = "get_group_member_list",
    val params: Params,
) {
    data class Params(
        @SerializedName("group_id")
        val groupId: Long,
    )
}