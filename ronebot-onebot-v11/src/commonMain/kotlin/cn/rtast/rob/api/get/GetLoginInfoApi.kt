/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/26
 */


package cn.rtast.rob.api.get

import java.util.*

internal data class GetLoginInfoApi(
    val action: String = "get_login_info",
    val echo: UUID
)