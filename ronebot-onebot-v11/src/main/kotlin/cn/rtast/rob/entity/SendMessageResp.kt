/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/17
 */


package cn.rtast.rob.entity

import cn.rtast.rob.enums.internal.ActionStatus
import com.google.gson.annotations.SerializedName

/**
 * 发送消息后服务器返回的消息ID
 */
internal data class SendMessageResp(
    val status: ActionStatus,
    @SerializedName("retcode")
    val retCode: Int,
    val data: SendResponse?,
) {
    data class SendResponse(
        /**
         * 消息ID
         */
        @SerializedName("message_id")
        val messageId: Long,
    )
}