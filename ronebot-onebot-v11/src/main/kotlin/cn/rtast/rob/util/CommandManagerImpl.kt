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
import cn.rtast.rob.entity.text
import cn.rtast.rob.interceptor.defaultInterceptor
import cn.rtast.rob.interceptor.handleGroupInterceptor
import cn.rtast.rob.interceptor.handlePrivateInterceptor

class CommandManagerImpl internal constructor() : CommandManager {
    override val commands = mutableListOf<BaseCommand>()
    override val permissionCommands = mutableListOf<PermissionCommand>()
    private val interceptor
        get() = if (!ROneBotFactory.isInterceptorInitialized) defaultInterceptor else ROneBotFactory.interceptor
    private val commandList
        get() = commands.flatMap { it.commandNames }.toMutableList()
            .apply { addAll(permissionCommands.flatMap { it.commandNames }) }
    val commandRegex get() = Regex(commandList.joinToString("|") { "/?$it" })

    private fun getCommand(message: BaseMessage): Pair<Pair<BaseCommand?, String?>, Pair<PermissionCommand?, String?>> {
        val matchedCommand = commandRegex.find(message.text)?.value
        val command = commands.find { it.commandNames.contains(matchedCommand) }
        val permissionMatchCommand = permissionCommands.find { it.commandNames.contains(matchedCommand) }
        return (command to matchedCommand) to (permissionMatchCommand to matchedCommand)
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
            val commandName = command.second ?: ""
            val permissionCommandName = permissionCommand.second ?: ""
            command.first?.handlePrivate(it, commandName)
            permissionCommand.first?.handlePrivate(it, permissionCommandName)
        }
    }

    override suspend fun handleGroup(message: GroupMessage) {
        val (command, permissionCommand) = this.getCommand(message)
        handleGroupInterceptor(message, interceptor) {
            val commandName = command.second ?: ""
            val permissionCommandName = permissionCommand.second ?: ""
            command.first?.handleGroup(it, commandName)
            permissionCommand.first?.handleGroup(it, permissionCommandName)
        }
    }
}