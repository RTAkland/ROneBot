/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/3
 */


package cn.rtast.rob.entity.lagrange

import cn.rtast.rob.entity.ArrayMessage
import cn.rtast.rob.enums.SegmentType
import com.google.gson.annotations.SerializedName

data class ForwardMessage(
    val data: ForwardMessage
) {
    data class ForwardMessage(
        val message: List<ForwardArrayMessage>
    )

    data class ForwardArrayMessage(
        val type: SegmentType,
        val data: ArrayMessage
    )

    data class ArrayMessage(
        @SerializedName("user_id")
        val userId: Long,
        val nickname: String,
        val content: List<Content>
    )

    data class Content(
        val type: SegmentType,
        val data: ArrayMessage.Data
    )
}