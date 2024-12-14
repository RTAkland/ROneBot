/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/22
 */


package cn.rtast.rob.entity.outbound.lagrange.set

import com.google.gson.annotations.SerializedName
import java.util.*

internal data class SetGroupAvatarOut(
    val params: Params,
    val action: String = "set_group_portrait",
    val echo: UUID
) {
    data class Params(
        @SerializedName("group_id")
        val groupId: Long,
        val file: String
    )
}

internal data class SetGroupAvatar(val status: String)