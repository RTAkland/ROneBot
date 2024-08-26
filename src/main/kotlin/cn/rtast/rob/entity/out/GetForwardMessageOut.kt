/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/26
 */


package cn.rtast.rob.entity.out

data class GetForwardMessageOut(
    val action: String = "get_forward_msg",
    val params: Params,
) {
    data class Params(
        val id: Long,
    )
}