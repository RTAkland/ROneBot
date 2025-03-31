/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/15
 */


package cn.rtast.rob.qqbot.enums.internal

import kotlinx.serialization.Serializable

@Serializable
internal enum class MessageDispatchType {
    GROUP_AT_MESSAGE_CREATE, FRIEND_ADD, C2C_MESSAGE_CREATE,
    FRIEND_DEL, GROUP_DEL_ROBOT, GROUP_ADD_ROBOT,
    C2C_MSG_RECEIVE, C2C_MSG_REJECT, GROUP_MSG_REJECT, GROUP_MSG_RECEIVE
}