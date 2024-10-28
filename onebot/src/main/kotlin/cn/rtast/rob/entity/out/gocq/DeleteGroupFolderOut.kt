/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/28
 */


package cn.rtast.rob.entity.out.gocq

import com.google.gson.annotations.SerializedName

internal data class DeleteGroupFolderOut(
    val params: Params,
    val action: String = "delete_group_folder"
) {
    data class Params(
        @SerializedName("group_id")
        val groupId: Long,
        @SerializedName("folder_id")
        val folderId: String,
    )
}