/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/26
 */


package cn.rtast.rob.api.set.group

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class KickGroupMemberApi(
    val action: String = "set_group_kick",
    val params: Params,
) {
    @Serializable
    data class Params(
        @SerialName("group_id")
        val groupId: Long,
        @SerialName("user_id")
        val userId: Long,
        @SerialName("reject_add_request")
        val rejectAddRequest: Boolean,
    )
}