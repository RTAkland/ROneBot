/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/1
 */

package cn.rtast.rob.session

import cn.rtast.rob.entity.*
import java.util.*
import kotlin.reflect.KFunction

interface IFunctionalSession {
    val id: UUID
    var active: Boolean
    val message: IMessage
    val functionalCommand: KFunction<*>
    val sender: ISender

    fun endSession() {
        active = false
    }
}

interface IFunctionalGroupSession : IFunctionalSession {
    override val sender: IGroupSender
    override val message: IGroupMessage
}

interface IFunctionalPrivateSession : IFunctionalSession {
    override val sender: IPrivateSender
    override val message: IPrivateMessage
}