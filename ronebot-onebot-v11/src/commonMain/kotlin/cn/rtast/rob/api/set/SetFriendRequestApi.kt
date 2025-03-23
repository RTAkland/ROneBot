/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/26
 */


package cn.rtast.rob.api.set

import kotlinx.serialization.Serializable

@Serializable
internal data class SetFriendRequestApi(
    val action: String = "set_friend_add_request",
    val params: Params,
) {
    @Serializable
    data class Params(
        val flag: String,
        val approve: Boolean,
        val remark: String,
    )
}