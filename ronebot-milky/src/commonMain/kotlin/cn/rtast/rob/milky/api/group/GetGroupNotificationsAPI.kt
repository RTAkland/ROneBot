/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 8/31/25, 2:23 PM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */


package cn.rtast.rob.milky.api.group

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class GetGroupNotificationsAPI(
    /**
     * 起始通知序列号
     */
    @SerialName("start_notification_seq")
    val startNotificationSeq: Long?,
    /**
     * `true` 表示只获取被过滤（由风险账号发起）的通知，`false` 表示只获取未被过滤的通知
     */
    @SerialName("is_filtered")
    val isFiltered: Boolean = false,
    /**
     * 获取的最大通知数量
     */
    val limit: Int = 20,
)