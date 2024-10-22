/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/22
 */


package cn.rtast.rob.entity.out.lagrange.set

import cn.rtast.rob.enums.internal.MessageEchoType

internal data class SetBotAvatarOut(
    val params: Params,
    val action: String = "set_qq_avatar",
    val echo: MessageEchoType = MessageEchoType.SetBotAvatar
) {
    data class Params(
        val file: String
    )
}

internal data class SetBotAvatar(
    val status: String
)