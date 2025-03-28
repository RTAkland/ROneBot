/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/26
 */


package cn.rtast.rob.event.raw.metadata

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class NoticeEvent(
    /**
     * 群号
     */
    @SerialName("group_id")
    val groupId: Long?,
    /**
     * 操作者QQ号
     */
    @SerialName("operator_id")
    val operatorId: Long? = null,
    /**
     * 消息ID
     */
    @SerialName("user_id")
    val userId: Long,
    /**
     * 备注
     */
    val comment: String?,
    /**
     * 时长
     */
    val duration: Int?,
    /**
     * 消息ID
     */
    @SerialName("message_id")
    val messageId: Long?,
)