/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/1
 */


package cn.rtast.rob.entity

import cn.rtast.rob.annotations.ExcludeField
import cn.rtast.rob.onebot.OneBotAction
import com.google.gson.annotations.SerializedName

public data class ReactionEvent(
    @ExcludeField
    var action: OneBotAction,
    /**
     * 群号
     */
    @SerializedName("group_id")
    val groupId: Long,
    /**
     * 操作者QQ号
     */
    @SerializedName("operator_Id")
    val operatorId: Long,
    /**
     * 表示有几个相同的code表情
     */
    val count: Int,
    /**
     * 消息ID
     */
    @SerializedName("message_id")
    val messageId: Long,
    /**
     * 自身的QQ号
     */
    @SerializedName("self_id")
    val selfId: Long,
    /**
     * code表示一个表情ID
     */
    val code: String
)