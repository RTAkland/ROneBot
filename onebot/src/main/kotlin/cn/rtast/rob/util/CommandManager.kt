/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/27
 */


package cn.rtast.rob.util

import cn.rtast.rob.entity.GroupMessage
import cn.rtast.rob.entity.PrivateMessage
import cn.rtast.rob.entity.command
import cn.rtast.rob.util.ob.OneBotListener

class CommandManager {
    private val commands = mutableListOf<BaseCommand>()

    /**
     * 注册命令
     */
    fun register(command: BaseCommand) = commands.add(command)

    /**
     * 处理私聊消息的命令
     */
    internal suspend fun handlePrivate(listener: OneBotListener, message: PrivateMessage) {
        commands.find { command -> command.commandNames.any { it == message.command } }
            ?.handlePrivate(listener, message, message.command)
    }

    /**
     * 处理群聊中的消息
     */
    internal suspend fun handleGroup(listener: OneBotListener, message: GroupMessage) {
        commands.find { command -> command.commandNames.any { it == message.command } }
            ?.handleGroup(listener, message, message.command)
    }
}