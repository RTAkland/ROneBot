/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/9/27
 */


package cn.rtast.rob.entity.out.lagrange

import cn.rtast.rob.enums.MessageEchoType
import com.google.gson.annotations.SerializedName

internal data class GetGroupFilesByFolderOut(
    val action: String = "get_group_files_by_folder",
    val echo: MessageEchoType = MessageEchoType.GetGroupFilesByFolder,
    val params: Params
) {
    data class Params(
        @SerializedName("group_id")
        val groupId: Long,
        @SerializedName("folder_id")
        val folderId: String,
    )
}