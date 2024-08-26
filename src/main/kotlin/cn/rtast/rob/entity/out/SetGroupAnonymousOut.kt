/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/26
 */


package cn.rtast.rob.entity.out

import com.google.gson.annotations.SerializedName

data class SetGroupAnonymousOut(
    val action: String = "set_group_anonymous",
    val params: Params,
) {
    data class Params(
        @SerializedName("group_id")
        val groupId: Long,
        val enable: Boolean
    )
}