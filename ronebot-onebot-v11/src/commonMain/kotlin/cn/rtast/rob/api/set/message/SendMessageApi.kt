/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/26
 */


@file:OptIn(ExperimentalUuidApi::class)

package cn.rtast.rob.api.set.message

import cn.rtast.rob.event.raw.ArrayMessage
import cn.rtast.rob.segment.InternalBaseSegment
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Serializable
internal data class CQCodeGroupMessageApi(
    val params: Params,
    val action: String = "send_group_msg",
    val echo: Uuid
) {
    @Serializable
    data class Params(
        @SerialName("group_id")
        val groupId: Long,
        val message: String,
    )
}

@Serializable
internal data class ArrayGroupMessageApi(
    val params: Params,
    val action: String = "send_group_msg",
    val echo: Uuid
) {
    @Serializable
    data class Params(
        @SerialName("group_id")
        val groupId: Long,
        val message: List<InternalBaseSegment>,
    )
}

@Serializable
internal data class RawArrayGroupMessageApi(
    val params: Params,
    val action: String = "send_group_msg",
    val echo: Uuid
) {
    @Serializable
    data class Params(
        @SerialName("group_id")
        val groupId: Long,
        val message: List<ArrayMessage>,
    )
}

@Serializable
internal data class CQCodePrivateMessageApi(
    val params: Params,
    val action: String = "send_private_msg",
    val echo: Uuid
) {
    @Serializable
    data class Params(
        @SerialName("user_id")
        val userId: Long,
        val message: String,
    )
}

@Serializable
internal data class ArrayPrivateMessageApi(
    val params: Params,
    val action: String = "send_private_msg",
    val echo: Uuid
) {
    @Serializable
    data class Params(
        @SerialName("user_id")
        val userId: Long,
        val message: List<InternalBaseSegment>,
    )
}

@Serializable
internal data class RawArrayPrivateMessageApi(
    val params: Params,
    val action: String = "send_private_msg",
    val echo: Uuid
) {
    @Serializable
    data class Params(
        @SerialName("user_id")
        val userId: Long,
        val message: List<ArrayMessage>,
    )
}