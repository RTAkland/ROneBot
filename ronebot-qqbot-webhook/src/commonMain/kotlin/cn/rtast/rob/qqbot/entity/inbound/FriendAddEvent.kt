/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/15
 */


package cn.rtast.rob.qqbot.entity.inbound

import cn.rtast.rob.qqbot.entity.inbound.GroupAtMessageCreateEvent.Author
import cn.rtast.rob.qqbot.qbot.QQBotAction
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

public data class FriendAddEvent(
    val id: String,
    val d: AddEvent,
) {
    @Serializable
    public data class AddEvent(
        val timestamp: String,
        val openid: String,
        val author: Author,
    ) {
        @Transient
        lateinit var action: QQBotAction
    }
}