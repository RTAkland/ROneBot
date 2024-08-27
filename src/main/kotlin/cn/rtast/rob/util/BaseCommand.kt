/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/27
 */


package cn.rtast.rob.util

import cn.rtast.rob.enums.MessageType
import cn.rtast.rob.util.ob.OBMessage

abstract class BaseCommand {
    abstract val commandName: String

    protected abstract fun execute(listener: OBMessage, type: MessageType, args: List<String>?)

    fun handle(listener: OBMessage, type: MessageType, message: String) {
        val args = message.split(" ").drop(1)
        this.execute(listener, type, args)
    }
}