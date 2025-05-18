/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/18/25, 8:00 PM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.milky.event.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 群公告
 */
@Serializable
public data class Announcement(
    /**
     * 群号
     */
    @SerialName("group_id")
    val groupId: Long,
    /**
     * 公告ID
     */
    @SerialName("announcement_id")
    val announcementId: String,
    /**
     * 公告发布者QQ号
     */
    @SerialName("user_id")
    val userId: Long,
    /**
     * UNIX时间戳 秒
     */
    val time: Long,
    /**
     * 公告内容
     */
    val content: String,
    /**
     * 图片
     */
    @SerialName("image_uri")
    val imageUri: String
)