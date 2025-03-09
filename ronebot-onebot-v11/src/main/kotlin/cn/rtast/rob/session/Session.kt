/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/2/28
 */

package cn.rtast.rob.session

import cn.rtast.rob.command.BaseCommand
import cn.rtast.rob.event.raw.GroupMessage
import cn.rtast.rob.event.raw.GroupSender
import cn.rtast.rob.event.raw.PrivateMessage
import cn.rtast.rob.event.raw.PrivateSender
import java.util.*

/**
 * 群聊会话对象
 */
public data class GroupSession(
    override var id: UUID,
    override var message: GroupMessage,
    override var command: BaseCommand,
    override val sender: GroupSender,
    override var active: Boolean = true,
) : IGroupSession

/**
 * 私聊会话对象
 */
public data class PrivateSession(
    override var id: UUID,
    override var message: PrivateMessage,
    override var command: BaseCommand,
    override val sender: PrivateSender,
    override var active: Boolean = true,
) : IPrivateSession