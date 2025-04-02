/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/28
 */

@file:Suppress("EnumEntryName")

package cn.rtast.rob.event.raw.group

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class GetGroupRequests(
    val data: List<GroupRequests>
) {
    @Serializable
    public data class GroupRequests(
        @SerialName("sub_type")
        val type: GroupRequestsType? = null,
        @SerialName("user_id")
        val userId: Long,
        @SerialName("group_id")
        val groupId: Long,
        @SerialName("invitor_id")
        val invitorId: Long,
        val comment: String,
        val flag: String,
        val time: Long,
        @SerialName("self_id")
        val selfId: Long
    )
}

public enum class GroupRequestsType {
    add, invite
}