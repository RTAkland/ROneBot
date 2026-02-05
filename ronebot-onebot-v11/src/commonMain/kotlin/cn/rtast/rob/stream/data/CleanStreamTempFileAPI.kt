/*
 * Copyright © 2025 RTAkland
 * Date: 10/5/25, 3:40 AM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */


@file:OptIn(ExperimentalUuidApi::class)

package cn.rtast.rob.stream.data

import kotlinx.serialization.Serializable
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Serializable
internal data class CleanStreamTempFileAPI(
    val action: String = "clean_stream_temp_file",
    val echo: Uuid
)