package cn.rtast.rob.qqbot.entity.internal

import cn.rtast.rob.qqbot.enums.internal.MessageDispatchType

internal data class BasePacket(
    val op: Int,
    val body: Any,
    val t: MessageDispatchType
)