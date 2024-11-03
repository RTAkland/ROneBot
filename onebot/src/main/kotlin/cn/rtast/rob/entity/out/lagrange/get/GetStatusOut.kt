/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/3
 */


package cn.rtast.rob.entity.out.lagrange.get

import java.util.UUID

internal data class GetStatusOut(
    val action: String = "get_status",
    val echo: UUID
)