/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/30
 */

package cn.rtast.rob.api.set.message

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 将消息转发到群聊
 * Napcat
 */
@Serializable
internal data class ForwardGroupSingleMsgApi(
    val action: String = "forward_group_single_msg",
    val params: Params,
) {
    @Serializable
    data class Params(
        @SerialName("message_id")
        val messageId: Long,
        @SerialName("group_id")
        val groupId: Long
    )
}