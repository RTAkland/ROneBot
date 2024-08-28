/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/27
 */


package cn.rtast.rob.util

import cn.rtast.rob.entity.GroupMessage
import cn.rtast.rob.entity.PrivateMessage
import cn.rtast.rob.util.ob.OBMessage

abstract class BaseCommand {
    abstract val commandName: String

    protected open suspend fun executeGroup(listener: OBMessage, message: GroupMessage, args: List<String>) {}

    protected open suspend fun executePrivate(listener: OBMessage, message: PrivateMessage, args: List<String>) {}

    suspend fun handlePrivate(listener: OBMessage, message: PrivateMessage) {
        val args = message.rawMessage.split(" ").drop(1)
        this.executePrivate(listener, message, args)
    }

    suspend fun handleGroup(listener: OBMessage, message: GroupMessage) {
        val args = message.rawMessage.split(" ").drop(1)
        this.executeGroup(listener, message, args)
    }
}