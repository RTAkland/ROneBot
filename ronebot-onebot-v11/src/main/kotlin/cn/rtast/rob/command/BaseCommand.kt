/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/2/27
 */

@file:Suppress("unused")

package cn.rtast.rob.command

import cn.rtast.rob.ROneBotFactory
import cn.rtast.rob.entity.*
import cn.rtast.rob.enums.MatchingStrategy
import cn.rtast.rob.onebot.MessageChain
import love.forte.plugin.suspendtrans.annotation.Api4J
import love.forte.plugin.suspendtrans.annotation.JvmAsync
import love.forte.plugin.suspendtrans.annotation.JvmBlocking


abstract class BaseCommand : IBaseCommand<GroupMessage, PrivateMessage> {
    abstract override val commandNames: List<String>

    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    override suspend fun executeGroup(message: GroupMessage, args: List<String>) {
    }

    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    override suspend fun executeGroup(message: GroupMessage, args: List<String>, matchedCommand: String) {
    }

    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    override suspend fun executePrivate(message: PrivateMessage, args: List<String>) {
    }

    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    override suspend fun executePrivate(message: PrivateMessage, args: List<String>, matchedCommand: String) {
    }

    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    override suspend fun onGroupSession(msg: GroupMessage) {
    }

    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    override suspend fun onPrivateSession(msg: PrivateMessage) {
    }

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

    final override suspend fun handleGroup(
        message: GroupMessage,
        matchedCommand: String,
        matchMode: MatchingStrategy
    ) {
        ROneBotFactory.totalCommandExecutionTimes++
        ROneBotFactory.groupCommandExecutionTimes++
        val args = when (matchMode) {
            MatchingStrategy.REGEX -> message.text.substring(matchedCommand.length).split(" ")
            MatchingStrategy.SPACES -> message.first.split(" ").drop(1)
        }
        this.executeGroup(message, args)
        this.executeGroup(message, args, matchedCommand)
    }

    @Api4J
    @JvmAsync
    @JvmBlocking
    suspend fun rejectJvm(message: GroupMessage, reason: IMessageChain) = message.reject(reason)

    @Api4J
    @JvmAsync
    @JvmBlocking
    suspend fun rejectJvm(message: PrivateMessage, reason: IMessageChain) = message.reject(reason)

    @Api4J
    @JvmAsync
    @JvmBlocking
    suspend fun skipSessionJvm(message: GroupMessage) = message.skipSession()

    @Api4J
    @JvmAsync
    @JvmBlocking
    suspend fun skipSessionJvm(message: PrivateMessage) = message.skipSession()

    @Api4J
    @JvmAsync
    @JvmBlocking
    suspend fun startSessionJvm(message: GroupMessage) = message.startSession()

    @Api4J
    @JvmAsync
    @JvmBlocking
    suspend fun startSessionJvm(message: PrivateMessage) = message.startSession()

    final override suspend fun GroupMessage.reject(reason: IMessageChain) {
        val chainReason = reason as MessageChain
        this.reply(chainReason)
    }

    final override suspend fun PrivateMessage.reject(reason: IMessageChain) {
        val chainReason = reason as MessageChain
        this.reply(chainReason)
    }

    final override suspend fun GroupMessage.skipSession() {
        ROneBotFactory.sessionManager.endGroupSession(this.sender)
    }

    final override suspend fun PrivateMessage.skipSession() {
        ROneBotFactory.sessionManager.endPrivateSession(this.sender)
    }

    final override suspend fun GroupMessage.startSession() {
        ROneBotFactory.sessionManager.startGroupSession(this, this@BaseCommand)
    }

    final override suspend fun PrivateMessage.startSession() {
        ROneBotFactory.sessionManager.startPrivateSession(this, this@BaseCommand)
    }
}