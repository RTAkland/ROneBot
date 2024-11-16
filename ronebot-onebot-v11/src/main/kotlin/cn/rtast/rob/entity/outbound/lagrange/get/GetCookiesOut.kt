/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/3
 */


package cn.rtast.rob.entity.outbound.lagrange.get

import java.util.*

internal data class GetCookiesOut(
    val action: String = "get_cookies",
    val echo: UUID,
    val params: Params
) {
    data class Params(
        val domain: String,
    )
}