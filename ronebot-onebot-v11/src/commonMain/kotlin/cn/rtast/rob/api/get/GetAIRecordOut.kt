/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/3
 */


@file:OptIn(ExperimentalUuidApi::class)

package cn.rtast.rob.api.get

import cn.rtast.rob.enums.internal.ActionStatus
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Serializable
internal data class GetAIRecordAndSendRecordApi(
    val params: Params,
    val echo: Uuid,
    val action: String
) {
    @Serializable
    data class Params(
        @SerialName("chat_type")
        val chatType: UInt,
        val text: String,
        @SerialName("group_id")
        val groupId: Long,
        val character: String,
    )
}

@Serializable
internal data class AIRecord(
    val status: ActionStatus,
    val data: String
)