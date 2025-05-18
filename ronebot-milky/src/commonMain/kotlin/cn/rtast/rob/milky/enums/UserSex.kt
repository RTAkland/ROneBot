/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/18/25, 4:33 AM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

@file:Suppress("EnumEntryName")

package cn.rtast.rob.milky.enums

import kotlinx.serialization.Serializable

/**
 * 用户的性别
 */
@Serializable
public enum class UserSex {
    /**
     * 未知
     */
    unknown,

    /**
     * 女
     */
    female,

    /**
    * 男
     */
    male
}