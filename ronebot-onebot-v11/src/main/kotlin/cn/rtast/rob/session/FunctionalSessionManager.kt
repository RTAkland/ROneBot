/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/1
 */

@file:Suppress("unused")

package cn.rtast.rob.session

import cn.rtast.rob.ROneBotFactory
import cn.rtast.rob.entity.GroupMessage
import cn.rtast.rob.entity.GroupSender
import cn.rtast.rob.entity.PrivateMessage
import cn.rtast.rob.entity.PrivateSender
import cn.rtast.rob.exceptions.NonFunctionalCommandHandlerException
import cn.rtast.rob.onebot.MessageChain
import kotlin.reflect.KFunction

internal typealias FUNCTIONAL_SM = IFunctionalSessionManager<PrivateMessage, GroupMessage, FunctionalPrivateSession, FunctionalGroupSession, GroupSender, PrivateSender>

public class FunctionalSessionManager : FUNCTIONAL_SM {
    override val privateActiveSessions: MutableMap<PrivateSender, FunctionalPrivateSession> =
        mutableMapOf<PrivateSender, FunctionalPrivateSession>()
    override val groupActiveSessions: MutableMap<GroupSender, FunctionalGroupSession> =
        mutableMapOf<GroupSender, FunctionalGroupSession>()

    @Throws(NonFunctionalCommandHandlerException::class)
    override suspend fun startGroupSession(message: GroupMessage, command: KFunction<*>): FunctionalGroupSession {
        this.inspectGroupCommandHandlerAnnotation(command)
        val session = FunctionalGroupSession(message.sessionId, message, message.sender, command)
        groupActiveSessions[message.sender] = session
        return session
    }

    @Throws(NonFunctionalCommandHandlerException::class)
    override suspend fun startPrivateSession(message: PrivateMessage, command: KFunction<*>): FunctionalPrivateSession {
        this.inspectPrivateCommandHandlerAnnotation(command)
        val session = FunctionalPrivateSession(message.sessionId, message, message.sender, command)
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
public suspend fun startGroupSession(message: GroupMessage, func: KFunction<*>): FunctionalGroupSession =
    ROneBotFactory.functionalSessionManager.startGroupSession(message, func)

/**
 * 私聊开始会话
 */
@Throws(NonFunctionalCommandHandlerException::class)
public suspend fun startPrivateSession(message: PrivateMessage, func: KFunction<*>): FunctionalPrivateSession =
    ROneBotFactory.functionalSessionManager.startPrivateSession(message, func)

/**
 * 群聊结束会话
 */
public suspend fun skipGroupSession(message: GroupMessage): Unit =
    ROneBotFactory.functionalSessionManager.endGroupSession(message.sender)

/**
 * 私聊结束会话
 */
public suspend fun skipPrivateSession(message: PrivateMessage): Unit =
    ROneBotFactory.functionalSessionManager.endPrivateSession(message.sender)

/**
 * 群聊拒绝这次会话回复的内容
 */
public suspend fun rejectGroupSession(message: GroupMessage, reason: MessageChain): Long? = message.reply(reason)

/**
 * 私聊拒绝这次会话回复的内容
 */
public suspend fun rejectPrivateSession(message: PrivateMessage, reason: MessageChain): Long? = message.reply(reason)