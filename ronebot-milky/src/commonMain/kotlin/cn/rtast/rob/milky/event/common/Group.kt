/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/18/25, 4:27 AM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.milky.event.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class Group(
    /**
     * 群号
     */
    @SerialName("group_id")
    val groupId: Long,
    /**
     * 群名称
     */
    val name: String,
    /**
     * 成员数量
     */
    @SerialName("member_count")
    val memberCount: Int,
    /**
     * 最大群成员数量
     */
    @SerialName("max_member_count")
    val maxMemberCount: Int
)