/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 9/16/25, 12:43 AM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */


package cn.rtast.rob.starter.frontend.entity

import kotlinx.serialization.Serializable

@Serializable
public data class FrontendConfig(
    val backend: String
)