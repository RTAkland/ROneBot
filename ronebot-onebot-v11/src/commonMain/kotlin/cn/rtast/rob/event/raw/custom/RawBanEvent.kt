/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/24
 */


package cn.rtast.rob.event.raw.custom

import cn.rtast.rob.onebot.OneBotAction

public data class RawBanEvent(
    val groupId: Long,
    val operator: Long,
    val duration: Int,
    val time: Long,
    val userId: Long,
    val action: OneBotAction
)

public data class RawPardonBanEvent(
    val groupId: Long,
    val operator: Long,
    val duration: Int,
    val time: Long,
    val userId: Long,
    val action: OneBotAction
)