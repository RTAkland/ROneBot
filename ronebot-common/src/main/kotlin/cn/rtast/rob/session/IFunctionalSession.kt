/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/1
 */

package cn.rtast.rob.session

import cn.rtast.rob.entity.*
import java.util.*
import kotlin.reflect.KFunction

public interface IFunctionalSession {
    public val id: UUID
    public var active: Boolean
    public val message: IMessage
    public val functionalCommand: KFunction<*>
    public val sender: ISender

    public fun endSession() {
        active = false
    }
}

public interface IFunctionalGroupSession : IFunctionalSession {
    override val sender: IGroupSender
    override val message: IGroupMessage
}

public interface IFunctionalPrivateSession : IFunctionalSession {
    override val sender: IPrivateSender
    override val message: IPrivateMessage
}