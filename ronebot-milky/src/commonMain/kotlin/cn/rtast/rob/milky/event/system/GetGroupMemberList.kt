/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/18/25, 4:30 AM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.milky.event.system

import cn.rtast.rob.milky.enums.internal.ApiStatus
import cn.rtast.rob.milky.event.common.GroupMember
import kotlinx.serialization.Serializable

/**
 * 获取群成员列表
 */
@Serializable
public data class GetGroupMemberList(
    val data: List<GroupMember>?,
    val status: ApiStatus,
    val message: String?
)