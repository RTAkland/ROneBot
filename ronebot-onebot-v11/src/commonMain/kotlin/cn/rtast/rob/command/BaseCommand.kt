/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/2/27
 */

package cn.rtast.rob.command

import cn.rtast.rob.OneBotFactory
import cn.rtast.rob.entity.*
import cn.rtast.rob.enums.MatchingStrategy
import cn.rtast.rob.event.raw.GroupMessage
import cn.rtast.rob.event.raw.PrivateMessage
import cn.rtast.rob.event.raw.first
import cn.rtast.rob.event.raw.text
import cn.rtast.rob.interceptor.CommandInterceptor
import cn.rtast.rob.onebot.MessageChain


public abstract class BaseCommand(
    public val interceptor: CommandInterceptor? = null
) : IBaseCommand<GroupMessage, PrivateMessage> {
    abstract override val commandNames: List<String>
    override suspend fun executeGroup(message: GroupMessage, args: List<String>) {}
    override suspend fun executeGroup(message: GroupMessage, args: List<String>, matchedCommand: String) {}
    override suspend fun executePrivate(message: PrivateMessage, args: List<String>) {}
    override suspend fun executePrivate(message: PrivateMessage, args: List<String>, matchedCommand: String) {}
    override suspend fun onGroupSession(msg: GroupMessage) {}
    override suspend fun onPrivateSession(msg: PrivateMessage) {}

    final override suspend fun handlePrivate(
        message: PrivateMessage,
        matchedCommand: String,
        matchMode: MatchingStrategy
    ) {
        OneBotFactory.totalCommandExecutionTimes++
        OneBotFactory.privateCommandExecutionTimes++
        val args = when (matchMode) {
            MatchingStrategy.REGEX -> message.text.substring(matchedCommand.length).split(" ")
            MatchingStrategy.SPACES -> message.first.split(" ").drop(1)
        }
        this.executePrivate(message, args)
        this.executePrivate(message, args, matchedCommand)
    }

    final override suspend fun handleGroup(
        message: GroupMessage,
        matchedCommand: String,
        matchMode: MatchingStrategy
    ) {
        OneBotFactory.totalCommandExecutionTimes++
        OneBotFactory.groupCommandExecutionTimes++
        val args = when (matchMode) {
            MatchingStrategy.REGEX -> message.text.substring(matchedCommand.length).split(" ")
            MatchingStrategy.SPACES -> message.first.split(" ").drop(1)
        }
        this.executeGroup(message, args)
        this.executeGroup(message, args, matchedCommand)
    }

    final override suspend fun GroupMessage.reject(reason: IMessageChain) {
        val chainReason = reason as MessageChain
        this.reply(chainReason)
    }

    final override suspend fun PrivateMessage.reject(reason: IMessageChain) {
        val chainReason = reason as MessageChain
        this.reply(chainReason)
    }

    final override suspend fun GroupMessage.skipSession() {
        OneBotFactory.sessionManager.endGroupSession(this.sender)
    }

    final override suspend fun PrivateMessage.skipSession() {
        OneBotFactory.sessionManager.endPrivateSession(this.sender)
    }

    final override suspend fun GroupMessage.startSession() {
        OneBotFactory.sessionManager.startGroupSession(this, this@BaseCommand)
    }

    final override suspend fun PrivateMessage.startSession() {
        OneBotFactory.sessionManager.startPrivateSession(this, this@BaseCommand)
    }
}