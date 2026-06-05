/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/3
 */


package cn.rtast.rob.api.get

import kotlinx.serialization.Serializable
import kotlin.uuid.Uuid

@Serializable
internal data class GetCookiesApi(
    val action: String = "get_cookies",
    val echo: Uuid,
    val params: Params,
) {
    @Serializable
    data class Params(
        val domain: String,
    )
}