/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/18/25, 3:43 AM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.milky.api.file

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 重命名群文件夹
 */
@Serializable
internal data class RenameGroupFolderAPI(
    @SerialName("group_id")
    val groupId: Long,
    /**
     * 文件夹 ID
     */
    @SerialName("folder_id")
    val folderId: String,
    /**
     * 新文件夹名
     */
    @SerialName("new_folder_name")
    val newFolderName: String
)