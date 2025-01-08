/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/1/8
 */


package cn.rtast.rob.entity.outbound.lagrange.set

import com.google.gson.annotations.SerializedName

data class JoinFriendEmojiChain(
    val params: Params,
    val action: String = ".join_friend_emoji_chain",
) {
    data class Params(
        @SerializedName("message_id")
        val messageId: Long,
        @SerializedName("emoji_id")
        val emojiId: Int,
        @SerializedName("user_id")
        val userId: Long
    )
}