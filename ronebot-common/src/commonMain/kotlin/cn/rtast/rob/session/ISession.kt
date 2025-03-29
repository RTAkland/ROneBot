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

public interface ISession<T> {
    public val id: Uuid
    public var active: Boolean
    public val message: IMessage
    public val command: IBaseCommand<out IGroupMessage, out IPrivateMessage>
    public val sender: ISender
    public val initArgType: T

    public fun endSession() {
        active = false
    }
}

public interface IPrivateSession<T> : ISession<T> {
    override val message: IPrivateMessage
    override val sender: IPrivateSender
}

public interface IGroupSession<T> : ISession<T> {
    override val message: IGroupMessage
    override val sender: IGroupSender
}