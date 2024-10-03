/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/3
 */


package cn.rtast.rob.entity.out.lagrange.get

import cn.rtast.rob.enums.internal.MessageEchoType

internal data class GetCookiesOut(
    val action: String = "get_cookies",
    val echo: MessageEchoType = MessageEchoType.GetCookies,
    val params: Params
) {
    data class Params(
        val domain: String,
    )
}