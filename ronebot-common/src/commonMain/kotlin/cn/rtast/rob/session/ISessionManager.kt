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
public interface ISessionManager<G: IGroupMessage, P: IPrivateMessage> {
    public val privateSessions: MutableMap<P, IPrivateSession<P>>
    public val groupSessions: MutableMap<G, IGroupSession<G>>

    public suspend fun handleGroupSession(message: G, args: List<String>)
    public suspend fun handlePrivateSession(message: P, args: List<String>)
}