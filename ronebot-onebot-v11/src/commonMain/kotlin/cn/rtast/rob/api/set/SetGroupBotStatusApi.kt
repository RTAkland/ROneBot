/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/2/26
 */


package cn.rtast.rob.api.set

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class SetGroupBotStatusApi(
    val action: String = "set_group_bot_status",
    val params: Params
) {
    @Serializable
    data class Params(
        @SerialName("group_id")
        val groupId: Long,
        @SerialName("bot_id")
        val botId: Long,
        val enable: Boolean
    )
}