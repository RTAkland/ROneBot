/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/2/27
 */

package cn.rtast.rob.command

import cn.rtast.rob.ROneBotFactory
import cn.rtast.rob.entity.GroupMessage
import cn.rtast.rob.entity.IBaseCommand
import cn.rtast.rob.entity.PrivateMessage
import cn.rtast.rob.entity.first
import cn.rtast.rob.entity.text
import cn.rtast.rob.enums.MatchingStrategy


abstract class BaseCommand : IBaseCommand<GroupMessage, PrivateMessage> {
    abstract override val commandNames: List<String>
    override suspend fun executeGroup(message: GroupMessage, args: List<String>) {}
    override suspend fun executeGroup(message: GroupMessage, args: List<String>, matchedCommand: String) {}
    override suspend fun executePrivate(message: PrivateMessage, args: List<String>) {}
    override suspend fun executePrivate(message: PrivateMessage, args: List<String>, matchedCommand: String) {}
    final override suspend fun handlePrivate(
        message: PrivateMessage,
        matchedCommand: String,
        matchMode: MatchingStrategy
    ) {
        ROneBotFactory.totalCommandExecutionTimes++
        ROneBotFactory.privateCommandExecutionTimes++
        val args = when (matchMode) {
            MatchingStrategy.REGEX -> message.text.substring(matchedCommand.length).split(" ")
            MatchingStrategy.SPACES -> message.first.split(" ").drop(1)
        }
        this.executePrivate(message, args)
        this.executePrivate(message, args, matchedCommand)
    }

    final override suspend fun handleGroup(message: GroupMessage, matchedCommand: String, matchMode: MatchingStrategy) {
        ROneBotFactory.totalCommandExecutionTimes++
        ROneBotFactory.groupCommandExecutionTimes++
        val args = when (matchMode) {
            MatchingStrategy.REGEX -> message.text.substring(matchedCommand.length).split(" ")
            MatchingStrategy.SPACES -> message.first.split(" ").drop(1)
        }
        this.executeGroup(message, args)
        this.executeGroup(message, args, matchedCommand)
    }
}