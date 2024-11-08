/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/8
 */


package cn.rtast.rob.satori.entity.guild.outbound

import com.google.gson.annotations.SerializedName

internal data class ApproveGuildRequestOutbound(
    @SerializedName("message_id")
    val messageID: String,
    val approve: Boolean,
    val comment: String?
)