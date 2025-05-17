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
 * 重命名群文件
 */
@Serializable
internal data class RenameGroupFileAPI(
    @SerialName("group_id")
    val groupId: Long,
    @SerialName("file_id")
    val fileId: String,
    @SerialName("new_name")
    val newName: String
)