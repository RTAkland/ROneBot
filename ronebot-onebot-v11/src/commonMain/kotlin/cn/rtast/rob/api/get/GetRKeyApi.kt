/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/2/3
 */


package cn.rtast.rob.api.get

import java.util.*

internal data class GetRKeyApi(
    val action: String = "get_rkey",
    val echo: UUID
)