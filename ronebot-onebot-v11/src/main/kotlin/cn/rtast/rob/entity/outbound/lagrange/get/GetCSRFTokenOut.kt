/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/2
 */


package cn.rtast.rob.entity.outbound.lagrange.get

import java.util.UUID

internal data class GetCSRFTokenOut(
    val action: String = "get_csrf_token",
    val echo: UUID
)