/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/19
 */


package cn.rtast.rob.qqbot.entity.inbound

import cn.rtast.rob.annotations.ExcludeField
import cn.rtast.rob.qqbot.qbot.QQBotAction
import com.google.gson.annotations.SerializedName

public data class FriendDelEvent(
    val id: String,
    val d: DeleteEvent
) {
    public data class DeleteEvent(
        @ExcludeField
        var action: QQBotAction,
        val timestamp: String,
        @SerializedName("openid")
        val openId: String,
        val author: Author,
    )

    public data class Author(
        @SerializedName("union_openid")
        val unionOpenId: String,
    )
}