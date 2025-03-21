/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/2/26
 */


package cn.rtast.rob.api.set

import com.google.gson.annotations.SerializedName

internal data class SetGroupBotStatusApi(
    val action: String = "set_group_bot_status",
    val params: Params
) {
    data class Params(
        @SerializedName("group_id")
        val groupId: Long,
        @SerializedName("bot_id")
        val botId: Long,
        val enable: Boolean
    )
}