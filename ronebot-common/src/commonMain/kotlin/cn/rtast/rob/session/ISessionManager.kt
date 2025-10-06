/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/2/28
 */

package cn.rtast.rob.session

import cn.rtast.rob.entity.*

/**
 * 一个会话管理器的接口, 提供了一些基本的对会话的操作
 * 例如: 获取会话、开始会话、结束会话
 */
public interface ISessionManager<
        GS : IGroupSender, PS : IPrivateSender,
        GM : IGroupMessage, PM : IPrivateMessage,
        > {

    public val privateSessions: MutableMap<PS, PrivateSession<PM>>
    public val groupSessions: MutableMap<GS, GroupSession<GM>>

    public fun getGroupSession(message: GM): (GroupSession<GM>)?
    public fun getPrivateSession(message: PM): (PrivateSession<PM>)?

    public suspend fun handleGroupSession(message: GM, args: List<String>): Unit?
    public suspend fun handlePrivateSession(message: PM, args: List<String>): Unit?

    public suspend fun startGroupSession(message: GM, block: GroupSession<GM>): Unit?
    public suspend fun startPrivateSession(message: PM, block: PrivateSession<PM>): Unit?

    public suspend fun endGroupSession(message: GM, feedback: IMessageChain?): Unit?
    public suspend fun endPrivateSession(message: PM, feedback: IMessageChain?): Unit?

    public suspend fun rejectGroupSession(message: GM, feedback: IMessageChain): Unit?
    public suspend fun rejectPrivateSession(message: PM, feedback: IMessageChain): Unit?
}