/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/2
 */


package cn.rtast.rob.event.raw.onebot

import kotlinx.serialization.Serializable

@Serializable
public data class CSRFToken(
    val data: CSRFToken
) {
    @Serializable
    public data class CSRFToken(
        val token: String
    )
}