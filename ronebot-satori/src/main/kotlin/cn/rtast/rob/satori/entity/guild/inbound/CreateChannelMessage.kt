/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/10
 */


package cn.rtast.rob.satori.entity.guild.inbound

import cn.rtast.rob.satori.entity.BaseMessage
import cn.rtast.rob.satori.entity.GuildMessage
import com.google.gson.annotations.SerializedName

data class CreateChannelMessage(
    val id: String,
    val content: String,
    val channel: GuildMessage.Channel,
    val user: BaseMessage.User,
    @SerializedName("create_at")
    val createAt: Long,
    val guild: GetGuild,
    val member: GuildMessage.Member
)