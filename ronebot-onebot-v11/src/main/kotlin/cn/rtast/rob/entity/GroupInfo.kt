/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/28
 */


package cn.rtast.rob.entity

import com.google.gson.annotations.SerializedName

/**
 * 群信息
 */
data class GroupInfo(
    val data: GroupInfo,
) {
    data class GroupInfo(
        /**
         * 群号
         */
        @SerializedName("group_id")
        val groupId: Long,
        /**
         * 群名字
         */
        @SerializedName("group_name")
        val groupName: String,
        /**
         * 成员数量
         */
        @SerializedName("member_count")
        val memberCount: Int,
        /**
         * 最大成员数量
         */
        @SerializedName("max_member_count")
        val maxMemberCount: Int,
    )
}