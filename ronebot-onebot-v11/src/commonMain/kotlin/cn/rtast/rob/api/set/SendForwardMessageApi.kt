/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/9/23
 */


@file:OptIn(ExperimentalUuidApi::class)

package cn.rtast.rob.api.set

import cn.rtast.rob.segment.InternalBaseSegment
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Serializable
internal data class SendGroupForwardMsgApi(
    val params: Params,
    val action: String = "send_group_forward_msg",
    val echo: Uuid,
) {
    @Serializable
    data class Params(
        @SerialName("group_id")
        val groupId: Long,
        val messages: List<InternalBaseSegment>
    )
}

@Serializable
internal data class SendPrivateForwardMsgApi(
    val params: Params,
    val action: String = "send_private_forward_msg",
    val echo: Uuid
) {
    @Serializable
    data class Params(
        @SerialName("user_id")
        val userId: Long,
        val messages: List<InternalBaseSegment>
    )
}