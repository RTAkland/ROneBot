/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/2/28
 */

package cn.rtast.rob.session

import cn.rtast.rob.command.IBaseCommand
import cn.rtast.rob.entity.IGroupMessage
import cn.rtast.rob.entity.IPrivateMessage
import java.util.*

interface ISessionManager<out P : IPrivateMessage,
        out G : IGroupMessage,
        PS : IPrivateSession,
        GS : IGroupSession,
        B : IBaseCommand<IGroupMessage, IPrivateMessage>> {
    val privateActiveSessions: MutableMap<UUID, out IPrivateSession>
    val groupActiveSessions: MutableMap<UUID, out IGroupSession>

    fun startGroupSession(sessionId: UUID, message: @UnsafeVariance G, command: B): GS

    fun startPrivateSession(sessionId: UUID, message: @UnsafeVariance P, command: B): PS

    fun endGroupSession(sessionId: UUID) {
        groupActiveSessions[sessionId]?.endSession()
        groupActiveSessions.remove(sessionId)
    }

    fun endPrivateSession(sessionId: UUID) {
        privateActiveSessions[sessionId]?.endSession()
        privateActiveSessions.remove(sessionId)
    }

    fun getPrivateSession(sessionId: UUID): PS?

    fun getGroupSession(sessionId: UUID): GS?
}