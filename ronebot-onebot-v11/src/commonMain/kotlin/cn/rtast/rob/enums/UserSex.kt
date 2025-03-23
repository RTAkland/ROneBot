/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/19
 */

@file:Suppress("EnumEntryName", "unused")

package cn.rtast.rob.enums

import kotlinx.serialization.Serializable

/**
 * 用户的性别
 */
@Serializable
public enum class UserSex {
    male, female, unknown
}