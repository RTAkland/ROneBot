/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/17
 */


package cn.rtast.rob.event.raw

import cn.rtast.rob.enums.internal.ActionStatus
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 发送消息后服务器返回的消息ID
 */
@Serializable
internal data class SendMessageResp(
    val status: ActionStatus,
    @SerialName("retcode")
    val retCode: Int,
    val data: SendResponse?,
) {
    @Serializable
    data class SendResponse(
        /**
         * 消息ID
         */
        @SerialName("message_id")
        val messageId: Long,
    )
}