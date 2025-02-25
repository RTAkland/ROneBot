/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/22
 */

package cn.rtast.rob.api

import java.util.UUID

internal data class CallAPIApi(
    val action: String,
    val params: Map<String, Any>,
    val echo: UUID
)