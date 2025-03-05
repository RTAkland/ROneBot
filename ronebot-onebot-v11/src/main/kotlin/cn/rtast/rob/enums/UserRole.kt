/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/31
 */

@file:Suppress("unused", "EnumEntryName")

package cn.rtast.rob.enums

/**
 * 用户角色
 */
public enum class UserRole(public val order: Int) {
    owner(3), admin(2), member(1)
}