/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/2/28
 */

@file:OptIn(ExperimentalUuidApi::class)

package cn.rtast.rob.session

import cn.rtast.rob.command.BaseCommand
import cn.rtast.rob.event.raw.GroupMessage
import cn.rtast.rob.event.raw.GroupSender
import cn.rtast.rob.event.raw.PrivateMessage
import cn.rtast.rob.event.raw.PrivateSender
import kotlin.uuid.ExperimentalUuidApi

private typealias SM = ISessionManager<PrivateMessage, GroupMessage, PrivateSession, GroupSession, BaseCommand, GroupSender, PrivateSender>

public class SessionManager : SM {
    override val privateActiveSessions: MutableMap<PrivateSender, PrivateSession> =
        mutableMapOf<PrivateSender, PrivateSession>()
    override val groupActiveSessions: MutableMap<GroupSender, GroupSession> = mutableMapOf<GroupSender, GroupSession>()

    override suspend fun startGroupSession(message: GroupMessage, command: BaseCommand): GroupSession {
        val session = GroupSession(message.sessionId!!, message, command, message.sender)
        groupActiveSessions[message.sender] = session
        return session
    }

    override suspend fun startPrivateSession(message: PrivateMessage, command: BaseCommand): PrivateSession {
        val session = PrivateSession(message.sessionId!!, message, command, message.sender)
        privateActiveSessions[message.sender] = session
        return session
    }

    override suspend fun getPrivateSession(sender: PrivateSender): PrivateSession? {
        return privateActiveSessions[sender]
    }

    override suspend fun getGroupSession(sender: GroupSender): GroupSession? {
        return groupActiveSessions[sender]
    }
}