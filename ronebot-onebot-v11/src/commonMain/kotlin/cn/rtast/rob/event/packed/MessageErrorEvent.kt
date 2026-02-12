/*
 * Copyright © 2026 RTAkland
 * Date: 2026/2/12 16:02
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */


package cn.rtast.rob.event.packed

import cn.rtast.rob.actionable.MessageActionable
import cn.rtast.rob.event.OneBotEvent
import cn.rtast.rob.event.raw.message.BaseMessage
import cn.rtast.rob.event.raw.message.GroupMessage
import cn.rtast.rob.event.raw.message.PrivateMessage
import cn.rtast.rob.onebot.OneBotAction

public sealed interface MessageErrorEvent<T : BaseMessage> {
    public val message: T
    public val exception: Exception
}

/**
 * 精确到某一条群聊消息的错误事件
 */
public data class GroupMessageErrorEvent(
    override val action: OneBotAction,
    override val message: GroupMessage,
    override val exception: Exception,
) : OneBotEvent, MessageErrorEvent<GroupMessage>, MessageActionable by message

/**
 * 精确到某一条私聊消息的错误事件
 */
public data class PrivateMessageErrorEvent(
    override val action: OneBotAction,
    override val message: PrivateMessage,
    override val exception: Exception,
) : OneBotEvent, MessageErrorEvent<PrivateMessage>, MessageActionable by message