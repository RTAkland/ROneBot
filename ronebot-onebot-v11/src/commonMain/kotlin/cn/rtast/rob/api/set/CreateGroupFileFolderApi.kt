/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/28
 */


package cn.rtast.rob.api.set

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class CreateGroupFileFolderApi(
    val params: Params,
    val action: String = "create_group_file_folder",
) {
    @Serializable
    data class Params(
        @SerialName("group_id")
        val groupId: Long,
        @SerialName("name")
        val name: String,
        @SerialName("parent_id")
        val parentId: String,
    )
}