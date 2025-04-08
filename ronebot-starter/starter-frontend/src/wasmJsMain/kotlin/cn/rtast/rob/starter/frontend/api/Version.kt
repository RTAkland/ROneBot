/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 2025/4/8 12:16
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */


package cn.rtast.rob.starter.frontend.api

import kotlinx.serialization.Serializable

@Serializable
public data class Version(
    val version: String
)