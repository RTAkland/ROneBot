/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/28
 */

package cn.rtast.rob.api.get

import kotlinx.serialization.Serializable
import kotlin.uuid.Uuid

@Serializable
internal data class GetGroupRequestsApi(
    val echo: Uuid,
    val action: String = "get_group_requests",
)