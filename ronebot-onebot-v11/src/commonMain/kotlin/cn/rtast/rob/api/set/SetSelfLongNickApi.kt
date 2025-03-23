/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/14
 */


package cn.rtast.rob.api.set

import kotlinx.serialization.Serializable

@Serializable
internal data class SetSelfLongNickApi(
    val action: String = "set_self_longnick",
    val params: Params
) {
    @Serializable
    data class Params(
        val longNick: String
    )
}