/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/2/27
 */

package cn.rtast.rob.command

import cn.rtast.rob.OneBotFactory
import cn.rtast.rob.entity.IMessageChain
import cn.rtast.rob.event.raw.GroupMessage
import cn.rtast.rob.event.raw.PrivateMessage
import cn.rtast.rob.event.raw.first
import cn.rtast.rob.interceptor.CommandInterceptor
import cn.rtast.rob.onebot.MessageChain
import love.forte.plugin.suspendtrans.annotation.JvmAsync
import love.forte.plugin.suspendtrans.annotation.JvmBlocking


public abstract class BaseCommand(
    public val interceptor: CommandInterceptor? = null
) : IBaseCommand<GroupMessage, PrivateMessage> {
    abstract override val commandNames: List<String>

    @JvmBlocking(suffix = "JvmBlocking")
    @JvmAsync(suffix = "JvmAsync")
    override suspend fun executeGroup(message: GroupMessage, args: List<String>) {
    }

    @JvmBlocking(suffix = "JvmBlocking")
    @JvmAsync(suffix = "JvmAsync")
    override suspend fun executeGroup(message: GroupMessage, args: List<String>, matchedCommand: String) {
    }

    override suspend fun executePrivate(message: PrivateMessage, args: List<String>) {}

    @JvmBlocking(suffix = "JvmBlocking")
    @JvmAsync(suffix = "JvmAsync")
    override suspend fun executePrivate(message: PrivateMessage, args: List<String>, matchedCommand: String) {
    }

    @JvmBlocking(suffix = "JvmBlocking")
    @JvmAsync(suffix = "JvmAsync")
    override suspend fun onGroupSession(message: GroupMessage) {
    }

    @JvmBlocking(suffix = "JvmBlocking")
    @JvmAsync(suffix = "JvmAsync")
    override suspend fun onGroupSession(message: GroupMessage, initArg: Any) {
    }

    @JvmBlocking(suffix = "JvmBlocking")
    @JvmAsync(suffix = "JvmAsync")
    override suspend fun onPrivateSession(message: PrivateMessage) {
    }

    @JvmBlocking(suffix = "JvmBlocking")
    @JvmAsync(suffix = "JvmAsync")
    override suspend fun onPrivateSession(message: PrivateMessage, initArg: Any) {
    }

    @JvmBlocking(suffix = "JvmBlocking")
    @JvmAsync(suffix = "JvmAsync")
    final override suspend fun handlePrivate(
        message: PrivateMessage,
        matchedCommand: String,
    ) {
        OneBotFactory.totalCommandExecutionTimes++
        OneBotFactory.privateCommandExecutionTimes++
        val args = message.first.split(" ").drop(1)
        this.executePrivate(message, args)
        this.executePrivate(message, args, matchedCommand)
    }

    @JvmBlocking(suffix = "JvmBlocking")
    @JvmAsync(suffix = "JvmAsync")
    final override suspend fun handleGroup(
        message: GroupMessage,
        matchedCommand: String,
    ) {
        OneBotFactory.totalCommandExecutionTimes++
        OneBotFactory.groupCommandExecutionTimes++
        val args = message.first.split(" ").drop(1)
        this.executeGroup(message, args)
        this.executeGroup(message, args, matchedCommand)
    }

    @JvmBlocking(suffix = "JvmBlocking")
    @JvmAsync(suffix = "JvmAsync")
    final override suspend fun GroupMessage.reject(reason: IMessageChain) {
        val chainReason = reason as MessageChain
        this.reply(chainReason)
    }

    @JvmBlocking(suffix = "JvmBlocking")
    @JvmAsync(suffix = "JvmAsync")
    final override suspend fun PrivateMessage.reject(reason: IMessageChain) {
        val chainReason = reason as MessageChain
        this.reply(chainReason)
    }

    @JvmBlocking(suffix = "JvmBlocking")
    @JvmAsync(suffix = "JvmAsync")
    final override suspend fun GroupMessage.skipSession() {
        OneBotFactory.sessionManager.endGroupSession(this.sender)
    }

    @JvmBlocking(suffix = "JvmBlocking")
    @JvmAsync(suffix = "JvmAsync")
    final override suspend fun PrivateMessage.skipSession() {
        OneBotFactory.sessionManager.endPrivateSession(this.sender)
    }

    @JvmBlocking(suffix = "JvmBlocking")
    @JvmAsync(suffix = "JvmAsync")
    final override suspend fun GroupMessage.startSession() {
        OneBotFactory.sessionManager.startGroupSession(this, this@BaseCommand)
    }

    @JvmBlocking(suffix = "JvmBlocking")
    @JvmAsync(suffix = "JvmAsync")
    final override suspend fun <T : Any> GroupMessage.startSession(initArg: T) {
        OneBotFactory.sessionManager.starterGroupSession(this, this@BaseCommand, initArg)
    }

    @JvmBlocking(suffix = "JvmBlocking")
    @JvmAsync(suffix = "JvmAsync")
    final override suspend fun PrivateMessage.startSession() {
        OneBotFactory.sessionManager.startPrivateSession(this, this@BaseCommand)
    }

    @JvmBlocking(suffix = "JvmBlocking")
    @JvmAsync(suffix = "JvmAsync")
    final override suspend fun <T : Any> PrivateMessage.startSession(initArg: T) {
        OneBotFactory.sessionManager.startPrivateSession(this, this@BaseCommand, initArg)
    }
}