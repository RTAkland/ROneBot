/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/2/27
 */

package cn.rtast.rob.command

import cn.rtast.rob.OneBotFactory
import cn.rtast.rob.event.raw.message.GroupMessage
import cn.rtast.rob.event.raw.message.PrivateMessage
import cn.rtast.rob.event.raw.message.first
import cn.rtast.rob.session.GroupSession
import cn.rtast.rob.session.GroupSessionStruct
import cn.rtast.rob.session.PrivateSession
import cn.rtast.rob.session.PrivateSessionStruct


public abstract class BaseCommand: IBaseCommand<GroupMessage, PrivateMessage> {
    abstract override val commandNames: List<String>
    override suspend fun executeGroup(message: GroupMessage, args: List<String>) {}
    override suspend fun executePrivate(message: PrivateMessage, args: List<String>) {}

    final override suspend fun startGroupSession(
        message: GroupMessage,
        block: suspend (GroupSessionStruct<GroupMessage>) -> Unit,
    ) {
        val session = GroupSession(message, block, message.groupId)
        OneBotFactory.sessionManager.startGroupSession(message, session)
        session.__finished.await()
    }

    final override suspend fun startPrivateSession(
        message: PrivateMessage,
        block: suspend (PrivateSessionStruct<PrivateMessage>) -> Unit,
    ) {
        val session = PrivateSession(block, message)
        OneBotFactory.sessionManager.startPrivateSession(message, session)
        session.__finished.await()
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