/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/18/25, 3:37 AM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.milky.api.group

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 设置群聊管理员
 */
@Serializable
internal data class SetGroupMemberAdminAPI(
    @SerialName("group_id")
    val groupId: Long,
    @SerialName("user_id")
    val userId: Long,
    @SerialName("is_set")
    val isSet: Boolean = true
)