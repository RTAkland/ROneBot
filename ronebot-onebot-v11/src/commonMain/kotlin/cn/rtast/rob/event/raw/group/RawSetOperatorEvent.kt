/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/24
 */


package cn.rtast.rob.event.raw.group

import cn.rtast.rob.onebot.OneBotAction

public data class RawSetOperatorEvent(
    val groupId: Long,
    val operator: Long,
    val time: Long,
    val userId: Long,
    val action: OneBotAction
)

public data class RawUnsetOperatorEvent(
    val groupId: Long,
    val operator: Long,
    val time: Long,
    val userId: Long,
    val action: OneBotAction
)

