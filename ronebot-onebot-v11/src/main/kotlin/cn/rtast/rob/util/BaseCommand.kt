/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/27
 */


package cn.rtast.rob.util

import cn.rtast.rob.ROneBotFactory
import cn.rtast.rob.entity.GroupMessage
import cn.rtast.rob.entity.PrivateMessage
import cn.rtast.rob.entity.text

/**
 * 继承[BaseCommand]来使用内置的指令管理器[CommandManagerImpl]
 * ```kotlin
 * class EchoCommand : BaseCommand() {
 *     override val commandNames = listOf("/echo", "/eee")
 *
 *     override suspend fun executeGroup(message: GroupMessage, args: List<String>) {
 *         val msg = CQMessageChain.Builder()
 *             .addReply(message.messageId)
 *             .addText(args.joinToString(" "))
 *             .build()
 *         message.action.sendGroupMessage(message.groupId, msg)
 *     }
 * }
 * ```
 */
abstract class BaseCommand {
    /**
     * 定义指令别名
     */
    abstract val commandNames: List<String>

    /**
     * 在群聊中触触发此接口, 注意: [args]参数是去掉了指令的部分, 如果输入的命令为`/echo Test`
     * 那么args参数的size属性就为1, 内容就是
     * ```kotlin
     * listOf("Test")
     * ```
     */
    protected open suspend fun executeGroup(message: GroupMessage, args: List<String>) {}

    /**
     * 群聊中触发此接口并附带匹配到的命令
     */
    protected open suspend fun executeGroup(message: GroupMessage, args: List<String>, matchedCommand: String) {}

    /**
     * 在私聊中触发此接口
     */
    protected open suspend fun executePrivate(message: PrivateMessage, args: List<String>) {}

    /**
     * 私聊中触发此接口并且附带匹配到的命令
     */
    protected open suspend fun executePrivate(message: PrivateMessage, args: List<String>, matchedCommand: String) {}

    internal open suspend fun handlePrivate(message: PrivateMessage, matchedCommand: String) {
        ROneBotFactory.totalCommandExecutionTimes++
        ROneBotFactory.privateCommandExecutionTimes++
        val args = message.text.substring(matchedCommand.length).split(" ")
        this.executePrivate(message, args)
        this.executePrivate(message, args, matchedCommand)
    }

    internal open suspend fun handleGroup(message: GroupMessage, matchedCommand: String) {
        ROneBotFactory.totalCommandExecutionTimes++
        ROneBotFactory.groupCommandExecutionTimes++
        val args = message.text.substring(matchedCommand.length).split(" ")
        this.executeGroup(message, args)
        this.executeGroup(message, args, matchedCommand)
    }
}