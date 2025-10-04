/*
 * Copyright © 2025 RTAkland
 * Date: 10/5/25, 4:47 AM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */


@file:OptIn(ExperimentalUuidApi::class)

package cn.rtast.rob.onebot.stream.data

import kotlinx.serialization.Serializable
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Serializable
internal data class TestDownloadStreamAPI(
    val action: String = "test_download_stream",
    val echo: Uuid,
)