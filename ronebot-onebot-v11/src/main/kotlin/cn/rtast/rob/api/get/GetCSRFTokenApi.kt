/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/2
 */


package cn.rtast.rob.api.get

import java.util.*

internal data class GetCSRFTokenApi(
    val action: String = "get_csrf_token",
    val echo: UUID
)