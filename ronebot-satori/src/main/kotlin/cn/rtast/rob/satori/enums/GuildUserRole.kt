/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/8
 */

@file:Suppress("unused", "EnumEntryName")


package cn.rtast.rob.satori.enums

/**
 * 群组角色信息
 */
public enum class GuildUserRole(public val roleId: String) {
    owner("4"), admin("3"), member("2")
}