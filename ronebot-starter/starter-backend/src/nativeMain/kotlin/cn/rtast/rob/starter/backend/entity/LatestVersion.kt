/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/11
 */

package cn.rtast.rob.starter.backend.entity

import kotlinx.serialization.Serializable

@Serializable
data class LatestVersion(
    val version: String
)