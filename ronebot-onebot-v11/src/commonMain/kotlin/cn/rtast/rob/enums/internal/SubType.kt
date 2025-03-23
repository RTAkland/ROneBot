/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/26
 */

@file:Suppress("EnumEntryName")

package cn.rtast.rob.enums.internal

import kotlinx.serialization.Serializable

/**
 * 事件子类型
 */
@Serializable
internal enum class SubType {
    kick, kick_me, invite,
    set, unset, ban, lift_ban,
    leave, approve, add, poke, remove,
    connect, normal
}