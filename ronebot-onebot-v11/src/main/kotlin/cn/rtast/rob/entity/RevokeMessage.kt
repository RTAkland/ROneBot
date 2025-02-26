/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/9/2
 */


package cn.rtast.rob.entity

import cn.rtast.rob.annotations.ExcludeField
import cn.rtast.rob.onebot.OneBotAction
import com.google.gson.annotations.SerializedName

data class GroupRevokeMessage(
    @ExcludeField
    var action: OneBotAction,
    /**
     * 群号
     */
    @SerializedName("group_id")
    val groupId: Long,
    /**
     * 撤回者QQ号
     */
    @SerializedName("user_id")
    val userId: Long,
    /**
     * 消息ID
     */
    @SerializedName("message_id")
    val messageId: Long,
    /**
     * 操作者QQ号
     */
    @SerializedName("operator_id")
    val operatorId: Long,
)

data class PrivateRevokeMessage(
    @ExcludeField
    var action: OneBotAction,
    /**
     * QQ号
     */
    @SerializedName("user_id")
    val userId: Long,
    /**
     * 消息ID
     */
    @SerializedName("message_id")
    val messageId: Long,
    /**
     * 操作者QQ号
     */
    @SerializedName("operator_id")
    val operatorId: Long,
)