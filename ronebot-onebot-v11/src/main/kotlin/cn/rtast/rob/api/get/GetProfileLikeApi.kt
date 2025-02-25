/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/14
 */


package cn.rtast.rob.api.get

import java.util.*

internal data class GetProfileLikeApi(
    val action: String = "get_profile_like",
    val echo: UUID
)