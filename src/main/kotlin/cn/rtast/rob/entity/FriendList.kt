/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/27
 */


package cn.rtast.rob.entity

import com.google.gson.annotations.SerializedName

data class FriendList(
    val data: List<Data>
) {
    data class Data(
        @SerializedName("user_id")
        val userId: Long,
        @SerializedName("q_id")
        val qId: String,
        val nickname: String,
        val remark: String,
        val group: Group
    )
    data class Group(
        @SerializedName("group_id")
        val groupId: Long,
        @SerializedName("group_name")
        val groupName: String,
    )
}