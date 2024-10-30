/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/30
 */


package cn.rtast.rob.util

import cn.rtast.rob.entity.GroupMessage
import cn.rtast.rob.entity.PrivateMessage
import cn.rtast.rob.entity.command
import cn.rtast.rob.util.ob.OneBotListener

class CommandManagerImpl internal constructor() : CommandManager {
    override val commands = mutableListOf<BaseCommand>()
    override val permissionCommands = mutableListOf<PermissionCommand>()

    override suspend fun register(command: BaseCommand) {
        commands.add(command)
    }

    override suspend fun register(command: PermissionCommand) {
        permissionCommands.add(command)
    }

    override suspend fun handlePrivate(listener: OneBotListener, message: PrivateMessage) {
        commands.find { command -> command.commandNames.any { it == message.command } }
            ?.handlePrivate(listener, message, message.command)
        permissionCommands.find { command -> command.commandNames.any { it == message.command } }
            ?.handlePrivate(listener, message, message.command)
    }

    override suspend fun handleGroup(listener: OneBotListener, message: GroupMessage) {
        commands.find { command -> command.commandNames.any { it == message.command } }
            ?.handleGroup(listener, message, message.command)
        permissionCommands.find { command -> command.commandNames.any { it == message.command } }
            ?.handleGroup(listener, message, message.command)
    }
}