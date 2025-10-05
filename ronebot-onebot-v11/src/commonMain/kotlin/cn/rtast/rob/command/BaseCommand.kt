/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/2/27
 */

@file:OptIn(ExperimentalROneBotApi::class)

package cn.rtast.rob.command

import cn.rtast.rob.OneBotFactory
import cn.rtast.rob.annotations.ExperimentalROneBotApi
import cn.rtast.rob.event.raw.message.GroupMessage
import cn.rtast.rob.event.raw.message.PrivateMessage
import cn.rtast.rob.event.raw.message.first
import cn.rtast.rob.interceptor.CommandInterceptor
import cn.rtast.rob.session.IGroupSession
import cn.rtast.rob.session.IPrivateSession


public abstract class BaseCommand(
    public val interceptor: CommandInterceptor? = null,
) : IBaseCommand<GroupMessage, PrivateMessage, IGroupSession, IPrivateSession> {
    abstract override val commandNames: List<String>
    override suspend fun executeGroup(message: GroupMessage, args: List<String>) {}
    override suspend fun executePrivate(message: PrivateMessage, args: List<String>) {}
    final override suspend fun <T> startGroupSession(init: T, block: IGroupSession) {

    }

    final override suspend fun <T> startPrivateSession(init: T, block: IPrivateSession) {

    }

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
}