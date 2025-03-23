/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/3
 */


@file:OptIn(ExperimentalUuidApi::class)

package cn.rtast.rob.api.get

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 * 获取文件的URL
 */
@Serializable
internal data class GetGroupFileUrlApi(
    val action: String = "get_group_file_url",
    val echo: Uuid,
    val params: Params
) {
    @Serializable
    data class Params(
        @SerialName("group_id")
        val groupId: Long,
        @SerialName("file_id")
        val fileId: String,
        val busid: Int
    )
}

/**
 * 获取根目录下的文件目录
 */
@Serializable
internal data class GetGroupRootFilesApi(
    val action: String = "get_group_root_files",
    val echo: Uuid,
    val params: Params
) {
    @Serializable
    data class Params(
        @SerialName("group_id")
        val groupId: Long,
    )
}

/**
 * 通过文件夹ID来获取这个文件夹下的文件目录
 */
@Serializable
internal data class GetGroupFilesByFolderApi(
    val action: String = "get_group_files_by_folder",
    val echo: Uuid,
    val params: Params
) {
    @Serializable
    data class Params(
        @SerialName("group_id")
        val groupId: Long,
        @SerialName("folder_id")
        val folderId: String,
    )
}