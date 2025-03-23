/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/9/25
 */


package cn.rtast.rob.api.set

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class FriendPokeApi(
    val action: String = "friend_poke",
    val params: Params
) {
    @Serializable
    data class Params(
        @SerialName("user_id")
        val userId: Long,
    )
}

@Serializable
internal data class GroupPokeApi(
    val action: String = "group_poke",
    val params: Params
) {
    @Serializable
    data class Params(
        @SerialName("group_id")
        val groupId: Long,
        @SerialName("user_id")
        val userId: Long,
    )
}