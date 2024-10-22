/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/17
 */


package cn.rtast.rob.entity

import cn.rtast.rob.enums.internal.ActionStatus
import com.google.gson.annotations.SerializedName

internal data class SendMessageResp(
    val status: ActionStatus,
    @SerializedName("retcode")
    val retCode: Int,
    val data: Data?,
) {
    data class Data(
        @SerializedName("message_id")
        val messageId: Long,
    )
}