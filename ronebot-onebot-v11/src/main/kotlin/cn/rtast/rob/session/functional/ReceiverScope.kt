/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/1
 */

package cn.rtast.rob.session.functional

import cn.rtast.rob.ROneBotFactory
import cn.rtast.rob.entity.GroupMessage
import cn.rtast.rob.entity.PrivateMessage
import cn.rtast.rob.exceptions.NonFunctionalCommandHandlerException
import cn.rtast.rob.onebot.MessageChain
import cn.rtast.rob.session.IGroupReceiverScope
import cn.rtast.rob.session.IPrivateReceiverScope
import kotlin.reflect.KFunction

suspend fun createGroupSession(
    message: GroupMessage,
    command: KFunction<*>,
    block: suspend GroupReceiverScope.() -> Unit
) {
    val scope = GroupReceiverScope(message, block, command)
    scope.startGroupSession(command)
}

class GroupReceiverScope(
    override val message: GroupMessage,
    override val receiver: suspend GroupReceiverScope.() -> Unit,
    override val command: KFunction<*>,
) : IGroupReceiverScope<GroupMessage, GroupReceiverScope> {

    /**
     * 群聊开始会话
     */
    @Throws(NonFunctionalCommandHandlerException::class)
    suspend fun startGroupSession(func: KFunction<*>) {
        ROneBotFactory.functionalSessionManager.startGroupSession(message, func, this)
    }

    /**
     * 群聊结束会话
     */
    suspend fun skipGroupSession() =
        ROneBotFactory.functionalSessionManager.endGroupSession(message.sender)

    /**
     * 群聊拒绝这次会话回复的内容
     */
    suspend fun rejectGroupSession(reason: MessageChain) = message.reply(reason)
}

class PrivateReceiverScope(
    override val message: PrivateMessage,
    override val receiver: suspend PrivateReceiverScope.() -> Unit,
    override val command: KFunction<*>
) : IPrivateReceiverScope<PrivateMessage, PrivateReceiverScope> {
    /**
     * 私聊开始会话
     */
    @Throws(NonFunctionalCommandHandlerException::class)
    suspend fun startPrivateSession(func: KFunction<*>, receiver: PrivateReceiverScope) =
        ROneBotFactory.functionalSessionManager.startPrivateSession(message, func, receiver)

    /**
     * 私聊结束会话
     */
    suspend fun skipPrivateSession() =
        ROneBotFactory.functionalSessionManager.endPrivateSession(message.sender)

    /**
     * 私聊拒绝这次会话回复的内容
     */
    suspend fun rejectPrivateSession(reason: MessageChain) = message.reply(reason)

}