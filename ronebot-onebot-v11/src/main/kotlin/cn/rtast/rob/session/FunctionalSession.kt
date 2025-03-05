/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/1
 */

package cn.rtast.rob.session

import cn.rtast.rob.entity.GroupMessage
import cn.rtast.rob.entity.GroupSender
import cn.rtast.rob.entity.PrivateMessage
import cn.rtast.rob.entity.PrivateSender
import java.util.*
import kotlin.reflect.KFunction

/**
 * 函数式群聊会话对象
 */
public data class FunctionalGroupSession(
    override var id: UUID,
    override var message: GroupMessage,
    override val sender: GroupSender,
    override val functionalCommand: KFunction<*>,
    override var active: Boolean = true,
) : IFunctionalGroupSession

/**
 * 函数式私聊会话对象
 */
public data class FunctionalPrivateSession(
    override var id: UUID,
    override var message: PrivateMessage,
    override val sender: PrivateSender,
    override val functionalCommand: KFunction<*>,
    override var active: Boolean = true,
) : IFunctionalPrivateSession