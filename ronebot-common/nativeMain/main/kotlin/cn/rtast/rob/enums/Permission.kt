/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/30
 */

@file:Suppress("unused")

package cn.rtast.rob.enums

/**
 * 指令管理器的权限控制
 */
enum class Permission(val order: Int) {
    OWNER(3), ADMIN(2), MEMBER(1)
}