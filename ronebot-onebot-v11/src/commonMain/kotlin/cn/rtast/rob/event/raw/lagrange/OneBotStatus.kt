/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/3
 */


package cn.rtast.rob.event.raw.lagrange

import cn.rtast.rob.event.raw.metadata.RawHeartBeatEvent

public data class OneBotStatus(
    val data: RawHeartBeatEvent.Status
)