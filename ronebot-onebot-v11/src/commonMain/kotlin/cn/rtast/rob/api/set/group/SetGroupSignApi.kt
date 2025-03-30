/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/30
 */

package cn.rtast.rob.api.set.group

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 群打卡
 * Napcat
 */
@Serializable
internal data class SetGroupSignApi(
    val action: String = "set_group_sign",
    val params: Params
) {
    @Serializable
    data class Params(
        @SerialName("group_id")
        val groupId: Long
    )
}