/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/10
 */


package cn.rtast.rob.satori.entity.guild.outbound

import com.google.gson.annotations.SerializedName

internal data class GetMessageOutbound(
    @SerializedName("channel_id")
    val channelId: String,
    @SerializedName("message_id")
    val messageId: String
)