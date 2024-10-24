/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/9/2
 */


package cn.rtast.rob.entity

import cn.rtast.rob.common.util.ExcludeFiled
import cn.rtast.rob.util.ob.OneBotAction
import com.google.gson.annotations.SerializedName

data class GroupRevokeMessage(
    @ExcludeFiled
    var action: OneBotAction,
    @SerializedName("group_id")
    val groupId: Long,
    @SerializedName("user_id")
    val userId: Long,
    @SerializedName("message_id")
    val messageId: Long,
    @SerializedName("operator_id")
    val operatorId: Long,
)

data class PrivateRevokeMessage(
    @ExcludeFiled
    var action: OneBotAction,
    @SerializedName("user_id")
    val userId: Long,
    @SerializedName("message_id")
    val messageId: Long,
    @SerializedName("operator_id")
    val operatorId: Long,
)