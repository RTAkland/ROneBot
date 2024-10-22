/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/27
 */


package cn.rtast.rob.entity

import cn.rtast.rob.enums.UserSex
import com.google.gson.annotations.SerializedName

data class GroupMemberList(
    val data: List<Data>
) {
    data class Data(
        val age: Int,
        val area: String,
        val card: String?,
        @SerializedName("card_changeable")
        val cardChangeable: Boolean,
        @SerializedName("group_id")
        val groupId: Long,
        @SerializedName("join_time")
        val joinTime: Long,
        @SerializedName("last_sent_time")
        val lastSentTime: Long,
        val level: String,
        val nickname: String,
        val role: String,
        val sex: UserSex,
        val title: Any,
        @SerializedName("title_expire_time")
        val titleExpireTime: Long,
        val unfriendly: Boolean,
        @SerializedName("user_id")
        val userId: Long
    )
}