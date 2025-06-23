/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 6/23/25, 4:36 AM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

@file:OptIn(ExperimentalUuidApi::class)

package cn.rtast.rob.api.set

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Serializable
internal data class DownloadFileApi(
    val params: Params,
    val action: String = "download_file",
    val echo: Uuid
) {
    @Serializable
    data class Params(
        val url: String,
        @SerialName("thread_count")
        val threadCount: Int?,
        val headers: String?
    )
}