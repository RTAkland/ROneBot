/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/8
 */


package cn.rtast.rob.satori.entity.guild.inbound

import cn.rtast.rob.satori.enums.GuildUserRole

public data class GetGuildRole(
    val data: List<Role>
) {
    public data class Role(
        val id: String,
        val name: GuildUserRole
    )
}