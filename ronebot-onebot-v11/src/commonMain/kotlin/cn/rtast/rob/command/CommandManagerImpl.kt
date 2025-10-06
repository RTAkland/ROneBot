/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/30
 */

@file:Suppress("UNCHECKED_CAST")
@file:OptIn(ExperimentalROneBotApi::class)

package cn.rtast.rob.command

import cn.rtast.rob.OneBotFactory
import cn.rtast.rob.annotations.ExperimentalROneBotApi
import cn.rtast.rob.entity.IMessage
import cn.rtast.rob.enums.MessageType
import cn.rtast.rob.event.raw.message.*

public class CommandManagerImpl internal constructor() : CommandManager<BaseCommand, GroupMessage, PrivateMessage> {
    override val commands: MutableList<BaseCommand> = mutableListOf()
    override val groupDslCommands: MutableList<Map<List<String>, suspend (GroupMessage) -> Unit>> = mutableListOf()
    override val privateDslCommands: MutableList<Map<List<String>, suspend (PrivateMessage) -> Unit>> = mutableListOf()
    override var commandRegex: Regex = Regex("")

    private fun getCommand(message: BaseMessage): Pair<BaseCommand?, String?> {
        val splitMessage = message.text.split(" ")
        val commandText = splitMessage.first()
        val match = commandRegex.find(commandText)
        return if (match != null) {
            val command = match.groupValues[1]
            val parameters = splitMessage.drop(1).joinToString(" ")
            val matchedCommand = commands.find { it.commandNames.contains(command) }
            matchedCommand to parameters
        } else null to null
    }


    override suspend fun handlePrivate(message: PrivateMessage) {
        val activeSession = OneBotFactory.sessionManager.getPrivateSession(message)
        val (command, commandName) = this.getCommand(message)
        if (activeSession != null) {
            val args = message.first.split(" ").drop(1)
            OneBotFactory.sessionManager.handlePrivateSession(message, args)
            return
        }
        val commandString = commandRegex.find(message.text)?.value
        if (commandString != null) {
            privateDslCommands.flatMap { it.filter { (k, _) -> commandString in k }.values }
                .forEach { it.invoke(message) }
        }
        command?.let {
            OneBotFactory.interceptor.handlePrivateInterceptor(message, it) {
                if (command.interceptor != null) {
                    command.interceptor.handlePrivateInterceptor(message, command) { message ->
                        command.handlePrivate(message, commandName ?: "")
                    }
                } else {
                    command.handlePrivate(message, commandName ?: "")
                }
            }
        }
        message.command.dispatchBrigadierCommand(message, MessageType.private)
    }

    override suspend fun handleGroup(message: GroupMessage) {
        val activeSession = OneBotFactory.sessionManager.getGroupSession(message)
        val (command, commandName) = this.getCommand(message)
        if (activeSession != null && activeSession.groupId == message.groupId) {
            val args = message.first.split(" ").drop(1)
            OneBotFactory.sessionManager.handleGroupSession(message, args)
            return
        }
        val commandString = commandRegex.find(message.text)?.value
        if (commandString != null) {
            groupDslCommands.flatMap { it.filter { (k, _) -> commandString in k }.values }
                .forEach { it.invoke(message) }
        }
        command?.let {
            OneBotFactory.interceptor.handleGroupInterceptor(message, it) {
                if (command.interceptor != null) {
                    command.interceptor.handleGroupInterceptor(message, command) { message ->
                        command.handleGroup(message, commandName ?: "")
                    }
                } else {
                    command.handleGroup(message, commandName ?: "")
                }
            }
        }
        message.command.dispatchBrigadierCommand(message, MessageType.group)
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
        commandName: String, command: suspend (GroupMessage) -> Unit,
    ): Unit = this.registerGroupDsl(listOf(commandName), command)

    /**
     * 适用于只需要一个指令名的情况的私聊dsl指令
     */
    public suspend fun CommandManagerImpl.privateCommand(
        commandName: String, command: suspend (PrivateMessage) -> Unit,
    ): Unit = this.registerPrivateDsl(listOf(commandName), command)

    /**
     * 适用于需要多个指令名的情况的群聊dsl指令
     */
    public suspend fun CommandManagerImpl.groupCommand(
        aliases: List<String>,
        command: suspend (GroupMessage) -> Unit,
    ): Unit = this.registerGroupDsl(aliases, command)

    /**
     * 适用于需要多个指令名的情况的私聊dsl指令
     */
    public suspend fun CommandManagerImpl.privateCommand(
        aliases: List<String>,
        command: suspend (PrivateMessage) -> Unit,
    ): Unit = this.registerPrivateDsl(aliases, command)
}