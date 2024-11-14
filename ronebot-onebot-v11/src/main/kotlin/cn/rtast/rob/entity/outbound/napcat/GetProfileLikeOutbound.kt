/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/14
 */


package cn.rtast.rob.entity.outbound.napcat

import java.util.UUID

internal data class GetProfileLikeOutbound(
    val action: String = "get_profile_like",
    val echo: UUID
)