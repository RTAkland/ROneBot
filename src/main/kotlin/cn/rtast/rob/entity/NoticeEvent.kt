/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/26
 */


package cn.rtast.rob.entity

import com.google.gson.annotations.SerializedName

data class NoticeEvent(
    @SerializedName("group_id")
    val groupId: Long?,
    @SerializedName("operator_id")
    val operatorId: Long,
    @SerializedName("user_id")
    val userId: Long,
    val comment: String?,
    val duration: Int?,
    @SerializedName("message_id")
    val messageId: String?,
)