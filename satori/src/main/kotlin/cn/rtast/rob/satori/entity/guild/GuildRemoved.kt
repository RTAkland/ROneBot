/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/11
 */


package cn.rtast.rob.satori.entity.guild

import cn.rtast.rob.annotations.ExcludeField
import cn.rtast.rob.satori.entity.GroupMessage.Guild
import cn.rtast.rob.satori.util.SatoriAction
import com.google.gson.annotations.SerializedName

data class GuildRemoved(
    val op: Int,
    val body: GuildRemoved
) {
    data class GuildRemoved(
        @ExcludeField
        var action: SatoriAction,
        val id: Int,
        val type: String,
        @SerializedName("self_id")
        val selfId: String,
        val platform: String,
        val timestamp: Long,
        val guild: Guild
    )
}