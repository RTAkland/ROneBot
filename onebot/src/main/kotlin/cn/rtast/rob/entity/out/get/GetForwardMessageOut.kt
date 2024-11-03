/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/26
 */


package cn.rtast.rob.entity.out.get

import java.util.UUID

internal data class GetForwardMessageOut(
    val params: Params,
    val echo: UUID,
    val action: String = "get_forward_msg",
) {
    data class Params(
        val id: String,
    )
}