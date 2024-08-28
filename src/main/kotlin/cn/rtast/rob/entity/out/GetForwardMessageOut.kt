/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/26
 */


package cn.rtast.rob.entity.out

import cn.rtast.rob.enums.MessageEchoType

internal data class GetForwardMessageOut(
    val action: String = "get_forward_msg",
    val params: Params,
    val echo: MessageEchoType = MessageEchoType.GetForwardMessage
) {
    data class Params(
        val id: String,
    )
}