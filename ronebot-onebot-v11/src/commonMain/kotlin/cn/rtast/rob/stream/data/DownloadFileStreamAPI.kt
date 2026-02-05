/*
 * Copyright © 2025 RTAkland
 * Date: 10/5/25, 4:48 AM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */


@file:OptIn(ExperimentalUuidApi::class)

package cn.rtast.rob.stream.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Serializable
internal data class DownloadFileStreamAPI(
    val action: String = "download_file_stream",
    val params: Params,
    val echo: Uuid,
) {
    @Serializable
    data class Params(
        val file: String,
        @SerialName("file_id")
        val fileId: String?,
        @SerialName("chunk_size")
        val chunkSize: Long = 64 * 1024
    )
}