/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/1
 */

package cn.rtast.rob.session

import cn.rtast.rob.entity.IGroupMessage
import cn.rtast.rob.entity.IGroupSender
import cn.rtast.rob.entity.IPrivateMessage
import cn.rtast.rob.entity.IPrivateSender
import kotlin.reflect.KFunction

interface IFunctionalSessionManager<out P : IPrivateMessage,
        out G : IGroupMessage,
        PS : IFunctionalPrivateSession,
        GS : IFunctionalGroupSession,
        GSS : IGroupSender,
        PSS : IPrivateSender> {
    val privateActiveSessions: MutableMap<PSS, out IFunctionalPrivateSession>
    val groupActiveSessions: MutableMap<GSS, out IFunctionalGroupSession>

    suspend fun startGroupSession(message: @UnsafeVariance G, command: KFunction<*>): GS

    suspend fun startPrivateSession(message: @UnsafeVariance P, command: KFunction<*>): PS

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