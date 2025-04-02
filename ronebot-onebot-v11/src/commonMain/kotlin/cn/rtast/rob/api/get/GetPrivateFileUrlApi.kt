/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/4/2
 */

@file:OptIn(ExperimentalUuidApi::class)

package cn.rtast.rob.api.get

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 * 获取私聊的文件
 */
@Serializable
internal data class GetPrivateFileUrlApi(
    val action: String = "get_private_file_url",
    val param: Params,
    val echo: Uuid,
) {
    @Serializable
    data class Params(
        @SerialName("user_id")
        val userId: Long,
        @SerialName("file_hash")
        val fileHash: String,
        @SerialName("file_id")
        val fileId: String
    )
}