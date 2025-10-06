/*
 * Copyright © 2025 RTAkland
 * Date: 10/7/25, 5:18 AM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */


package cn.rtast.rob.milky.session

import cn.rtast.rob.entity.IMessageChain
import cn.rtast.rob.milky.event.ws.raw.RawMessageReceiveEvent
import cn.rtast.rob.milky.milky.MessageChain
import cn.rtast.rob.session.*

public class SessionManager :
    ISessionManager<SimulationSender, SimulationSender, RawMessageReceiveEvent.IncomingMessage, RawMessageReceiveEvent.IncomingMessage> {
    @Deprecated("", level = DeprecationLevel.HIDDEN)
    override val privateSessions: MutableMap<SimulationSender, PrivateSession<RawMessageReceiveEvent.IncomingMessage>>
        get() = TODO()

    @Deprecated("", level = DeprecationLevel.HIDDEN)
    override val groupSessions: MutableMap<SimulationSender, GroupSession<RawMessageReceiveEvent.IncomingMessage>>
        get() = TODO()

    /**
     * 包括了临时会话和私聊
     */
    private val _privateSessions: MutableMap<Long, PrivateSession<RawMessageReceiveEvent.IncomingMessage>> =
        mutableMapOf()

    private val _groupSessions: MutableMap<Long, GroupSession<RawMessageReceiveEvent.IncomingMessage>> =
        mutableMapOf()

    override fun getGroupSession(message: RawMessageReceiveEvent.IncomingMessage): GroupSession<RawMessageReceiveEvent.IncomingMessage>? {
        return _groupSessions[message.senderId]
    }

    override fun getPrivateSession(message: RawMessageReceiveEvent.IncomingMessage): PrivateSession<RawMessageReceiveEvent.IncomingMessage>? {
        return _privateSessions[message.senderId]
    }

    public fun getTempSession(message: RawMessageReceiveEvent.IncomingMessage): PrivateSession<RawMessageReceiveEvent.IncomingMessage>? =
        getPrivateSession(message)

    override suspend fun handleGroupSession(
        message: RawMessageReceiveEvent.IncomingMessage,
        args: List<String>,
    ) {
        getGroupSession(message)?.block?.invoke(GroupSessionStruct(args, message, this))
    }

    override suspend fun handlePrivateSession(
        message: RawMessageReceiveEvent.IncomingMessage,
        args: List<String>,
    ) {
        getPrivateSession(message)?.block?.invoke(PrivateSessionStruct(args, message, this))
    }

    public suspend fun handleTempSession(
        message: RawMessageReceiveEvent.IncomingMessage,
        args: List<String>,
    ): Unit = handlePrivateSession(message, args)

    override suspend fun endGroupSession(
        message: RawMessageReceiveEvent.IncomingMessage,
        feedback: IMessageChain?,
    ) {
        val session = getGroupSession(message) ?: return
        _groupSessions.remove(message.senderId)
        feedback?.let { message.reply(it as MessageChain) }
        session.__finished.complete(Unit)
    }

    override suspend fun endPrivateSession(
        message: RawMessageReceiveEvent.IncomingMessage,
        feedback: IMessageChain?,
    ) {
        val session = getPrivateSession(message) ?: return
        _privateSessions.remove(message.senderId)
        feedback?.let { message.reply(it as MessageChain) }
        session.__finished.complete(Unit)
    }

    public suspend fun endTempSession(
        message: RawMessageReceiveEvent.IncomingMessage,
        feedback: IMessageChain?,
    ): Unit = endPrivateSession(message, feedback)

    override suspend fun rejectGroupSession(
        message: RawMessageReceiveEvent.IncomingMessage,
        feedback: IMessageChain,
    ) {
        message.reply(feedback as MessageChain)
    }

    override suspend fun rejectPrivateSession(
        message: RawMessageReceiveEvent.IncomingMessage,
        feedback: IMessageChain,
    ) {
        message.reply(feedback as MessageChain)
    }

    public suspend fun rejectTempSession(
        message: RawMessageReceiveEvent.IncomingMessage,
        feedback: IMessageChain,
    ): Unit = rejectPrivateSession(message, feedback)

    override suspend fun startGroupSession(
        message: RawMessageReceiveEvent.IncomingMessage,
        block: GroupSession<RawMessageReceiveEvent.IncomingMessage>,
    ) {
        _groupSessions[message.senderId] = block
    }

    override suspend fun startPrivateSession(
        message: RawMessageReceiveEvent.IncomingMessage,
        block: PrivateSession<RawMessageReceiveEvent.IncomingMessage>,
    ) {
        _privateSessions[message.senderId] = block
    }

    public suspend fun startTempSession(
        message: RawMessageReceiveEvent.IncomingMessage,
        block: PrivateSession<RawMessageReceiveEvent.IncomingMessage>,
    ): Unit = startPrivateSession(message, block)
}