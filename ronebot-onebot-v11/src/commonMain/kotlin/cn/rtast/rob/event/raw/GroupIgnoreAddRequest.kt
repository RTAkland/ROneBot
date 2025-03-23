/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/28
 */


package cn.rtast.rob.event.raw

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/**
 * 已过滤的加群请求
 */
@Serializable
public data class GroupIgnoreAddRequest(
    val data: List<Request>
) {
    @Serializable
    public data class Request(
        /**
         * 群号
         */
        @SerialName("group_id")
        val groupId: Long,
        /**
         * 用户QQ号
         */
        @SerialName("user_id")
        val userId: Long,
        /**
         * flag作为本次请求的ID
         */
        val flag: String
    )
}