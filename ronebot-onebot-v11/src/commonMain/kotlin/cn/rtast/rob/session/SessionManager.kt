/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/2/28
 */

@file:OptIn(ExperimentalUuidApi::class)

package cn.rtast.rob.session

import cn.rtast.rob.OneBotFactory
import cn.rtast.rob.entity.IMessageChain
import cn.rtast.rob.event.raw.GroupSender
import cn.rtast.rob.event.raw.PrivateSender
import cn.rtast.rob.event.raw.message.GroupMessage
import cn.rtast.rob.event.raw.message.PrivateMessage
import cn.rtast.rob.onebot.MessageChain
import kotlin.uuid.ExperimentalUuidApi


public class SessionManager : ISessionManager<GroupSender, PrivateSender, GroupMessage, PrivateMessage> {
    override val privateSessions: MutableMap<PrivateSender, PrivateSession<PrivateMessage>> = mutableMapOf()
    override val groupSessions: MutableMap<GroupSender, GroupSession<GroupMessage>> = mutableMapOf()

    override fun getGroupSession(message: GroupMessage): GroupSession<GroupMessage>? =
        groupSessions[message.sender]

    override fun getPrivateSession(message: PrivateMessage): PrivateSession<PrivateMessage>? =
        privateSessions[message.sender]

    override suspend fun handleGroupSession(
        message: GroupMessage,
        args: List<String>,
    ) {
        getGroupSession(message)?.block?.invoke(GroupSessionStruct(args, message, OneBotFactory.sessionManager))
    }

    override suspend fun handlePrivateSession(
        message: PrivateMessage,
        args: List<String>,
    ) {
        getPrivateSession(message)?.block?.invoke(PrivateSessionStruct(args, message, OneBotFactory.sessionManager))
    }

    override suspend fun startGroupSession(
        message: GroupMessage,
        block: GroupSession<GroupMessage>,
    ) {
        groupSessions[message.sender] = block
    }

    override suspend fun startPrivateSession(
        message: PrivateMessage,
        block: PrivateSession<PrivateMessage>,
    ) {
        privateSessions[message.sender] = block
    }

    override suspend fun endGroupSession(
        message: GroupMessage,
        feedback: IMessageChain?,
    ) {
        val session = getGroupSession(message) ?: return
        groupSessions.remove(message.sender)
        feedback?.let { message.reply(it as MessageChain) }
        session.__finished.complete(Unit)
    }

    override suspend fun endPrivateSession(
        message: PrivateMessage,
        feedback: IMessageChain?,
    ) {
        val session = getPrivateSession(message) ?: return
        privateSessions.remove(message.sender)
        feedback?.let { message.reply(it as MessageChain) }
        session.__finished.complete(Unit)
    }

    override suspend fun rejectGroupSession(
        message: GroupMessage,
        feedback: IMessageChain,
    ) {
        message.reply(feedback as MessageChain)
    }

    override suspend fun rejectPrivateSession(
        message: PrivateMessage,
        feedback: IMessageChain,
    ) {
        message.reply(feedback as MessageChain)
    }
}