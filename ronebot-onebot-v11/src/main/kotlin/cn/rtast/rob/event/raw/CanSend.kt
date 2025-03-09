/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/28
 */


package cn.rtast.rob.event.raw

internal data class CanSend(
    val data: Data,
) {
    data class Data(
        val yes: Boolean
    )
}