/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/1
 */

package cn.rtast.rob.session

import cn.rtast.rob.entity.IGroupMessage
import cn.rtast.rob.entity.IGroupSender
import cn.rtast.rob.entity.IMessage
import cn.rtast.rob.entity.IPrivateMessage
import cn.rtast.rob.entity.IPrivateSender
import cn.rtast.rob.entity.ISender
import java.util.UUID
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

interface IFunctionalGroupSession: IFunctionalSession {
    override val sender: IGroupSender
    override val message: IGroupMessage
}

interface IFunctionalPrivateSession : IFunctionalSession {
    override val sender: IPrivateSender
    override val message: IPrivateMessage
}