/*
 * Copyright © 2025 RTAkland
 * Date: 10/5/25, 4:47 AM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */


@file:OptIn(ExperimentalUuidApi::class)

package cn.rtast.rob.onebot.stream.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Serializable
internal data class UploadFileStreamAPI(
    val action: String = "upload_file_stream",
    val params: Params,
    val echo: Uuid,
) {
    @Serializable
    data class Params(
        @SerialName("stream_id")
        val streamId: Uuid,
        /**
         * chunk byte array base64
         */
        @SerialName("chunk_data")
        val chunkData: String,
        @SerialName("chunk_index")
        val chunkIndex: Int,
        @SerialName("total_chunks")
        val totalChunks: Int,
        @SerialName("file_size")
        val fileSize: Long,
        @SerialName("expected_sha256")
        val expectedSha256: String,
        @SerialName("filename")
        val filename: String,
        /**
         * 文件有效期
         */
        @SerialName("file_retention")
        val fileRetention: Long,
    )
}

@Serializable
internal data class CompleteStreamUploadAPI(
    val action: String = "upload_file_stream",
    val params: Params,
    val echo: Uuid
) {
    @Serializable
    data class Params(
        @SerialName("stream_id")
        val streamId: Uuid,
        @SerialName("is_complete")
        val isComplete: Boolean = true
    )
}