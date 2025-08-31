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
 * 群管理员变更通知
 */
@Serializable
public data class AdminChangeNotification(
    override val type: NotificationType = NotificationType.AdminChange,
    override val groupId: Long,
    /**
     * 通知序列号
     */
    @SerialName("notification_seq")
    val notificationSeq: Long,
    /**
     * 被设置/取消用户 QQ 号
     */
    @SerialName("target_user_id")
    val targetUserId: Long,
    /**
     * 是否被设置为管理员，`false` 表示被取消管理员
     */
    @SerialName("is_set")
    val isSet: Boolean,
    /**
     * 操作者（群主）QQ 号
     */
    @SerialName("operator_id")
    val operatorId: Long,
) : GroupNotification