/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/18/25, 11:02 PM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.milky.command

import cn.rtast.rob.annotations.ExperimentalROneBotApi
import cn.rtast.rob.command.IBaseCommand
import cn.rtast.rob.milky.MilkyBotFactory
import cn.rtast.rob.milky.event.ws.raw.ReceiveMessage
import cn.rtast.rob.session.GroupSession
import cn.rtast.rob.session.GroupSessionStruct
import cn.rtast.rob.session.PrivateSession
import cn.rtast.rob.session.PrivateSessionStruct

public typealias TempSession = PrivateSessionStruct<ReceiveMessage>

public abstract class BaseCommand : IBaseCommand<ReceiveMessage, ReceiveMessage> {
    public enum class ExecuteType {
        Group, Friend, Temp
    }

    public abstract val type: List<ExecuteType>

    public abstract suspend fun onExecute(message: ReceiveMessage, type: ExecuteType, args: List<String>)
    public override suspend fun executeGroup(message: ReceiveMessage, args: List<String>) {}
    public override suspend fun executePrivate(message: ReceiveMessage, args: List<String>) {}
    public override suspend fun executeTemp(message: ReceiveMessage, args: List<String>) {}

    /**
     * 开启群聊会话
     */
    @OptIn(ExperimentalROneBotApi::class)
    final override suspend fun startGroupSession(
        message: ReceiveMessage,
        block: suspend (GroupSessionStruct<ReceiveMessage>) -> Unit
    ) {
        val session = GroupSession(message, block, message.group!!.groupId)
        MilkyBotFactory.sessionManager.startGroupSession(message, session)
        session.__finished.await()
    }

    /**
     * 开启私聊会话
     */
    @OptIn(ExperimentalROneBotApi::class)
    final override suspend fun startPrivateSession(
        message: ReceiveMessage,
        block: suspend (PrivateSessionStruct<ReceiveMessage>) -> Unit
    ) {
        val session = PrivateSession(block, message)
        MilkyBotFactory.sessionManager.startPrivateSession(message, session)
        session.__finished.await()
    }

    /**
     * 开启临时消息会话
     */
    @OptIn(ExperimentalROneBotApi::class)
    public suspend fun startTempSession(
        message: ReceiveMessage,
        block: suspend (TempSession) -> Unit
    ): Unit = startPrivateSession(message, block)
}