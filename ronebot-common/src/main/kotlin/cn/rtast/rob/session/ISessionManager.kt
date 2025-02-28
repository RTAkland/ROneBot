/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/2/28
 */

package cn.rtast.rob.session

import cn.rtast.rob.command.IBaseCommand
import cn.rtast.rob.entity.IGroupMessage
import cn.rtast.rob.entity.IGroupSender
import cn.rtast.rob.entity.IPrivateMessage
import cn.rtast.rob.entity.IPrivateSender

interface ISessionManager<out P : IPrivateMessage,
        out G : IGroupMessage,
        PS : IPrivateSession,
        GS : IGroupSession,
        B : IBaseCommand<IGroupMessage, IPrivateMessage>,
        GSS : IGroupSender,
        PSS : IPrivateSender> {
    val privateActiveSessions: MutableMap<PSS, out IPrivateSession>
    val groupActiveSessions: MutableMap<GSS, out IGroupSession>

    suspend fun startGroupSession(message: @UnsafeVariance G, command: B): GS

    suspend fun startPrivateSession(message: @UnsafeVariance P, command: B): PS

    suspend fun endGroupSession(sender: GSS) {
        groupActiveSessions[sender]?.endSession()
        groupActiveSessions.remove(sender)
    }

    suspend fun endPrivateSession(sender: PSS) {
        privateActiveSessions[sender]?.endSession()
        privateActiveSessions.remove(sender)
    }

    suspend fun getPrivateSession(sender: PSS): PS?

    suspend fun getGroupSession(sender: GSS): GS?
}