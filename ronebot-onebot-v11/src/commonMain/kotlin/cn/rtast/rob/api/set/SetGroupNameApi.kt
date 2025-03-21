/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/26
 */


package cn.rtast.rob.api.set

import com.google.gson.annotations.SerializedName

internal data class SetGroupNameApi(
    val action: String = "set_group_name",
    val params: Params,
) {
    data class Params(
        @SerializedName("group_id")
        val groupId: Long,
        @SerializedName("group_name")
        val groupName: String,
    )
}