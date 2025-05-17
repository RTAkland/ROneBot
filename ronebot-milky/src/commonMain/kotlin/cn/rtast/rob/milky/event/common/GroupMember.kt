/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/18/25, 4:30 AM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.milky.event.common

import cn.rtast.rob.milky.enums.UserSex
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
public data class GroupMember(
    @SerialName("card")
    val card: String,
    @SerialName("group_id")
    val groupId: Long,
    @SerialName("join_time")
    val joinTime: Long,
    @SerialName("last_sent_time")
    val lastSentTime: Long,
    val level: Int,
    val nickname: String,
    val role: String,
    val sex: UserSex,
    val title: String,
    val userId: Long
)

