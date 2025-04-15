/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 2025/4/15 16:33
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.session.v2

import cn.rtast.rob.event.raw.GroupSender
import cn.rtast.rob.event.raw.PrivateSender
import cn.rtast.rob.event.raw.message.GroupMessage
import cn.rtast.rob.event.raw.message.PrivateMessage

public class DefaultSessionManager internal constructor() {
    public val groupSessions: MutableMap<GroupSender, GroupSessionContext<*>> = mutableMapOf()
    public val privateSessions: MutableMap<PrivateSender, PrivateSessionContext<*>> = mutableMapOf()

    public suspend fun <T : Any> joinSessionWaitList(
        message: GroupMessage,
        initialData: T,
        lambda: suspend GroupSessionContext<T>.() -> Unit
    ) {
        val context = GroupSessionContext(initialData, message, lambda)
            .apply { this.lambda.invoke(this) }
        groupSessions[message.sender] = context
    }

    public fun <T : Any> joinSessionWaitList(
        message: PrivateMessage,
        initialData: T,
        lambda: suspend GroupSessionContext<T>.(T) -> Unit
    ) {
        privateSessions[message.sender] = PrivateSessionContext(initialData, message, lambda)
    }

    public fun getGroupSession(sender: GroupSender): GroupSessionContext<*>? = groupSessions[sender]
    public fun getPrivateSession(sender: PrivateSender): PrivateSessionContext<*>? = privateSessions[sender]

    internal suspend fun addMessageToChannel(message: GroupMessage) {
        groupSessions[message.sender]!!.channel.send(message)
    }

    internal suspend fun addMessageToChannel(message: PrivateMessage) {
        privateSessions[message.sender]!!.channel.send(message)
    }
}