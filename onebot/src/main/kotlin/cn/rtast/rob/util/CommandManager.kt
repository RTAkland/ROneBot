/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/27
 */


package cn.rtast.rob.util

import cn.rtast.rob.entity.BaseMessage
import cn.rtast.rob.entity.GroupMessage
import cn.rtast.rob.entity.PrivateMessage
import cn.rtast.rob.enums.ArrayMessageType
import cn.rtast.rob.util.ob.OneBotListener

class CommandManager {
    private val commands = mutableListOf<BaseCommand>()

    /**
     * 获取第一个类型为[ArrayMessageType.text]的消息段的内容
     * 然后使用空格分割之后返回
     */
    private fun getCommand(message: BaseMessage): String {
        val firstWord = message.message.find {
            it.type == ArrayMessageType.text
        }?.data?.text?.split(" ")?.firstOrNull() ?: ""
        return firstWord
    }

    /**
     * 注册命令
     */
    fun register(command: BaseCommand) = commands.add(command)

    /**
     * 处理私聊消息的命令
     */
    internal suspend fun handlePrivate(listener: OneBotListener, message: PrivateMessage) {
        val firstWord = this.getCommand(message)
        commands.find { command ->
            command.commandNames.any { it == firstWord }
        }?.handlePrivate(listener, message, firstWord)
    }

    /**
     * 处理群聊中的消息
     */
    internal suspend fun handleGroup(listener: OneBotListener, message: GroupMessage) {
        val firstWord = this.getCommand(message)
        commands.find { command ->
            command.commandNames.any { it == firstWord }
        }?.handleGroup(listener, message, firstWord)
    }
}