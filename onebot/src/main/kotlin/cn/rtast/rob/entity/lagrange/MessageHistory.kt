/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/3
 */


package cn.rtast.rob.entity.lagrange

import cn.rtast.rob.entity.GroupMessage
import cn.rtast.rob.entity.PrivateMessage

data class GroupMessageHistory(
    val data: Data
) {
    data class Data(
        var messages: List<GroupMessage>
    )
}

data class PrivateMessageHistory(
    val data: Data
) {
    data class Data(
        val messages: List<PrivateMessage>
    )
}

