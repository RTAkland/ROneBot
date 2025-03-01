/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/1
 */

package cn.rtast.rob.session.functional

import cn.rtast.rob.entity.GroupMessage
import cn.rtast.rob.entity.GroupSender
import cn.rtast.rob.entity.PrivateMessage
import cn.rtast.rob.entity.PrivateSender
import cn.rtast.rob.session.IFunctionalGroupSession
import cn.rtast.rob.session.IFunctionalPrivateSession
import java.util.*
import kotlin.reflect.KFunction

/**
 * 函数式群聊会话对象
 */
data class FunctionalGroupSession(
    override var id: UUID,
    override var message: GroupMessage,
    override val sender: GroupSender,
    override val functionalCommand: KFunction<*>,
    override val functionalSessionReceiver: GroupReceiverScope,
    override var active: Boolean = true,
) : IFunctionalGroupSession

/**
 * 函数式私聊会话对象
 */
data class FunctionalPrivateSession(
    override var id: UUID,
    override var message: PrivateMessage,
    override val sender: PrivateSender,
    override val functionalCommand: KFunction<*>,
    override val functionalSessionReceiver: PrivateReceiverScope,
    override var active: Boolean = true,
) : IFunctionalPrivateSession