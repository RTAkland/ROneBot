/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/22
 */


package cn.rtast.rob.api.set

import java.util.*

internal data class SetBotAvatarApi(
    val params: Params,
    val action: String = "set_qq_avatar",
    val echo: UUID
) {
    data class Params(
        val file: String
    )
}

internal data class SetBotAvatar(
    val status: String
)