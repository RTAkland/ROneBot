/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/18/25, 11:02 PM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.milky.command

import cn.rtast.rob.command.CommandManager
import cn.rtast.rob.milky.event.ws.raw.ReceiveMessage
import cn.rtast.rob.milky.event.ws.raw.text

public class CommandManagerImpl internal constructor() : CommandManager<BaseCommand, ReceiveMessage, ReceiveMessage> {
    override val groupDslCommands: MutableList<Map<List<String>, suspend (ReceiveMessage) -> Unit>>
        get() = TODO("Not yet implemented")
    override val privateDslCommands: MutableList<Map<List<String>, suspend (ReceiveMessage) -> Unit>>
        get() = TODO("Not yet implemented")

    override suspend fun handlePrivate(message: ReceiveMessage): Nothing = TODO("Not yet implemented")
    override suspend fun handleGroup(message: ReceiveMessage): Nothing = TODO("Not yet implemented")

    // 上面的全都没用

    override val commands: MutableList<BaseCommand> = mutableListOf()
    override var commandRegex: Regex = Regex("")
    public val commandsDsl: MutableList<Triple<List<String>, List<BaseCommand.ExecuteType>, suspend (CommandRequest) -> Unit>> =
        mutableListOf()

    override suspend fun generateRegex() {
        val commandNames = (commandsDsl.flatMap { it.first } + commands.flatMap { it.commandNames }).distinct()
        commandRegex = Regex("^(${commandNames.joinToString("|")})$")
    }

    private fun ReceiveMessage.getCommand(): Pair<BaseCommand?, String?> {
        val splitMessage = this.text.split(" ")
        val commandText = splitMessage.first()
        val match = commandRegex.find(commandText)
        if (match != null && match.groupValues.size > 1) {
            val command = match.groupValues[1]
            val parameters = splitMessage.drop(1).joinToString(" ")
            val matchedCommand = commands.find { it.commandNames.contains(command) }
            return matchedCommand to parameters
        }
        return null to null
    }

    public suspend fun registerCommandByDsl(
        aliases: List<String>,
        type: List<BaseCommand.ExecuteType>,
        block: suspend (CommandRequest) -> Unit,
    ): Unit = commandsDsl.add(Triple(aliases, type, block)).apply { generateRegex() }.let { }

    public suspend fun handleCommand(message: ReceiveMessage) {
        val splitWords = message.text.split(" ")
        val args = splitWords.drop(1)
        message.getCommand().first?.let { command ->
            command.onExecute(message, message.messageScene.toExecuteType(), args)
            command.type.forEach { type ->
                when (type) {
                    BaseCommand.ExecuteType.Group -> command.executeGroup(message, args)
                    BaseCommand.ExecuteType.Friend -> command.executePrivate(message, args)
                    BaseCommand.ExecuteType.Temp -> command.executeTemp(message, args)
                }
            }
        }
        val commandText = commandRegex.find(message.text)?.value
        commandText?.let { text ->
            commandsDsl.forEach { (keywords, types, block) ->
                if (text in keywords && (types.isEmpty() || message.messageScene.toExecuteType() in types)) {
                    block(CommandRequest(message, args))
                }
            }
        }
    }
}