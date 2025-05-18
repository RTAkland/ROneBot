/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/18/25, 8:03 PM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

@file:Suppress("EnumEntryName")

package cn.rtast.rob.milky.enums

import kotlinx.serialization.Serializable

/**
 * 群成员角色
 */
@Serializable
public enum class GroupMemberRole {
    /**
     * 群主
     */
    owner,

    /**
     * 管理员
     */
    admin,

    /**
     * 普通成员
     */
    member
}