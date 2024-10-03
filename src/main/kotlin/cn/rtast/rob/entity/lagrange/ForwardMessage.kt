/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/3
 */


package cn.rtast.rob.entity.lagrange

import cn.rtast.rob.entity.ArrayMessage
import cn.rtast.rob.enums.ArrayMessageType
import com.google.gson.annotations.SerializedName

data class ForwardMessage(
    val data: Data
) {
    data class Data(
        val message: List<ForwardArrayMessage>
    )

    data class ForwardArrayMessage(
        @SerializedName("user_id")
        val userId: Long,
        val nickname: String,
        val content: List<Content>
    )

    data class Content(
        val type: ArrayMessageType,
        val data: ArrayMessage.Data
    )
}