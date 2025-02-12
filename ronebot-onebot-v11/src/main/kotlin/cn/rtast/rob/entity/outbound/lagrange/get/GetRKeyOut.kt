/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/2/3
 */


package cn.rtast.rob.entity.outbound.lagrange.get

import java.util.*

internal data class GetRKeyOut(
    val action: String = "get_rkey",
    val echo: UUID
)