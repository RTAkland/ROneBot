/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/3
 */


package cn.rtast.rob.entity.lagrange

import cn.rtast.rob.entity.GroupMessage
import cn.rtast.rob.entity.PrivateMessage

data class GroupMessageHistory(
    val data: MessageHistory
) {
    data class MessageHistory(
        var messages: List<GroupMessage>
    )
}

data class PrivateMessageHistory(
    val data: MessageHistory
) {
    data class MessageHistory(
        val messages: List<PrivateMessage>
    )
}

