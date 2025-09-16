/*
 * Copyright © 2025 RTAkland
 * Date: 9/16/25, 10:53 PM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */


package cn.rtast.rob.starter.common

import kotlinx.serialization.Serializable

@Serializable
public data class GeneratedFileResponse(
    val filename: String,
    val content: String,
    val path: String = ""
)