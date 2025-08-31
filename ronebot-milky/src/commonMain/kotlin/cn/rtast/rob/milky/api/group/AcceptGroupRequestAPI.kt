/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 8/31/25, 2:25 PM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */


package cn.rtast.rob.milky.api.group

import cn.rtast.rob.milky.enums.NotificationType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class AcceptGroupRequestAPI(
    /**
     * 请求对应的通知序列号
     */
    @SerialName("notification_seq")
    val notificationSeq: Long,
    /**
     * 请求对应的通知类型
     */
    @SerialName("notification_type")
    val notificationType: NotificationType,
    @SerialName("group_id")
    val groupId: Long,
    @SerialName("is_filtered")
    val isFiltered: Boolean = false,
)