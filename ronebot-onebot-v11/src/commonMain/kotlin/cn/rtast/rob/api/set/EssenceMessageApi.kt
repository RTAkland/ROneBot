/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/3
 */


@file:OptIn(ExperimentalUuidApi::class)

package cn.rtast.rob.api.set

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 * 删除群精华消息
 */
@Serializable
internal data class DeleteEssenceMessageApi(
    val action: String = "delete_essence_msg",
    val params: Params
) {
    @Serializable
    data class Params(
        @SerialName("message_id")
        val messageId: Long
    )
}

/**
 * 设置群精华消息
 */
@Serializable
internal data class SetEssenceMessageApi(
    val action: String = "set_essence_msg",
    val params: Params
) {
    @Serializable
    data class Params(
        @SerialName("message_id")
        val messageId: Long,
    )
}

/**
 * 获取群精华消息
 */
@Serializable
internal data class GetEssenceMessageListApi(
    val action: String = "get_essence_msg_list",
    val echo: Uuid,
    val params: Params
) {
    @Serializable
    data class Params(
        @SerialName("group_id")
        val groupId: Long,
    )
}

