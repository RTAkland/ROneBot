/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/30
 */

package cn.rtast.rob.api.set

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 设置好友备注
 * llonebot
 */
@Serializable
internal data class SetFriendRemarkApi(
    val action: String = "set_friend_remark",
    val params: Params
) {
    @Serializable
    data class Params(
        @SerialName("user_id")
        val userId: Long,
        val remark: String?
    )
}