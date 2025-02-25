/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/3
 */


package cn.rtast.rob.api.get

import cn.rtast.rob.enums.internal.ActionStatus
import com.google.gson.annotations.SerializedName
import java.util.*

internal data class GetAIRecordAndSendRecordApi(
    val params: Params,
    val echo: UUID,
    val action: String
) {
    data class Params(
        @SerializedName("chat_type")
        val chatType: UInt,
        val text: String,
        @SerializedName("group_id")
        val groupId: Long,
        val character: String,
    )
}

internal data class AIRecord(
    val status: ActionStatus,
    val data: String
)