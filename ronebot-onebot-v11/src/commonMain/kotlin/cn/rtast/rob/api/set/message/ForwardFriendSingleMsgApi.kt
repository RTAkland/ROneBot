/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/30
 */

package cn.rtast.rob.api.set.message

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 将消息转发给好友
 * Napcat
 */
@Serializable
internal data class ForwardFriendSingleMsgApi(
    val action: String = "forward_friend_single_msg",
    val params: Params,
) {
    @Serializable
    data class Params(
        @SerialName("message_id")
        val messageId: Long,
        @SerialName("user_id")
        val userId: Long
    )
}