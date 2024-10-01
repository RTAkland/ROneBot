/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/1
 */


package cn.rtast.rob.entity.out.lagrange

import cn.rtast.rob.enums.internal.MessageEchoType
import com.google.gson.annotations.SerializedName

internal data class GetEssenceMessageListOut(
    val action: String = "get_essence_msg_list",
    val echo: MessageEchoType = MessageEchoType.GetEssenceMessageList,
    val params: Params
) {
    data class Params(
        @SerializedName("group_id")
        val groupId: Long,
    )
}