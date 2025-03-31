package cn.rtast.rob.qqbot.entity.internal

import cn.rtast.rob.qqbot.enums.internal.MessageDispatchType
import kotlinx.serialization.Serializable

@Serializable
internal data class BasePacket(
    val op: Int,
    val t: MessageDispatchType
)