/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/1
 */

@file:Suppress("unused")

package cn.rtast.rob.session.functional

import cn.rtast.rob.ROneBotFactory
import cn.rtast.rob.entity.GroupMessage
import cn.rtast.rob.entity.GroupSender
import cn.rtast.rob.entity.PrivateMessage
import cn.rtast.rob.entity.PrivateSender
import cn.rtast.rob.exceptions.NonFunctionalCommandHandlerException
import cn.rtast.rob.onebot.MessageChain
import cn.rtast.rob.session.IFunctionalSessionManager
import kotlin.reflect.KFunction

typealias FUNCTIONAL_SM = IFunctionalSessionManager<PrivateMessage, GroupMessage, FunctionalPrivateSession, FunctionalGroupSession, GroupSender, PrivateSender, GroupReceiverScope, PrivateReceiverScope>

class FunctionalSessionManager : FUNCTIONAL_SM {
    override val privateActiveSessions = mutableMapOf<PrivateSender, FunctionalPrivateSession>()
    override val groupActiveSessions = mutableMapOf<GroupSender, FunctionalGroupSession>()

    @Throws(NonFunctionalCommandHandlerException::class)
    override suspend fun startGroupSession(
        message: GroupMessage,
        command: KFunction<*>,
        receiver: GroupReceiverScope,
    ): FunctionalGroupSession {
        this.inspectGroupCommandHandlerAnnotation(command)
        val session = FunctionalGroupSession(message.sessionId, message, message.sender, command, receiver)
        groupActiveSessions[message.sender] = session
        return session
    }

    @Throws(NonFunctionalCommandHandlerException::class)
    override suspend fun startPrivateSession(
        message: PrivateMessage,
        command: KFunction<*>,
        receiver: PrivateReceiverScope
    ): FunctionalPrivateSession {
        this.inspectPrivateCommandHandlerAnnotation(command)
        val session = FunctionalPrivateSession(message.sessionId, message, message.sender, command, receiver)
        privateActiveSessions[message.sender] = session
        return session
    }

    override suspend fun getPrivateSession(sender: PrivateSender): FunctionalPrivateSession? {
        return privateActiveSessions[sender]
    }

    override suspend fun getGroupSession(sender: GroupSender): FunctionalGroupSession? {
        return groupActiveSessions[sender]
    }
}

/**
 * 群聊开始会话
 */
@Throws(NonFunctionalCommandHandlerException::class)
suspend fun KFunction<*>.startGroupSession(message: GroupMessage, receiver: GroupReceiverScope) =
    ROneBotFactory.functionalSessionManager.startGroupSession(message, this, receiver)

/**
 * 私聊开始会话
 */
@Throws(NonFunctionalCommandHandlerException::class)
suspend fun KFunction<*>.startPrivateSession(message: PrivateMessage, receiver: PrivateReceiverScope) =
    ROneBotFactory.functionalSessionManager.startPrivateSession(message, this, receiver)

/**
 * 群聊结束会话
 */
suspend fun KFunction<*>.skipGroupSession(message: GroupMessage) =
    ROneBotFactory.functionalSessionManager.endGroupSession(message.sender)

/**
 * 私聊结束会话
 */
suspend fun KFunction<*>.skipPrivateSession(message: PrivateMessage) =
    ROneBotFactory.functionalSessionManager.endPrivateSession(message.sender)

/**
 * 群聊拒绝这次会话回复的内容
 */
suspend fun KFunction<*>.rejectGroupSession(message: GroupMessage, reason: MessageChain) = message.reply(reason)

/**
 * 私聊拒绝这次会话回复的内容
 */
suspend fun KFunction<*>.rejectPrivateSession(message: PrivateMessage, reason: MessageChain) = message.reply(reason)