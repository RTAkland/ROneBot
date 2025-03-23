/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/9/2
 */

@file:Suppress("EnumEntryName")

package cn.rtast.rob.enums.internal

import kotlinx.serialization.Serializable

/**
 * 两种消息类型
 */
@Serializable
internal enum class ContactType {
    qq, group
}