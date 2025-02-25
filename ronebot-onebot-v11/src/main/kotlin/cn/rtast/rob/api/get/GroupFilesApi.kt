/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/3
 */


package cn.rtast.rob.api.get

import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * 获取文件的URL
 */
internal data class GetGroupFileUrlApi(
    val action: String = "get_group_file_url",
    val echo: UUID,
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

/**
 * 获取根目录下的文件目录
 */
internal data class GetGroupRootFilesApi(
    val action: String = "get_group_root_files",
    val echo: UUID,
    val params: Params
) {
    data class Params(
        @SerializedName("group_id")
        val groupId: Long,
    )
}

/**
 * 通过文件夹ID来获取这个文件夹下的文件目录
 */
internal data class GetGroupFilesByFolderApi(
    val action: String = "get_group_files_by_folder",
    val echo: UUID,
    val params: Params
) {
    data class Params(
        @SerializedName("group_id")
        val groupId: Long,
        @SerializedName("folder_id")
        val folderId: String,
    )
}