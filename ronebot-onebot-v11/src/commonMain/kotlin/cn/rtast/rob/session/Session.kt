/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/2/28
 */

@file:OptIn(ExperimentalUuidApi::class)

package cn.rtast.rob.session

import cn.rtast.rob.command.BaseCommand
import cn.rtast.rob.event.raw.GroupMessage
import cn.rtast.rob.event.raw.GroupSender
import cn.rtast.rob.event.raw.PrivateMessage
import cn.rtast.rob.event.raw.PrivateSender
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 * 群聊会话对象
 */
public data class GroupSession<T : Any>(
    override var id: Uuid,
    override var message: GroupMessage,
    override var command: BaseCommand,
    override val sender: GroupSender,
    override val initArgType: T,
    override var active: Boolean = true,
) : IGroupSession<T>

/**
 * 私聊会话对象
 */
public data class PrivateSession<T : Any>(
    override var id: Uuid,
    override var message: PrivateMessage,
    override var command: BaseCommand,
    override val sender: PrivateSender,
    override val initArgType: T,
    override var active: Boolean = true,
) : IPrivateSession<T>