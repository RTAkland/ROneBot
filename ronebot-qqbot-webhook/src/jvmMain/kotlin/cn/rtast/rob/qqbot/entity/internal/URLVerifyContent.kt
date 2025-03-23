/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/15
 */


package cn.rtast.rob.qqbot.entity.internal

import com.google.gson.annotations.SerializedName

internal data class URLVerifyContent(
    @SerializedName("bot_appid")
    val appId: Long,
)