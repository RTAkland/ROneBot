/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/26
 */


package cn.rtast.rob.entity.out.set

import com.google.gson.annotations.SerializedName

internal data class SetGroupAdminOut(
    val action: String = "set_group_admin",
    val params: Params,
) {
    data class Params(
        @SerializedName("group_id")
        val groupId: Long,
        @SerializedName("user_id")
        val userId: Long,
        val enable: Boolean
    )
}