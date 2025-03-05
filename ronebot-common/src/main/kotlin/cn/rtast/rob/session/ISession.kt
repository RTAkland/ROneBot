/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/2/28
 */

package cn.rtast.rob.session

import cn.rtast.rob.command.IBaseCommand
import cn.rtast.rob.entity.*
import java.util.*

public interface ISession {
    public val id: UUID
    public var active: Boolean
    public val message: IMessage
    public val command: IBaseCommand<IGroupMessage, IPrivateMessage>
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