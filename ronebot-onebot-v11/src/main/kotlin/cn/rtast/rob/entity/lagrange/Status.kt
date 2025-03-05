/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/3
 */


package cn.rtast.rob.entity.lagrange

import cn.rtast.rob.entity.metadata.event.HeartBeatEvent

public data class Status(
    val data: HeartBeatEvent.Status
)