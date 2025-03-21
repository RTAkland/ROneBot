/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/26
 */


package cn.rtast.rob.event.raw.metadata

import com.google.gson.annotations.SerializedName

internal data class NoticeEvent(
    /**
     * 群号
     */
    @SerializedName("group_id")
    val groupId: Long?,
    /**
     * 操作者QQ号
     */
    @SerializedName("operator_id")
    val operatorId: Long,
    /**
     * 消息ID
     */
    @SerializedName("user_id")
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
    @SerializedName("message_id")
    val messageId: Long?,
)