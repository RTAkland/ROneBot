/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/28
 */


package cn.rtast.rob.entity.outbound.llonebot

import java.util.*

internal data class GetRobotUinRangeOut(
    val action: String = "get_robot_uin_range",
    val echo: UUID,
)