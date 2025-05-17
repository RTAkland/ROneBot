/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/18/25, 3:30 AM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.milky.api.system

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 获取群成员信息
 */
@Serializable
internal data class GetGroupMemberInfoAPI(
    @SerialName("group_id")
    val groupId: Long,
    @SerialName("user_id")
    val userId: Long,
    @SerialName("no_cache")
    val noCache: Boolean
)