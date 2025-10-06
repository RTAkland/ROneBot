/*
 * Copyright © 2025 RTAkland
 * Date: 10/6/25, 2:37 PM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */


package cn.rtast.rob.session

import cn.rtast.rob.entity.IGroupMessage
import cn.rtast.rob.entity.IMessageChain
import cn.rtast.rob.entity.IPrivateMessage

/**
 * 结束会话
 * 可选的对消息进行回复
 * 群聊
 */
public suspend fun <T : IGroupMessage> GroupSessionStruct<T>.accept(feedback: IMessageChain? = null): Unit? =
    manager.endGroupSession(message, feedback)

/**
 * 结束会话
 * 可选的对消息进行回复
 * 私聊
 */
public suspend fun <T : IPrivateMessage> PrivateSessionStruct<T>.accept(feedback: IMessageChain? = null): Unit? =
    manager.endPrivateSession(message, feedback)

/**
 * 拒绝本次输入, 不结束会话继续接收内容
 * 并回复内容
 * 群聊
 */
public suspend fun <T : IGroupMessage> GroupSessionStruct<T>.reject(feedback: IMessageChain): Unit? =
    manager.rejectGroupSession(message, feedback)

/**
 * 拒绝本次输入, 不结束会话继续接收内容
 * 并回复内容
 * 私聊
 */
public suspend fun <T : IPrivateMessage> PrivateSessionStruct<T>.reject(feedback: IMessageChain): Unit? =
    manager.rejectPrivateSession(message, feedback)
