/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/2/27
 */

package cn.rtast.rob.qqbot.entity

import cn.rtast.rob.qqbot.enums.OPCode

public data class HttpCallbackACK(
    val op: Int = OPCode.HTTPCallbackACK.opCode
)