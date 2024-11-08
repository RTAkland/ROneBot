/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/28
 */


package cn.rtast.rob.entity.outbound.llonebot

import com.google.gson.annotations.SerializedName

data class GroupIgnoreAddRequest(
    val data: List<Request>
) {
    data class Request(
        @SerializedName("group_id")
        val groupId: Long,
        @SerializedName("user_id")
        val userId: Long,
        val flag: String
    )
}