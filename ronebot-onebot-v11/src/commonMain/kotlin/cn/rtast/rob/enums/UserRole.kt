/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/31
 */

@file:Suppress("unused", "EnumEntryName")

package cn.rtast.rob.enums

import kotlinx.serialization.Serializable

/**
 * 用户角色
 */
@Serializable
public enum class UserRole(public val order: Int) {
    owner(3), admin(2), member(1)
}