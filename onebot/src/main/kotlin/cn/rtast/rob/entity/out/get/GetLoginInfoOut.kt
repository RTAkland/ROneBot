/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/26
 */


package cn.rtast.rob.entity.out.get

import java.util.UUID

internal data class GetLoginInfoOut(
    val action: String = "get_login_info",
    val echo: UUID
)