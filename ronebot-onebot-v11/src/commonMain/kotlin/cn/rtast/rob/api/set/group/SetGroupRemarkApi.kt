/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/30
 */

package cn.rtast.rob.api.set.group

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 设置群聊备注
 * llonebot
 */
@Serializable
internal data class SetGroupRemarkApi(
    val action: String = "set_group_remark",
    val params: Params
) {
    @Serializable
    data class Params(
        @SerialName("group_id")
        val groupId: Long,
        val remark: String?
    )
}