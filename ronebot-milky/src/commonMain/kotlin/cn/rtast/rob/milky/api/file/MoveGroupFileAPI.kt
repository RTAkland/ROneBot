/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/18/25, 3:42 AM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.milky.api.file

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 移动群文件
 */
@Serializable
internal data class MoveGroupFileAPI(
    @SerialName("group_id")
    val groupId: Long,
    @SerialName("file_id")
    val fileId: String,
    @SerialName("target_folder_id")
    val targetFolderId: String = "/"
)