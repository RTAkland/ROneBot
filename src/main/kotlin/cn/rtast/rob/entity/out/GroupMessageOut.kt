/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/26
 */


package cn.rtast.rob.entity.out

import cn.rtast.rob.entity.ArrayMessage
import cn.rtast.rob.entity.segment.BaseArrayMessage
import com.google.gson.annotations.SerializedName

internal data class CQCodeGroupMessageOut(
    val action: String = "send_group_msg",
    val params: Params,
) {
    data class Params(
        @SerializedName("group_id")
        val groupId: Long,
        val message: String,
    )
}

internal data class ArrayGroupMessageOut(
    val action: String = "send_group_msg",
    val params: Params,
) {
    data class Params(
        @SerializedName("group_id")
        val groupId: Long,
        val message: List<BaseArrayMessage>,
    )
}

internal data class RawArrayGroupMessageOut(
    val action: String = "send_group_msg",
    val params: Params,
) {
    data class Params(
        @SerializedName("group_id")
        val groupId: Long,
        val message: List<ArrayMessage>,
    )
}