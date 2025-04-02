/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/4/2
 */

package cn.rtast.rob.event.raw.internal

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class SendPacketResponse(
    val data: SendPacket
) {
    @Serializable
    public data class SendPacket(
        val sequence: Int,
        val result: String = "",
        @SerialName("retcode")
        val retCode: Int,
        val extra: String? = null
    )
}