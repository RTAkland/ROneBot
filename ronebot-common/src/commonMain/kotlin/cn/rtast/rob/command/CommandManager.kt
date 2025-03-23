/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/27
 */


package cn.rtast.rob.command

import cn.rtast.rob.entity.IGroupMessage
import cn.rtast.rob.entity.IPrivateMessage

/**
 * 内置的指令管理器, 可以分别处理群聊和私聊中的指令
 */
public interface CommandManager<B : IBaseCommand<out IGroupMessage, out IPrivateMessage>, G : IGroupMessage, P : IPrivateMessage> {
    /**
     * 存储普通的命令
     */
    public val commands: MutableList<B>

    /**
     * dsl形式的群聊指令
     */
    public val groupDslCommands: MutableList<Map<List<String>, suspend (G) -> Unit>>

    /**
     * dsl形式的私聊指令
     */
    public val privateDslCommands: MutableList<Map<List<String>, suspend (P) -> Unit>>

    /**
     * 生成的命令匹配正则表达式
     */
    public var commandRegex: Regex

    /**
     * 生成Regex文本
     */
    public suspend fun generateRegex() {
        val commandNames = commands.flatMap { it.commandNames }
        val groupDslCommandNames = groupDslCommands.flatMap { it.keys }.flatten()
        val privateDslCommandNames = privateDslCommands.flatMap { it.keys }.flatten()
        commandRegex = Regex(
            (commandNames +
                    groupDslCommandNames +
                    privateDslCommandNames)
                .joinToString("|") { "/?$it" })
    }

    /**
     * 注册一个命令
     */
    public suspend fun register(command: B) {
        commands.add(command)
        this.generateRegex()
    }

    /**
     * 注册群聊的dsl的指令
     */
    public suspend fun registerGroupDsl(commands: List<String>, block: suspend (G) -> Unit) {
        groupDslCommands.add(mapOf(commands to block))
        this.generateRegex()
    }

    /**
     * 注册私聊的dsl的指令
     */
    public suspend fun registerPrivateDsl(commands: List<String>, block: suspend (P) -> Unit) {
        privateDslCommands.add(mapOf(commands to block))
        this.generateRegex()
    }

    /**
     * 私聊中触发
     */
    public suspend fun handlePrivate(message: P)

    /**
     * 群聊中触发
     */
    public suspend fun handleGroup(message: G)
}