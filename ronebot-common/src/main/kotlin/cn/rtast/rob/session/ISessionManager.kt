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

/**
 * 一个会话管理器的接口, 提供了一些基本的对会话的操作
 * 例如: 获取会话、开始会话、结束会话
 */
interface ISessionManager<
        out P : IPrivateMessage,
        out G : IGroupMessage,
        PS : IPrivateSession,
        GS : IGroupSession,
        B : IBaseCommand<IGroupMessage, IPrivateMessage>,
        GSS : IGroupSender,
        PSS : IPrivateSender> {
    /**
     * 私聊正在进行的会话
     */
    val privateActiveSessions: MutableMap<PSS, out IPrivateSession>

    /**
     * 群聊正在进行的会话
     */
    val groupActiveSessions: MutableMap<GSS, out IGroupSession>

    /**
     * 开始群聊会话
     */
    suspend fun startGroupSession(message: @UnsafeVariance G, command: B): GS

    /**
     * 开始私聊会话
     */
    suspend fun startPrivateSession(message: @UnsafeVariance P, command: B): PS

    /**
     * 结束群聊会话
     */
    suspend fun endGroupSession(sender: GSS) {
        groupActiveSessions[sender]?.endSession()
        groupActiveSessions.remove(sender)
    }

    /**
     * 结束私聊会话
     */
    suspend fun endPrivateSession(sender: PSS) {
        privateActiveSessions[sender]?.endSession()
        privateActiveSessions.remove(sender)
    }

    /**
     * 获取私聊会话
     */
    suspend fun getPrivateSession(sender: PSS): PS?

    /**
     * 获取群聊会话
     */
    suspend fun getGroupSession(sender: GSS): GS?
}