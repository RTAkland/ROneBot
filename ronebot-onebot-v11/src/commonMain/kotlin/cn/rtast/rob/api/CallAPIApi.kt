/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/22
 */

package cn.rtast.rob.api

import kotlinx.serialization.Serializable
import kotlin.uuid.Uuid

@Serializable
internal data class CallAPIApi(
    val action: String,
    val params: Map<String, String>,
    val echo: Uuid,
)