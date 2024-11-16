/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/14
 */


package cn.rtast.rob.entity.outbound.napcat

import java.util.*

internal data class GetMiniAppArkOutbound(
    val params: Params,
    val echo: UUID,
    val action: String = "get_mini_app_ark"
) {
    data class Params(
        val type: String,
        val title: String,
        val desc: String,
        val picUrl: String,
        val jumpUrl: String,
        val iconUrl: String? = null,
        val sdkId: String? = null,
        val appId: String? = null,
    )
}