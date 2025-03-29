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
public interface ISessionManager<
        P : IPrivateMessage,
        G : IGroupMessage,
        PS : IPrivateSession<*>,
        GS : IGroupSession<*>,
        B : IBaseCommand<out IGroupMessage, out IPrivateMessage>,
        GSS : IGroupSender,
        PSS : IPrivateSender> {
    /**
     * 私聊正在进行的会话
     */
    public val privateActiveSessions: MutableMap<PSS, out IPrivateSession<*>>

    /**
     * 群聊正在进行的会话
     */
    public val groupActiveSessions: MutableMap<GSS, out IGroupSession<*>>

    /**
     * 开始群聊会话
     */
    public suspend fun startGroupSession(message: G, command: B): GS

    /**
     * 开始群聊会话但是带上一个初始参数
     */
    public suspend fun <T : Any> starterGroupSession(message: G, command: B, initArg: T): GS

    /**
     * 开始私聊会话
     */
    public suspend fun startPrivateSession(message: P, command: B): PS

    /**
     * 开启私聊会话但是带上一个初始参数
     */
    public suspend fun <T : Any> startPrivateSession(message: P, command: B, initArg: T): PS

    /**
     * 结束群聊会话
     */
    public suspend fun endGroupSession(sender: GSS) {
        groupActiveSessions[sender]?.endSession()
        groupActiveSessions.remove(sender)
    }

    /**
     * 结束私聊会话
     */
    public suspend fun endPrivateSession(sender: PSS) {
        privateActiveSessions[sender]?.endSession()
        privateActiveSessions.remove(sender)
    }

    /**
     * 获取私聊会话
     */
    public suspend fun getPrivateSession(sender: PSS): PS?

    /**
     * 获取私聊会话
     */
    public suspend fun <T> getTypedPrivateSession(sender: PSS): IPrivateSession<T>?

    /**
     * 获取群聊会话
     */
    public suspend fun getGroupSession(sender: GSS): GS?

    /**
     * 获取群聊会话
     */
    public suspend fun <T> getTypedGroupSession(sender: GSS): IGroupSession<T>?
}