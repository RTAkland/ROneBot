/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/30
 */

@file:Suppress("UNCHECKED_CAST")

package cn.rtast.rob.command

import cn.rtast.rob.DEFAULT_FUNCTIONAL_CLASS_NAME
import cn.rtast.rob.OneBotFactory
import cn.rtast.rob.annotations.command.CommandMatchingStrategy
import cn.rtast.rob.annotations.command.functional.GroupCommandHandler
import cn.rtast.rob.annotations.command.functional.PrivateCommandHandler
import cn.rtast.rob.annotations.command.functional.session.GroupSessionHandler
import cn.rtast.rob.annotations.command.functional.session.PrivateSessionHandler
import cn.rtast.rob.enums.MatchingStrategy
import cn.rtast.rob.event.raw.BaseMessage
import cn.rtast.rob.event.raw.GroupMessage
import cn.rtast.rob.event.raw.PrivateMessage
import cn.rtast.rob.event.raw.command
import cn.rtast.rob.event.raw.text
import cn.rtast.rob.interceptor.FunctionalCommandInterceptor
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.full.callSuspend
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.memberFunctions

public class CommandManagerImpl internal constructor() : CommandManager<BaseCommand, GroupMessage, PrivateMessage> {
    override val commands: MutableList<BaseCommand> = mutableListOf<BaseCommand>()
    override val functionCommands: MutableList<KFunction<*>> = mutableListOf<KFunction<*>>()
    override var commandRegex: Regex = Regex("")

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
        val activeSession = OneBotFactory.sessionManager.getPrivateSession(message.sender)
        val (command, commandName, matchingStrategy) = this.getCommand(message)
        if (activeSession != null) {
            activeSession.command.onPrivateSession(message)
            return
        }
        val functionalActiveSession = OneBotFactory.functionalSessionManager.getPrivateSession(message.sender)
        functionalActiveSession?.functionalCommand?.findAnnotation<PrivateCommandHandler>()
            ?.session?.let { clazz ->
                clazz.memberFunctions.forEach {
                    if (it.findAnnotation<PrivateSessionHandler>() != null) {
                        it.callSuspend(clazz.createInstance(), message)
                        return
                    }
                }
            }
        val commandString = commandRegex.find(message.text)?.value
        if (commandString != null) {
            functionCommands.filter { func ->
                func.findAnnotation<PrivateCommandHandler>()?.aliases?.contains(commandString) == true
            }.forEach { func ->
                OneBotFactory.functionalInterceptor.handlePrivateInterceptor(message, func) {
                    val interceptor = func.findAnnotation<PrivateCommandHandler>()!!.interceptor
                    if (interceptor.qualifiedName == DEFAULT_FUNCTIONAL_CLASS_NAME) {
                        func.callSuspend(message)
                    } else {
                        interceptor as KClass<FunctionalCommandInterceptor<PrivateMessage>>
                        interceptor.createInstance().handleInterceptor(message) {
                            func.callSuspend(message)
                        }
                    }
                    OneBotFactory.totalCommandExecutionTimes++
                    OneBotFactory.privateCommandExecutionTimes++
                }
            }
        }
        command?.let {
            OneBotFactory.interceptor.handlePrivateInterceptor(message, it) {
                if (command.interceptor != null) {
                    command.interceptor.handlePrivateInterceptor(message, command) {
                        command.handlePrivate(it, commandName ?: "", matchingStrategy)
                    }
                } else {
                    command.handlePrivate(it, commandName ?: "", matchingStrategy)
                }
            }
        }
    }

    override suspend fun handleGroup(message: GroupMessage) {
        val activeSession = OneBotFactory.sessionManager.getGroupSession(message.sender)
        val (command, commandName, matchingStrategy) = this.getCommand(message)
        if (activeSession != null && activeSession.sender.groupId == message.groupId) {
            activeSession.command.onGroupSession(message)
            return
        }
        val functionalActiveSession = OneBotFactory.functionalSessionManager.getGroupSession(message.sender)
        functionalActiveSession?.functionalCommand?.findAnnotation<GroupCommandHandler>()
            ?.session?.let { clazz ->
                clazz.memberFunctions.forEach {
                    if (it.findAnnotation<GroupSessionHandler>() != null) {
                        it.callSuspend(clazz.createInstance(), message)
                        return
                    }
                }
            }
        val commandString = commandRegex.find(message.text)?.value
        if (commandString != null) {
            functionCommands.filter { func ->
                func.findAnnotation<GroupCommandHandler>()?.aliases?.contains(commandString) == true
            }.forEach { func ->
                OneBotFactory.functionalInterceptor.handleGroupInterceptor(message, func) {
                    val interceptor = func.findAnnotation<GroupCommandHandler>()!!.interceptor
                    if (interceptor.qualifiedName == DEFAULT_FUNCTIONAL_CLASS_NAME) {
                        func.callSuspend(message)
                    } else {
                        interceptor as KClass<FunctionalCommandInterceptor<GroupMessage>>
                        interceptor.createInstance().handleInterceptor(message) {
                            func.callSuspend(message)
                        }
                    }
                    OneBotFactory.totalCommandExecutionTimes++
                    OneBotFactory.groupCommandExecutionTimes++
                }
            }
        }
        command?.let {
            OneBotFactory.interceptor.handleGroupInterceptor(message, it) {
                if (command.interceptor != null) {
                    command.interceptor.handleGroupInterceptor(message, command) {
                        command.handleGroup(it, commandName ?: "", matchingStrategy)
                    }
                } else {
                    command.handleGroup(it, commandName ?: "", matchingStrategy)
                }
            }
        }
    }
}