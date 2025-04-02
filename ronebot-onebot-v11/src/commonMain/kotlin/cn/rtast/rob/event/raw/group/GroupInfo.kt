/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/28
 */


package cn.rtast.rob.event.raw.group

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/**
 * 群信息
 */
@Serializable
public data class GroupInfo(
    val data: GroupInfo,
) {
    @Serializable
    public data class GroupInfo(
        /**
         * 群号
         */
        @SerialName("group_id")
        val groupId: Long,
        /**
         * 群名字
         */
        @SerialName("group_name")
        val groupName: String,
        /**
         * 成员数量
         */
        @SerialName("member_count")
        val memberCount: Int,
        /**
         * 最大成员数量
         */
        @SerialName("max_member_count")
        val maxMemberCount: Int,
    )
}