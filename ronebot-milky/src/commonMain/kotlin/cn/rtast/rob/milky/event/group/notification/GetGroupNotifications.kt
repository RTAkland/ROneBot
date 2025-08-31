/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 8/31/25, 2:49 PM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */


package cn.rtast.rob.milky.event.group.notification

import cn.rtast.rob.milky.enums.internal.ApiStatus
import cn.rtast.rob.milky.event.group.notification.type.GroupNotification
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 获取群通知列表
 */
@Serializable
public data class GetGroupNotifications(
    val message: String?,
    val status: ApiStatus,
    val data: GroupNotifications?,
) {
    @Serializable
    public data class GroupNotifications(
        /**
         * 	获取到的群通知（notification_seq 降序排列），序列号不一定连续
         */
        val notifications: List<GroupNotification>,
        /**
         * 下一页起始通知序列号
         */
        @SerialName("next_notification_seq")
        val nextNotificationSeq: Long?,
    )
}