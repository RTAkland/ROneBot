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
 * 用户入群请求
 */
@Serializable
public data class JoinRequestNotification(
    override val type: NotificationType = NotificationType.JoinRequest,
    @SerialName("group_id")
    override val groupId: Long,
    /**
     * 请求是否被过滤（发起自风险账户）
     */
    @SerialName("is_filtered")
    val isFiltered: Boolean,
    /**
     * 发起者 QQ 号
     */
    @SerialName("initiator_id")
    val initiatorId: Long,
    /**
     * 请求状态
     */
    val state: RequestState,
    /**
     * 处理请求的管理员 QQ 号
     */
    @SerialName("operator_id")
    val operatorId: Long?,
    /**
     * 入群请求附加信息
     */
    val comment: String,
) : GroupNotification