/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/12
 */


package cn.rtast.rob.qqbot.legacy.entity.outbound

internal data class GetAccessTokenOutbound(
    val appId: String,
    val clientSecret: String
)