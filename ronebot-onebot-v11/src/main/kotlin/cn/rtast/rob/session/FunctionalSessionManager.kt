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
import cn.rtast.rob.onebot.MessageChain
import kotlin.reflect.KFunction

typealias FUNCTIONAL_SM = IFunctionalSessionManager<PrivateMessage, GroupMessage, FunctionalPrivateSession, FunctionalGroupSession, GroupSender, PrivateSender>

class FunctionalSessionManager : FUNCTIONAL_SM {
    override val privateActiveSessions = mutableMapOf<PrivateSender, FunctionalPrivateSession>()
    override val groupActiveSessions = mutableMapOf<GroupSender, FunctionalGroupSession>()

    override suspend fun startGroupSession(message: GroupMessage, command: KFunction<*>): FunctionalGroupSession {
        val session = FunctionalGroupSession(message.sessionId, message, message.sender, command)
        groupActiveSessions[message.sender] = session
        return session
    }

    override suspend fun startPrivateSession(message: PrivateMessage, command: KFunction<*>): FunctionalPrivateSession {
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

suspend fun KFunction<*>.startGroupSession(message: GroupMessage) =
    ROneBotFactory.functionalSessionManager.startGroupSession(message, this)

suspend fun KFunction<*>.startPrivateSession(message: PrivateMessage) =
    ROneBotFactory.functionalSessionManager.startPrivateSession(message, this)

suspend fun KFunction<*>.skipGroupSession(message: GroupMessage) =
    ROneBotFactory.functionalSessionManager.endGroupSession(message.sender)

suspend fun KFunction<*>.skipPrivateSession(message: PrivateMessage) =
    ROneBotFactory.functionalSessionManager.endPrivateSession(message.sender)

suspend fun KFunction<*>.rejectGroupSession(message: GroupMessage, reason: MessageChain) = message.reply(reason)

suspend fun KFunction<*>.rejectPrivateSession(message: PrivateMessage, reason: MessageChain) = message.reply(reason)