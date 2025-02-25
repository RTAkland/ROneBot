/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/26
 */


package cn.rtast.rob.api.set

internal data class SetGroupRequestApi(
    val action: String = "set_group_add_request",
    val params: Params,
) {
    data class Params(
        val flag: String,
        val type: String,
        val approve: Boolean,
        val reason: String,
    )
}