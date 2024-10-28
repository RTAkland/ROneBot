/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/28
 */


package cn.rtast.rob.entity.out.gocq

import com.google.gson.annotations.SerializedName

internal data class DeleteFriendOut(
    val params: Params,
    val action: String = "delete_friend"
) {
    data class Params(
        @SerializedName("user_id")
        val userId: Long,
    )
}