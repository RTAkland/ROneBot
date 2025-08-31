/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 8/31/25, 2:50 PM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */


package cn.rtast.rob.milky.event.group.notification.type

import cn.rtast.rob.milky.enums.NotificationType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 群成员被移除通知
 */
@Serializable
public data class KickNotification(
    override val type: NotificationType = NotificationType.Kick,
    override val groupId: Long,
    /**
     * 通知序列号
     */
    @SerialName("notification_seq")
    val notificationSeq: Long,
    /**
     * 被移除用户 QQ 号
     */
    @SerialName("target_user_id")
    val targetUserId: Long,
    /**
     * 	移除用户的管理员 QQ 号
     */
    @SerialName("operator_id")
    val operatorId: Long,
) : GroupNotification