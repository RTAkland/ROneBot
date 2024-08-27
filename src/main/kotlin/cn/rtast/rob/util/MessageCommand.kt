/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/27
 */


package cn.rtast.rob.util

import cn.rtast.rob.enums.MessageType
import cn.rtast.rob.util.ob.OBMessage

class MessageCommand {
    private val commands = mutableListOf<BaseCommand>()

    fun register(command: BaseCommand) {
        commands.add(command)
    }

    fun handle(listener: OBMessage, type: MessageType, message: String) {
        commands.find { message.startsWith(it.commandName) }?.handle(listener, type, message)
    }
}