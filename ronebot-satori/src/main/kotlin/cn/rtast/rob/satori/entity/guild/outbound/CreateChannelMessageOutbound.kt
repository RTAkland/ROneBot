/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/10
 */


package cn.rtast.rob.satori.entity.guild.outbound

import com.google.gson.annotations.SerializedName

internal data class CreateChannelMessageOutbound(
    @SerializedName("channel_id")
    val channelId: String,
    val content: String
)