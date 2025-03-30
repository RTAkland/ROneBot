/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/30
 */

package cn.rtast.rob.event.raw

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class GetFileResponse(
    val data: GetFileResponseInfo
) {
    @Serializable
    public data class GetFileResponseInfo(
        val file: String,
        val url: String,
        @SerialName("file_size")
        val fileSize: String,
        @SerialName("file_name")
        val filename: String,
        // 需要在llonebot中主动开启
        val base64: String? = null
    )
}