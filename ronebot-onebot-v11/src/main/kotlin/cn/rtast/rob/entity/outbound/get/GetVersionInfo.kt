/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/27
 */


package cn.rtast.rob.entity.outbound.get

import java.util.*

internal data class GetVersionInfo(
    val action: String = "get_version_info",
    val echo: UUID
)
