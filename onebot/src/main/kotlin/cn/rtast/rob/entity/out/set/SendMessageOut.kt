/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/26
 */


package cn.rtast.rob.entity.out.set

import cn.rtast.rob.entity.ArrayMessage
import cn.rtast.rob.enums.internal.MessageEchoType
import cn.rtast.rob.segment.BaseSegment
import com.google.gson.annotations.SerializedName

internal data class CQCodeGroupMessageOut(
    val params: Params,
    val action: String = "send_group_msg",
    val echo: MessageEchoType = MessageEchoType.SendGroupMessage
) {
    data class Params(
        @SerializedName("group_id")
        val groupId: Long,
        val message: String,
    )
}

internal data class ArrayGroupMessageOut(
    val params: Params,
    val action: String = "send_group_msg",
    val echo: MessageEchoType = MessageEchoType.SendGroupMessage
) {
    data class Params(
        @SerializedName("group_id")
        val groupId: Long,
        val message: List<BaseSegment>,
    )
}

internal data class RawArrayGroupMessageOut(
    val params: Params,
    val action: String = "send_group_msg",
    val echo: MessageEchoType = MessageEchoType.SendGroupMessage
) {
    data class Params(
        @SerializedName("group_id")
        val groupId: Long,
        val message: List<ArrayMessage>,
    )
}

internal data class CQCodePrivateMessageOut(
    val params: Params,
    val action: String = "send_private_msg",
    val echo: MessageEchoType = MessageEchoType.SendPrivateMessage
) {
    data class Params(
        @SerializedName("user_id")
        val userId: Long,
        val message: String,
    )
}

internal data class ArrayPrivateMessageOut(
    val params: Params,
    val action: String = "send_private_msg",
    val echo: MessageEchoType = MessageEchoType.SendPrivateMessage
) {
    data class Params(
        @SerializedName("user_id")
        val userId: Long,
        val message: List<BaseSegment>,
    )
}

internal data class RawArrayPrivateMessageOut(
    val params: Params,
    val action: String = "send_private_msg",
    val echo: MessageEchoType = MessageEchoType.SendPrivateMessage
) {
    data class Params(
        @SerializedName("user_id")
        val userId: Long,
        val message: List<ArrayMessage>,
    )
}