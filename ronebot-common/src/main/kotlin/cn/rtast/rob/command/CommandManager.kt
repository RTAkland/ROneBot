/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/27
 */


package cn.rtast.rob.command

import cn.rtast.rob.entity.IBaseCommand
import cn.rtast.rob.entity.IGroupMessage
import cn.rtast.rob.entity.IPrivateMessage

/**
 * 内置的指令管理器, 可以分别处理群聊和私聊中的指令
 */
interface CommandManager<B : IBaseCommand<IGroupMessage, IPrivateMessage>, G : IGroupMessage, P : IPrivateMessage> {
    /**
     * 存储普通的命令
     */
    val commands: MutableList<B>

    /**
     * 生成的命令匹配正则表达式
     */
    var commandRegex: Regex

    /**
     * 生成Regex文本
     */
    suspend fun generateRegex() {
        commandRegex = Regex(commands.flatMap { it.commandNames }.joinToString("|") { "/?$it" })
    }

    /**
     * 注册一个命令
     */
    suspend fun register(command: B) {
        commands.add(command)
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