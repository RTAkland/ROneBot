/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/30
 */

package cn.rtast.rob.api.set.message

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 设置表情回应
 * llonebot
 */
@Serializable
internal data class SetMsgEmojiLikeApi(
    val action: String = "set_msg_emoji_like",
    val params: Params
) {
    @Serializable
    data class Params(
        @SerialName("message_id")
        val messageId: Long,
        @SerialName("emoji_id")
        val emojiId: Int
    )
}