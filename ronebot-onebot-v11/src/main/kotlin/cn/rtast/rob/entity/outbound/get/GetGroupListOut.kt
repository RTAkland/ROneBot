/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/27
 */


package cn.rtast.rob.entity.outbound.get

import java.util.UUID

internal data class GetGroupListOut(
    val action: String = "get_group_list",
    val echo: UUID
)