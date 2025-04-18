/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/2/27
 */

@file:OptIn(ExperimentalROneBotApi::class)

package cn.rtast.rob.command

import cn.rtast.rob.OneBotFactory
import cn.rtast.rob.annotations.ExperimentalROneBotApi
import cn.rtast.rob.entity.IMessageChain
import cn.rtast.rob.event.raw.message.GroupMessage
import cn.rtast.rob.event.raw.message.PrivateMessage
import cn.rtast.rob.event.raw.message.first
import cn.rtast.rob.interceptor.CommandInterceptor
import cn.rtast.rob.onebot.MessageChain


public abstract class BaseCommand(
    public val interceptor: CommandInterceptor? = null
) : IBaseCommand<GroupMessage, PrivateMessage> {
    abstract override val commandNames: List<String>
    override suspend fun executeGroup(message: GroupMessage, args: List<String>) {}
    override suspend fun executePrivate(message: PrivateMessage, args: List<String>) {}
    override suspend fun onGroupSession(message: GroupMessage) {}
    override suspend fun onGroupSession(message: GroupMessage, initArg: Any) {}
    override suspend fun onPrivateSession(message: PrivateMessage) {}
    override suspend fun onPrivateSession(message: PrivateMessage, initArg: Any) {}

    final override suspend fun handlePrivate(
        message: PrivateMessage,
        matchedCommand: String,
    ) {
        OneBotFactory.totalCommandExecutionTimes++
        OneBotFactory.privateCommandExecutionTimes++
        val args = message.first.split(" ").drop(1)
        this.executePrivate(message, args)
    }

    final override suspend fun handleGroup(
        message: GroupMessage,
        matchedCommand: String,
    ) {
        OneBotFactory.totalCommandExecutionTimes++
        OneBotFactory.groupCommandExecutionTimes++
        val args = message.first.split(" ").drop(1)
        this.executeGroup(message, args)
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

    final override suspend fun <T : Any> GroupMessage.startSession(initArg: T) {
        OneBotFactory.sessionManager.startGroupSession(this, this@BaseCommand, initArg)
    }

    final override suspend fun PrivateMessage.startSession() {
        OneBotFactory.sessionManager.startPrivateSession(this, this@BaseCommand)
    }

    final override suspend fun <T : Any> PrivateMessage.startSession(initArg: T) {
        OneBotFactory.sessionManager.startPrivateSession(this, this@BaseCommand, initArg)
    }
}