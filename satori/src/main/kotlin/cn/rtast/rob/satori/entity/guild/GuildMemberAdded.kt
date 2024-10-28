/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/11
 */


package cn.rtast.rob.satori.entity.guild

import cn.rtast.rob.common.annotations.ExcludeField
import cn.rtast.rob.satori.entity.GroupMessage
import cn.rtast.rob.satori.entity.GroupMessage.Guild
import cn.rtast.rob.satori.entity.LoginInfo
import cn.rtast.rob.satori.util.SatoriAction
import com.google.gson.annotations.SerializedName

data class GuildMemberAdded(
    val op: Int,
    val body: GuildMemberAdded
) {
    data class GuildMemberAdded(
        @ExcludeField
        var action: SatoriAction,
        val id: Int,
        val type: String,
        @SerializedName("self_id")
        val selfId: String,
        val platform: String,
        val timestamp: Long,
        val guild: Guild,
        val user: LoginInfo.User,
        val member: GroupMessage.Member
    )
}