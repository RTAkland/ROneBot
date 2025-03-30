/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/30
 */

package cn.rtast.rob.api.set

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 移动好友到分组
 * llonebot
 */
@Serializable
internal data class SetFriendCategoryApi(
    val action: String = "set_friend_category",
    val params: Params
) {
    @Serializable
    data class Params(
        @SerialName("user_id")
        val userId: Long,
        @SerialName("category_id")
        val categoryId: String
    )
}