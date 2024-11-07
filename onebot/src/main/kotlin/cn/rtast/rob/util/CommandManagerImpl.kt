/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/30
 */


package cn.rtast.rob.util

import cn.rtast.rob.ROneBotFactory
import cn.rtast.rob.entity.BaseMessage
import cn.rtast.rob.entity.GroupMessage
import cn.rtast.rob.entity.PrivateMessage
import cn.rtast.rob.entity.command
import cn.rtast.rob.interceptor.defaultInterceptor
import cn.rtast.rob.interceptor.handleGroupInterceptor
import cn.rtast.rob.interceptor.handlePrivateInterceptor

class CommandManagerImpl internal constructor() : CommandManager {
    override val commands = mutableListOf<BaseCommand>()
    override val permissionCommands = mutableListOf<PermissionCommand>()
    private val interceptor
        get() = if (!ROneBotFactory.isInterceptorInitialized) defaultInterceptor else ROneBotFactory.interceptor

    private fun getCommand(message: BaseMessage): Pair<BaseCommand?, PermissionCommand?> {
        val command = commands.find { command -> command.commandNames.any { it == message.command } }
        val permissionCommand =
            permissionCommands.find { command -> command.commandNames.any { it == message.command } }
        return command to permissionCommand
    }

    override suspend fun register(command: BaseCommand) {
        commands.add(command)
    }

    override suspend fun register(command: PermissionCommand) {
        permissionCommands.add(command)
    }

    override suspend fun handlePrivate(message: PrivateMessage) {
        val (command, permissionCommand) = this.getCommand(message)
        handlePrivateInterceptor(message, interceptor) {
            command?.handlePrivate(message, message.command)
            permissionCommand?.handlePrivate(message, message.command)
        }
    }

    override suspend fun handleGroup(message: GroupMessage) {
        val (command, permissionCommand) = this.getCommand(message)
        handleGroupInterceptor(message, interceptor) {
            command?.handleGroup(message, message.command)
            permissionCommand?.handleGroup(message, message.command)
        }
    }
}