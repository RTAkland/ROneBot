/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/9/27
 */


package cn.rtast.rob.entity.out.lagrange

import cn.rtast.rob.enums.MessageEchoType
import com.google.gson.annotations.SerializedName

internal data class GetGroupFileUrlOut(
    val action: String = "get_group_file_url",
    val echo: MessageEchoType = MessageEchoType.GetGroupFileUrl,
    val params: Params
) {
    data class Params(
        @SerializedName("group_id")
        val groupId: Long,
        @SerializedName("file_id")
        val fileId: String,
        val busid: Int
    )
}