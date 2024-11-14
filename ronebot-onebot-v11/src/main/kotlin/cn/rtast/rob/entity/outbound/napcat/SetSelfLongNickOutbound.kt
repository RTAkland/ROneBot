/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/14
 */


package cn.rtast.rob.entity.outbound.napcat

internal data class SetSelfLongNickOutbound(
    val action: String = "set_self_longnick",
    val params: Params
) {
    data class Params(
        val longNick: String
    )
}