/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/28
 */


package cn.rtast.rob.entity.out.gocq

import com.google.gson.annotations.SerializedName

internal data class CreateGroupFileFolderOut(
    val params: Params,
    val action: String = "create_group_file_folder",
) {
    data class Params(
        @SerializedName("group_id")
        val groupId: Long,
        @SerializedName("name")
        val name: String,
        @SerializedName("parent_id")
        val parentId: String,
    )
}