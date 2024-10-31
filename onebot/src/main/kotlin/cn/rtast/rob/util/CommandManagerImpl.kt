/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/30
 */


package cn.rtast.rob.util

import cn.rtast.rob.entity.GroupMessage
import cn.rtast.rob.entity.PrivateMessage
import cn.rtast.rob.entity.command

class CommandManagerImpl internal constructor() : CommandManager {
    override val commands = mutableListOf<BaseCommand>()
    override val permissionCommands = mutableListOf<PermissionCommand>()

    override suspend fun register(command: BaseCommand) {
        commands.add(command)
    }

    override suspend fun register(command: PermissionCommand) {
        permissionCommands.add(command)
    }

    override suspend fun handlePrivate(message: PrivateMessage) {
        commands.find { command -> command.commandNames.any { it == message.command } }
            ?.handlePrivate(message, message.command)
        permissionCommands.find { command -> command.commandNames.any { it == message.command } }
            ?.handlePrivate(message, message.command)
    }

    override suspend fun handleGroup(message: GroupMessage) {
        commands.find { command -> command.commandNames.any { it == message.command } }
            ?.handleGroup(message, message.command)
        permissionCommands.find { command -> command.commandNames.any { it == message.command } }
            ?.handleGroup(message, message.command)
    }
}