/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/31
 */


package cn.rtast.rob.entity

import com.google.gson.annotations.SerializedName

data class GroupArrayMessage(
    @SerializedName("sub_type")
    val subType: String,
    @SerializedName("message_id")
    val messageId: Long,
    @SerializedName("user_id")
    val userId: Long,
    @SerializedName("group_id")
    val groupId: Long,
    val message: List<ArrayMessage>,
    @SerializedName("raw_message")
    val rawMessage: String,
    val sender: Sender,
    val time: Long,
)