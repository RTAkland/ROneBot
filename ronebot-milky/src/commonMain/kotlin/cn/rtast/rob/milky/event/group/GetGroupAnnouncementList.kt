/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/18/25, 9:52 PM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.milky.event.group

import cn.rtast.rob.milky.enums.internal.ApiStatus
import cn.rtast.rob.milky.event.common.Announcement
import kotlinx.serialization.Serializable

/**
 * 获取群公告列表
 */
@Serializable
public data class GetGroupAnnouncementList(
    val data: Announcements?,
    val status: ApiStatus,
    val message: String?
) {
    @Serializable
    public data class Announcements(
        val announcements: List<Announcement>
    )
}