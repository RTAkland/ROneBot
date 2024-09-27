/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/26
 */


package cn.rtast.rob.entity.out.lagrange

import com.google.gson.annotations.SerializedName

internal data class SetGroupMemberTitleOut(
    val action: String = "set_group_special_title",
    val params: Params,
) {
    data class Params(
        @SerializedName("group_id")
        val groupId: Long,
        @SerializedName("user_id")
        val userId: Long,
        @SerializedName("special_title")
        val specialTitle: String,
        val duration: Int
    )
}