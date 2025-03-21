/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/3
 */


package cn.rtast.rob.event.raw.lagrange

import cn.rtast.rob.event.raw.ArrayMessage
import cn.rtast.rob.enums.SegmentType
import com.google.gson.annotations.SerializedName

public data class ForwardMessage(
    val data: ForwardMessage
) {
    public data class ForwardMessage(
        val message: List<ForwardArrayMessage>
    )

    public data class ForwardArrayMessage(
        val type: SegmentType,
        val data: ArrayMessage
    )

    public data class ArrayMessage(
        @SerializedName("user_id")
        val userId: Long,
        val nickname: String,
        val content: List<Content>
    )

    public data class Content(
        val type: SegmentType,
        val data: ArrayMessage.Data
    )
}