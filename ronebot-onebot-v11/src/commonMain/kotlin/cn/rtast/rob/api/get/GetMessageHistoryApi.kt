/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/3
 */


@file:OptIn(ExperimentalUuidApi::class)

package cn.rtast.rob.api.get

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Serializable
internal data class GetGroupMessageHistoryApi(
    val action: String = "get_group_msg_history",
    val echo: Uuid,
    val params: Params,
) {
    @Serializable
    data class Params(
        @SerialName("group_id")
        val groupId: Long,
        @SerialName("message_id")
        val messageId: Long,
        val count: Int
    )
}

@Serializable
internal data class GetPrivateMessageHistoryApi(
    val action: String = "get_friend_msg_history",
    val echo: Uuid,
    val params: Params,
) {
    @Serializable
    data class Params(
        @SerialName("user_id")
        val userId: Long,
        @SerialName("message_id")
        val messageId: Long,
        val count: Int
    )
}