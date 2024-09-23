/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/26
 */


package cn.rtast.rob.entity.out

import cn.rtast.rob.enums.MessageEchoType
import com.google.gson.annotations.SerializedName

internal data class GetMessageOut(
    val action: String = "get_msg",
    val params: Params,
    val echo: MessageEchoType = MessageEchoType.GetMessage
) {
    data class Params(
        @SerializedName("message_id")
        val groupId: Long,
    )
}