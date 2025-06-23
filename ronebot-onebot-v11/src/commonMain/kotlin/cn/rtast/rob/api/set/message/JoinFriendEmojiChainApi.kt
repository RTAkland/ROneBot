/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/1/8
 */

@file:OptIn(ExperimentalUuidApi::class)

package cn.rtast.rob.api.set.message

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Serializable
internal data class JoinFriendEmojiChainApi(
    val params: Params,
    val echo: Uuid,
    val action: String = ".join_friend_emoji_chain",
) {
    @Serializable
    data class Params(
        @SerialName("message_id")
        val messageId: Long,
        @SerialName("emoji_id")
        val emojiId: Int,
        @SerialName("user_id")
        val userId: Long
    )
}