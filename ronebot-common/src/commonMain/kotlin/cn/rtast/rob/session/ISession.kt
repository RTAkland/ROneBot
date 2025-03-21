/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/2/28
 */

@file:OptIn(ExperimentalUuidApi::class)

package cn.rtast.rob.session

import cn.rtast.rob.command.IBaseCommand
import cn.rtast.rob.entity.*
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

public interface ISession {
    public val id: Uuid
    public var active: Boolean
    public val message: IMessage
    public val command: IBaseCommand<out IGroupMessage, out IPrivateMessage>
    public val sender: ISender

    public fun endSession() {
        active = false
    }
}

public interface IPrivateSession : ISession {
    override val message: IPrivateMessage
    override val sender: IPrivateSender
}

public interface IGroupSession : ISession {
    override val message: IGroupMessage
    override val sender: IGroupSender
}