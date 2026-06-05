/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/22
 */


package cn.rtast.rob.api.set

import kotlinx.serialization.Serializable
import kotlin.uuid.Uuid

@Serializable
internal data class SetBotAvatarApi(
    val params: Params,
    val action: String = "set_qq_avatar",
    val echo: Uuid,
) {
    @Serializable
    data class Params(
        val file: String,
    )
}

@Serializable
internal data class SetBotAvatar(
    val status: String,
)