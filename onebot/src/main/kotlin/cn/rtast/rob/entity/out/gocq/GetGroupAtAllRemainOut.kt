/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/28
 */


package cn.rtast.rob.entity.out.gocq

import cn.rtast.rob.enums.internal.MessageEchoType
import com.google.gson.annotations.SerializedName

internal data class GetGroupAtAllRemainOut(
    val params: Params,
    val action: String = "get_group_at_all_remain",
    val echo: MessageEchoType = MessageEchoType.GetGroupAtAllRemain
) {
    data class Params(
        @SerializedName("group_id")
        val groupId: Long,
    )
}