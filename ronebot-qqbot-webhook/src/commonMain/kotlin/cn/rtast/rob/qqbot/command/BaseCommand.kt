/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/2/27
 */

package cn.rtast.rob.qqbot.command

import cn.rtast.rob.command.IBaseCommand
import cn.rtast.rob.qqbot.QBotFactory
import cn.rtast.rob.qqbot.entity.inbound.C2CMessageCreateEvent
import cn.rtast.rob.qqbot.entity.inbound.GroupAtMessageCreateEvent

public abstract class BaseCommand : IBaseCommand<GroupAtMessageCreateEvent, C2CMessageCreateEvent> {
    abstract override val commandNames: List<String>

    override suspend fun executeGroup(message: GroupAtMessageCreateEvent, args: List<String>) {
    }

    override suspend fun executePrivate(message: C2CMessageCreateEvent, args: List<String>) {
    }

    final override suspend fun handlePrivate(
        message: C2CMessageCreateEvent,
        matchedCommand: String,
    ) {
        QBotFactory.totalCommandExecutionTimes++
        QBotFactory.privateCommandExecutionTimes++
        val args = message.d.content.split(matchedCommand).drop(1)
        this.executePrivate(message, args)
    }

    final override suspend fun handleGroup(
        message: GroupAtMessageCreateEvent,
        matchedCommand: String,
    ) {
        QBotFactory.totalCommandExecutionTimes++
        QBotFactory.groupCommandExecutionTimes++
        val args = message.d.content.split(matchedCommand).drop(1)
        this.executeGroup(message, args)
    }
}