/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/3
 */


package cn.rtast.rob.event.raw.lagrange

import cn.rtast.rob.enums.SegmentType
import cn.rtast.rob.event.raw.ArrayMessage
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class ForwardMessage(
    val data: ForwardMessage
) {
    @Serializable
    public data class ForwardMessage(
        val message: List<ForwardArrayMessage>
    )

    @Serializable
    public data class ForwardArrayMessage(
        val type: SegmentType,
        val data: ArrayMessage
    )

    @Serializable
    public data class ArrayMessage(
        @SerialName("user_id")
        val userId: Long,
        val nickname: String,
        val content: List<Content>
    )

    @Serializable
    public data class Content(
        val type: SegmentType,
        val data: ArrayMessage.Data
    )
}