/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/28
 */


package cn.rtast.rob.entity.out.gocq

import cn.rtast.rob.enums.internal.MessageEchoType
import com.google.gson.annotations.SerializedName

internal data class GetGroupFileSystemInfoOut(
    val params: Params,
    val action: String = "get_group_file_system_info",
    val echo: MessageEchoType = MessageEchoType.GetGroupFileSystemInfo
) {
    data class Params(
        @SerializedName("group_id")
        val groupId: Long,
    )
}