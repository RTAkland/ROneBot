/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/9/25
 */


package cn.rtast.rob.api.set

import com.google.gson.annotations.SerializedName

internal data class FriendPokeApi(
    val action: String = "friend_poke",
    val params: Params
) {
    data class Params(
        @SerializedName("user_id")
        val userId: Long,
    )
}

internal data class GroupPokeApi(
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