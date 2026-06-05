/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/4/2
 */


package cn.rtast.rob.api.set.internal

import kotlinx.serialization.Serializable
import kotlin.uuid.Uuid

/**
 * 发送自定义数据包
 */
@Serializable
@Suppress("CLASSNAME")
internal data class _SendPacketApi(
    val action: String = ".send_packet",
    val params: Params,
    val echo: Uuid,
) {
    @Serializable
    data class Params(
        val data: String,
        val command: String,
        val sign: Boolean,
        val type: Byte,
    )
}