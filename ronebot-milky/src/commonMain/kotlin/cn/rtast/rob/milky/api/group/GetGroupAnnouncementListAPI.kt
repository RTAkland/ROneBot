/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/18/25, 3:38 AM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.milky.api.group

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 获取群公告列表
 */
@Serializable
internal data class GetGroupAnnouncementListAPI(
    @SerialName("group_id")
    val groupId: Long,
)