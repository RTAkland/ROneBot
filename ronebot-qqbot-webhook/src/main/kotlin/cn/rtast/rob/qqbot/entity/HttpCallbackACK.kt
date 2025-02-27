/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/2/27
 */

package cn.rtast.rob.qqbot.entity

import cn.rtast.rob.qqbot.enums.OPCode

data class HttpCallbackACK(
    val op: Int = OPCode.HTTPCallbackACK.opCode
)