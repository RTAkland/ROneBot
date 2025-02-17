/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/1/29
 */

@file:Suppress("unused")

package cn.rtast.rob.permission.enums

/**
 * 内置的基本权限
 */
enum class BasicPermission(val level: Int) {
    Owner(3), Admin(2), User(1), Other(0);

    companion object {
        fun fromLevel(level: Int): BasicPermission {
            return when {
                level >= 3 -> Owner
                level == 2 -> Admin
                level == 1 -> User
                else -> Other
            }
        }
    }
}