/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/8
 */


package cn.rtast.rob.satori.entity.guild.inbound

import cn.rtast.rob.satori.entity.BaseMessage
import com.google.gson.annotations.SerializedName

data class GetGuildMember(
    val user: BaseMessage.User,
    val nick: String,
    val avatar: String,
    @SerializedName("joined_at")
    val joinedAt: Long
)