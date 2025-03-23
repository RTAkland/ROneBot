/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/28
 */


package cn.rtast.rob.api.set

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class DeleteFriendApi(
    val params: Params,
    val action: String = "delete_friend"
) {
    @Serializable
    data class Params(
        @SerialName("user_id")
        val userId: Long,
        // 是否屏蔽用户
        val block: Boolean
    )
}