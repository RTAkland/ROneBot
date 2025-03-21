/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/28
 */


package cn.rtast.rob.api.get

import com.google.gson.annotations.SerializedName
import java.util.*

internal data class GetGroupFileSystemInfoApi(
    val params: Params,
    val action: String = "get_group_file_system_info",
    val echo: UUID
) {
    data class Params(
        @SerializedName("group_id")
        val groupId: Long,
    )
}