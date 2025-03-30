/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/30
 */

@file:OptIn(ExperimentalUuidApi::class)

package cn.rtast.rob.api.get

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 * 获取文件下载地址和base64
 * llonebot
 */
@Serializable
internal data class GetFileApi(
    val action: String = "get_file",
    val params: Params,
    val echo: Uuid
) {
    @Serializable
    data class Params(
        @SerialName("file_id")
        val fileId: String
    )
}