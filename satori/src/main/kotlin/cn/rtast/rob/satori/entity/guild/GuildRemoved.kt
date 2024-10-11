/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/11
 */


package cn.rtast.rob.satori.entity.guild

import cn.rtast.rob.satori.entity.GroupMessage.Guild
import com.google.gson.annotations.SerializedName

data class GuildRemoved(
    val op: Int,
    val body: Body
) {
    data class Body(
        val id: Int,
        val type: String,
        @SerializedName("self_id")
        val selfId: String,
        val platform: String,
        val timestamp: Long,
        val guild: Guild
    )
}