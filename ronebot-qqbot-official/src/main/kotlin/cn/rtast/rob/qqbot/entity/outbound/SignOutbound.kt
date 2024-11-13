/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/13
 */


package cn.rtast.rob.qqbot.entity.outbound

internal data class SignOutbound(
    val d: Body,
    val op: Int = 12
) {
    data class Body(
        val plainToken: String,
        val signature: String
    )
}