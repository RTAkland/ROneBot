/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/26
 */


package cn.rtast.rob.entity.out

data class SetFriendRequestOut(
    val action: String = "set_friend_add_request",
    val params: Params,
) {
    data class Params(
        val flag: String,
        val approve: Boolean,
        val remark: String,
    )
}