/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/27
 */


package cn.rtast.rob.command

import cn.rtast.rob.annotations.command.functional.GroupCommandHandler
import cn.rtast.rob.annotations.command.functional.PrivateCommandHandler
import cn.rtast.rob.entity.IGroupMessage
import cn.rtast.rob.entity.IPrivateMessage
import kotlin.reflect.KFunction
import kotlin.reflect.full.findAnnotation

/**
 * 内置的指令管理器, 可以分别处理群聊和私聊中的指令
 */
interface CommandManager<B : IBaseCommand<IGroupMessage, IPrivateMessage>, G : IGroupMessage, P : IPrivateMessage> {
    /**
     * 存储普通的命令
     */
    val commands: MutableList<B>

    /**
     * 函数式命令处理器的列表
     */
    val functionCommands: MutableList<KFunction<*>>

    /**
     * 生成的命令匹配正则表达式
     */
    var commandRegex: Regex

    /**
     * 生成Regex文本
     */
    suspend fun generateRegex() {
        val commandNames = commands.flatMap { it.commandNames }
        val groupFunctionCommandNames = functionCommands.flatMap { func ->
            func.findAnnotation<GroupCommandHandler>()?.aliases?.toList() ?: emptyList()
        }
        val privateFunctionCommandNames = functionCommands.flatMap { func ->
            func.findAnnotation<PrivateCommandHandler>()?.aliases?.toList() ?: emptyList()
        }
        commandRegex = Regex(
            (commandNames +
                    groupFunctionCommandNames +
                    privateFunctionCommandNames).joinToString("|") { "/?$it" })
    }

    /**
     * 注册一个命令
     */
    suspend fun register(command: B) {
        commands.add(command)
        this.generateRegex()
    }

    /**
     * 注册一个函数式的命令处理器
     * 使用`::xxxx`引用的方式传参
     */
    suspend fun registerFunction(func: KFunction<*>) {
        functionCommands.add(func)
        this.generateRegex()
    }

    /**
     * 私聊中触发
     */
    suspend fun handlePrivate(message: P)

    /**
     * 群聊中触发
     */
    suspend fun handleGroup(message: G)
}