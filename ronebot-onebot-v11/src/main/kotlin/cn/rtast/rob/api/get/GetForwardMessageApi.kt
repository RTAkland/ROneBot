/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/26
 */


package cn.rtast.rob.api.get

import java.util.*

internal data class GetForwardMessageApi(
    val params: Params,
    val echo: UUID,
    val action: String = "get_forward_msg",
) {
    data class Params(
        val id: String,
    )
}