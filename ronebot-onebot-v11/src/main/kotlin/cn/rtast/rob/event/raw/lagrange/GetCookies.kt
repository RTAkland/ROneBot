/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/3
 */


package cn.rtast.rob.event.raw.lagrange

public data class GetCookies(
    val data: Cookie
) {
    public data class Cookie(
        val cookies: String
    )
}