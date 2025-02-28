/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/2/28
 */

package cn.rtast.rob.session

import cn.rtast.rob.command.BaseCommand
import cn.rtast.rob.entity.GroupMessage
import cn.rtast.rob.entity.PrivateMessage
import java.util.*

typealias SM = ISessionManager<PrivateMessage, GroupMessage, PrivateSession, GroupSession, BaseCommand>

class SessionManager : SM {
    override val privateActiveSessions = mutableMapOf<UUID, PrivateSession>()
    override val groupActiveSessions = mutableMapOf<UUID, GroupSession>()
    override fun startGroupSession(sessionId: UUID, message: GroupMessage, command: BaseCommand): GroupSession {
        val session = GroupSession(sessionId, message, command, message.sender)
        groupActiveSessions[sessionId] = session
        return session
    }

    override fun startPrivateSession(sessionId: UUID, message: PrivateMessage, command: BaseCommand): PrivateSession {
        val session = PrivateSession(sessionId, message, command, message.sender)
        privateActiveSessions[sessionId] = session
        return session
    }

    override fun getPrivateSession(sessionId: UUID): PrivateSession? {
        return privateActiveSessions[sessionId]
    }

    override fun getGroupSession(sessionId: UUID): GroupSession? {
        return groupActiveSessions[sessionId]
    }
}