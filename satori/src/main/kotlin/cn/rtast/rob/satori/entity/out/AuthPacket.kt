/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/8
 */


package cn.rtast.rob.satori.entity.out

import cn.rtast.rob.satori.enums.OPCode

internal data class AuthPacket(
    val op: OPCode = OPCode.IDENTIFY,
    val body: AuthBody
) {
    data class AuthBody(
        val token: String,
        val sequence: Int = 0
    )
}