/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/28
 */


package cn.rtast.rob.event.raw

import kotlinx.serialization.Serializable

@Serializable
internal data class CanSend(
    val data: Data,
) {
    @Serializable
    data class Data(
        val yes: Boolean
    )
}