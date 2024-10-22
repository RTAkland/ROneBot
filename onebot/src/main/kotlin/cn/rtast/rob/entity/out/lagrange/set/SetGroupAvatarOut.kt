/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/22
 */


package cn.rtast.rob.entity.out.lagrange.set

import cn.rtast.rob.enums.internal.MessageEchoType

internal data class SetGroupAvatarOut(
    val params: Params,
    val action: String = "set_group_portrait",
    val echo: MessageEchoType = MessageEchoType.SetGroupAvatar
) {
    data class Params(
        val file: String
    )
}

internal data class SetGroupAvatar(val status: String)