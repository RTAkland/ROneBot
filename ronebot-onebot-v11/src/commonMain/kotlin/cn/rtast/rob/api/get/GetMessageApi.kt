/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/26
 */


@file:OptIn(ExperimentalUuidApi::class)

package cn.rtast.rob.api.get

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Serializable
internal data class GetMessageApi(
    val action: String = "get_msg",
    val params: Params,
    val echo: Uuid
) {
    @Serializable
    data class Params(
        @SerialName("message_id")
        val groupId: Long,
    )
}