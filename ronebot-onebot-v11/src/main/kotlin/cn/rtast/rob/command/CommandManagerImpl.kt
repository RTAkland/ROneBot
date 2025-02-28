/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/30
 */


package cn.rtast.rob.command

import cn.rtast.rob.ROneBotFactory
import cn.rtast.rob.annotations.command.CommandMatchingStrategy
import cn.rtast.rob.annotations.command.functional.GroupCommandHandler
import cn.rtast.rob.annotations.command.functional.PrivateCommandHandler
import cn.rtast.rob.entity.*
import cn.rtast.rob.enums.MatchingStrategy
import cn.rtast.rob.interceptor.Interceptor
import cn.rtast.rob.interceptor.defaultInterceptor
import kotlin.reflect.KFunction
import kotlin.reflect.full.callSuspend
import kotlin.reflect.full.findAnnotation

class CommandManagerImpl internal constructor() : CommandManager<BaseCommand, GroupMessage, PrivateMessage> {
    override val commands = mutableListOf<BaseCommand>()
    override val functionCommands = mutableListOf<KFunction<*>>()
    private val interceptor
        get() =
            if (!ROneBotFactory.isInterceptorInitialized) defaultInterceptor else ROneBotFactory.interceptor
    override var commandRegex = Regex("")
    private val _interceptor = Interceptor<BaseCommand, GroupMessage, PrivateMessage>()

    /**
     * 先用[Regex]来获取文本中的命令, 如果没有匹配到就提前结束函数
     * 如果匹配到了再获取这个命令是否有[CommandMatchingStrategy]注解
     * 如果没有这个注解的话就默认给个[MatchingStrategy.SPACES], 表示
     * 使用空格来解析命令, 如果是[MatchingStrategy.REGEX]的话就用[Regex]
     * 来解析命令, [Regex]解析的命令范围更广, 空格和[Regex]都能匹配到
     * 但是缺点就是如果设置一个命令用[MatchingStrategy.REGEX]来匹配的话
     * 用户输入的却是空格解析的格式则会让[BaseCommand.handleGroup]的
     * args参数的第一个文本最前面加上一个空格
     */
    private fun getCommand(message: BaseMessage): Triple<BaseCommand?, String?, MatchingStrategy> {
        val matchedCommand = commandRegex.find(message.text)?.value
        val command = commands.find { it.commandNames.contains(matchedCommand) }
        if (command == null) return Triple(null, null, MatchingStrategy.SPACES)
        val matchMode =
            command::class.java.getAnnotation(CommandMatchingStrategy::class.java)?.mode ?: MatchingStrategy.SPACES
        when (matchMode) {
            MatchingStrategy.REGEX -> {
                return Triple(command, matchedCommand, MatchingStrategy.REGEX)
            }

            MatchingStrategy.SPACES -> {
                val command = commands.find { command -> command.commandNames.any { it == message.command } }
                return Triple(command, message.command, MatchingStrategy.SPACES)
            }
        }
    }

    override suspend fun handlePrivate(message: PrivateMessage) {
        val activeSession = ROneBotFactory.sessionManager.getPrivateSession(message.sender)
        val (command, commandName, matchingStrategy) = this.getCommand(message)
        if (activeSession != null) {
            activeSession.command.onPrivateSession(message)
            return
        }
        val functionalActiveSession = ROneBotFactory.functionalSessionManager.getPrivateSession(message.sender)
        if (functionalActiveSession != null) {
            functionalActiveSession.functionalCommand.callSuspend(message)
            return
        }
        val commandString = commandRegex.find(message.text)?.value
        if (commandString != null) {
            functionCommands.filter { func ->
                func.findAnnotation<PrivateCommandHandler>()?.aliases?.contains(commandString) == true
            }.forEach { func -> func.callSuspend(message) }
        }
        command?.let {
            _interceptor.handlePrivateInterceptor(message, interceptor, it) {
                command.handlePrivate(it, commandName ?: "", matchingStrategy)
            }
        }
    }

    override suspend fun handleGroup(message: GroupMessage) {
        val activeSession = ROneBotFactory.sessionManager.getGroupSession(message.sender)
        val (command, commandName, matchingStrategy) = this.getCommand(message)
        if (activeSession != null && activeSession.sender.groupId == message.groupId) {
            activeSession.command.onGroupSession(message)
            return
        }
        val functionalActiveSession = ROneBotFactory.functionalSessionManager.getGroupSession(message.sender)
        if (functionalActiveSession != null && functionalActiveSession.sender.groupId == message.groupId) {
            functionalActiveSession.functionalCommand.callSuspend(message)
            return
        }
        val commandString = commandRegex.find(message.text)?.value
        if (commandString != null) {
            functionCommands.filter { func ->
                func.findAnnotation<GroupCommandHandler>()?.aliases?.contains(commandString) == true
            }.forEach { func -> func.callSuspend(message) }
        }
        command?.let {
            _interceptor.handleGroupInterceptor(message, interceptor, it) {
                command.handleGroup(it, commandName ?: "", matchingStrategy)
            }
        }
    }
}