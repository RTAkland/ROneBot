/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/7
 */

package cn.rtast.rob.kook.webhook.event

import cn.rtast.rob.annotations.ExcludeField
import cn.rtast.rob.kook.webhook.entity.Author
import cn.rtast.rob.kook.webhook.kook.ChannelType
import cn.rtast.rob.kook.webhook.kook.KookAction
import com.google.gson.annotations.SerializedName

public data class RawMessageEvent(
    val s: Int,
    val d: Body,
    val sn: Int,
    @ExcludeField
    var action: KookAction,
) {
    public data class Body(
        @SerializedName("channel_type")
        val channelType: ChannelType,
        val type: Int,
        val content: String,
        @SerializedName("target_id")
        val targetId: String,
        @SerializedName("author_id")
        val authorId: String,
        val extra: Extra,
        @SerializedName("msg_id")
        val messageId: String,
        @SerializedName("msg_timestamp")
        val messageTimestamp: Long,
        val nonce: String,
        @SerializedName("from_type")
        val fromType: Int,
        @SerializedName("verify_token")
        val verifyToken: String
    )

    public data class Extra(
        val type: Int,
        @SerializedName("guild_id")
        val guildId: String,
        val code: String,
        @SerializedName("guild_type")
        val guildType: Int,
        @SerializedName("channel_name")
        val channelName: String,
        val author: Author,
        @SerializedName("visible_only")
        val visibleOnly: String,
        val mention: List<String>,
        @SerializedName("mention_all")
        val mentionAll: Boolean,
        @SerializedName("mention_roles")
        val mentionRoles: List<String>,
        @SerializedName("mention_here")
        val mentionHere: Boolean,
    )
}