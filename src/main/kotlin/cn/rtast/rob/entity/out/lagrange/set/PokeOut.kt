/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/9/25
 */


package cn.rtast.rob.entity.out.lagrange.set

import com.google.gson.annotations.SerializedName

internal data class FriendPokeOut(
    val action: String = "friend_poke",
    val params: Params
) {
    data class Params(
        @SerializedName("user_id")
        val userId: Long,
    )
}

internal data class GroupPokeOut(
    val action: String = "group_poke",
    val params: Params
) {
    data class Params(
        @SerializedName("group_id")
        val groupId: Long,
        @SerializedName("user_id")
        val userId: Long,
    )
}