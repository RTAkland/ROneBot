/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/15
 */


package cn.rtast.rob.qqbot.entity.inbound

import cn.rtast.rob.annotations.ExcludeField
import cn.rtast.rob.qqbot.entity.inbound.GroupAtMessageCreate.Author
import cn.rtast.rob.qqbot.qbot.QQBotAction

data class FriendAddEvent(
    val d: AddEvent,
) {
    data class AddEvent(
        @ExcludeField
        var action: QQBotAction,
        val timestamp: String,
        val openid: String,
        val author: Author,
    )
}