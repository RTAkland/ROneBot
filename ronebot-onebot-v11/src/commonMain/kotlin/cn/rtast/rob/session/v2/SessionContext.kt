/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 2025/4/15 16:33
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.session.v2

import cn.rtast.rob.event.raw.message.GroupMessage
import cn.rtast.rob.event.raw.message.PrivateMessage
import cn.rtast.rob.onebot.MessageChain
import kotlinx.coroutines.channels.Channel

public data class GroupSessionContext<T : Any>(
    val initialData: T,
    val message: GroupMessage,
    val lambda: suspend GroupSessionContext<T>.() -> Unit,
    var isActive: Boolean = true,
) {
    public val channel: Channel<GroupMessage> = Channel(Channel.BUFFERED)

    public suspend fun waitNextMessage(block: (GroupMessage) -> Unit) {
        for (d in channel) {
            block(d)
        }
    }

    public suspend fun confirm(reason: MessageChain? = null) {
        isActive = false
        if (reason == null) return
        message.reply(reason)
    }

    public suspend fun reject(reason: MessageChain? = null) {
        if (reason == null) return
        message.reply(reason)
    }
}

public data class PrivateSessionContext<T : Any>(
    val initialData: T,
    val message: PrivateMessage,
    val lambda: suspend GroupSessionContext<T>.(T) -> Unit,
    var isActive: Boolean = true,
) {
    public val channel: Channel<PrivateMessage> = Channel(Channel.BUFFERED)

    public suspend fun waitNextMessage(): PrivateMessage = channel.receive()

    public suspend fun confirm(reason: MessageChain? = null) {
        isActive = false
        if (reason == null) return
        message.reply(reason)
    }

    public suspend fun reject(reason: MessageChain? = null) {
        if (reason == null) return
        message.reply(reason)
    }
}