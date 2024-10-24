/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/1
 */


package cn.rtast.rob.entity

import cn.rtast.rob.common.util.ExcludeFiled
import cn.rtast.rob.util.ob.OneBotAction
import com.google.gson.annotations.SerializedName

data class ReactionEvent(
    @ExcludeFiled
    var action: OneBotAction,
    @SerializedName("group_id")
    val groupId: Long,
    @SerializedName("operator_Id")
    val operatorId: Long,
    val count: Int,  // count表示有几个相同的code表情
    @SerializedName("message_id")
    val messageId: Long,
    @SerializedName("self_id")
    val selfId: Long,
    val code: String  // code表示一个表情ID
)