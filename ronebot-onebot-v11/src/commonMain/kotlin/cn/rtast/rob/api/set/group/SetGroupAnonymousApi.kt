/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/26
 */


package cn.rtast.rob.api.set.group

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class SetGroupAnonymousApi(
    val action: String = "set_group_anonymous",
    val params: Params,
) {
    @Serializable
    data class Params(
        @SerialName("group_id")
        val groupId: Long,
        val enable: Boolean
    )
}