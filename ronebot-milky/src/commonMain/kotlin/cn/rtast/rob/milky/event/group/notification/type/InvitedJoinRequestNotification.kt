/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 8/31/25, 2:50 PM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */


package cn.rtast.rob.milky.event.group.notification.type

import cn.rtast.rob.milky.enums.NotificationType
import cn.rtast.rob.milky.enums.RequestState
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 群成员邀请他人入群请求
 */
@Serializable
public data class InvitedJoinRequestNotification(
    override val type: NotificationType = NotificationType.InvitedHJoinRequest,
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
    /**
     * 请求状态
     */
    val state: RequestState,
    /**
     * 邀请者 QQ 号
     */
    @SerialName("initiator_id")
    val initiatorId: Long,
) : GroupNotification