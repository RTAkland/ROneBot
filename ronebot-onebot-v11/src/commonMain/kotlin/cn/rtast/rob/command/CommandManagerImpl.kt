/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/30
 */

@file:Suppress("UNCHECKED_CAST")

package cn.rtast.rob.command

import cn.rtast.rob.OneBotFactory
import cn.rtast.rob.entity.IMessage
import cn.rtast.rob.enums.MessageType
import cn.rtast.rob.event.raw.message.*

public class CommandManagerImpl internal constructor() : CommandManager<BaseCommand, GroupMessage, PrivateMessage> {
    override val commands: MutableList<BaseCommand> = mutableListOf<BaseCommand>()
    override val groupDslCommands: MutableList<Map<List<String>, suspend (GroupMessage) -> Unit>> = mutableListOf()
    override val privateDslCommands: MutableList<Map<List<String>, suspend (PrivateMessage) -> Unit>> = mutableListOf()
    override var commandRegex: Regex = Regex("")

    private fun getCommand(message: BaseMessage): Pair<BaseCommand?, String?> {
        val matchedCommand = commandRegex.find(message.text)?.value
        val command = commands.find { it.commandNames.contains(matchedCommand) }
        return command to message.command
    }

    override suspend fun handlePrivate(message: PrivateMessage) {
        val activeSession = OneBotFactory.sessionManager.getPrivateSession(message.sender)
        val (command, commandName) = this.getCommand(message)
        if (activeSession != null) {
            activeSession.command.onPrivateSession(message)
            activeSession.command.onPrivateSession(message, activeSession.initArgType)
            return
        }
        val commandString = commandRegex.find(message.text)?.value
        if (commandString != null) {
            commandString.dispatchBrigadierCommand(message, MessageType.private)
            privateDslCommands.flatMap { it.filter { (k, _) -> commandString in k }.values }
                .forEach { it.invoke(message) }
        }
        command?.let {
            OneBotFactory.interceptor.handlePrivateInterceptor(message, it) {
                if (command.interceptor != null) {
                    command.interceptor.handlePrivateInterceptor(message, command) {
                        command.handlePrivate(it, commandName ?: "")
                    }
                } else {
                    command.handlePrivate(it, commandName ?: "")
                }
            }
        }
    }

    override suspend fun handleGroup(message: GroupMessage) {
        val activeSession = OneBotFactory.sessionManager.getGroupSession(message.sender)
        val (command, commandName) = this.getCommand(message)
        if (activeSession != null && activeSession.sender.groupId == message.groupId) {
            activeSession.command.onGroupSession(message)
            activeSession.command.onGroupSession(message, activeSession.initArgType)
            return
        }
        val commandString = commandRegex.find(message.text)?.value
        if (commandString != null) {
            commandString.dispatchBrigadierCommand(message, MessageType.group)
            groupDslCommands.flatMap { it.filter { (k, _) -> commandString in k }.values }
                .forEach { it.invoke(message) }
        }
        command?.let {
            OneBotFactory.interceptor.handleGroupInterceptor(message, it) {
                if (command.interceptor != null) {
                    command.interceptor.handleGroupInterceptor(message, command) {
                        command.handleGroup(it, commandName ?: "")
                    }
                } else {
                    command.handleGroup(it, commandName ?: "")
                }
            }
        }
    }

    private fun String.dispatchBrigadierCommand(message: IMessage, type: MessageType) {
        dispatchBrigadierCommand(this, message, type)
    }

    /**
     * 可以直接对一个属性进行invoke
     */
    public suspend operator fun invoke(block: suspend (CommandManagerImpl).() -> Unit) {
        this.block()
    }

    /**
     * 适用于只需要一个指令名的情况的群聊dsl指令
     */
    public suspend fun CommandManagerImpl.groupCommand(
        commandName: String, command: suspend (GroupMessage) -> Unit
    ) = this.registerGroupDsl(listOf(commandName), command)

    /**
     * 适用于只需要一个指令名的情况的私聊dsl指令
     */
    public suspend fun CommandManagerImpl.privateCommand(
        commandName: String, command: suspend (PrivateMessage) -> Unit
    ) = this.registerPrivateDsl(listOf(commandName), command)

    /**
     * 适用于需要多个指令名的情况的群聊dsl指令
     */
    public suspend fun CommandManagerImpl.groupCommand(
        aliases: List<String>,
        command: suspend (GroupMessage) -> Unit
    ) = this.registerGroupDsl(aliases, command)

    /**
     * 适用于需要多个指令名的情况的私聊dsl指令
     */
    public suspend fun CommandManagerImpl.privateCommand(
        aliases: List<String>,
        command: suspend (PrivateMessage) -> Unit
    ) = this.registerPrivateDsl(aliases, command)
}