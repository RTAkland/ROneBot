/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/2/27
 */

package cn.rtast.rob.qqbot.command

import cn.rtast.rob.command.CommandManager
import cn.rtast.rob.enums.MatchingStrategy
import cn.rtast.rob.qqbot.QBotFactory
import cn.rtast.rob.qqbot.entity.inbound.C2CMessageCreateEvent
import cn.rtast.rob.qqbot.entity.inbound.GroupAtMessageCreateEvent
import cn.rtast.rob.qqbot.entity.inbound.command
import kotlin.reflect.KFunction

public class CommandManagerImpl : CommandManager<BaseCommand, GroupAtMessageCreateEvent, C2CMessageCreateEvent> {
    override val commands: MutableList<BaseCommand> = mutableListOf<BaseCommand>()
    override var commandRegex: Regex = Regex("")
    override val functionCommands: MutableList<KFunction<*>> = mutableListOf<KFunction<*>>()

    override suspend fun handlePrivate(message: C2CMessageCreateEvent) {
        val matchedCommand = commandRegex.find(message.d.content)?.value
        val command = commands.find { command -> command.commandNames.any { it == message.command } }
        command?.let {
            QBotFactory.interceptor.handlePrivateInterceptor(message, it) {
                command.handlePrivate(it, matchedCommand ?: "", MatchingStrategy.SPACES)
            }
        }
    }

    override suspend fun handleGroup(message: GroupAtMessageCreateEvent) {
        val matchedCommand = commandRegex.find(message.d.content)?.value
        val command = commands.find { command -> command.commandNames.any { it == message.command } }
        command?.let {
            QBotFactory.interceptor.handleGroupInterceptor(message, it) {
                command.handleGroup(it, matchedCommand ?: "", MatchingStrategy.SPACES)
            }
        }
    }
}