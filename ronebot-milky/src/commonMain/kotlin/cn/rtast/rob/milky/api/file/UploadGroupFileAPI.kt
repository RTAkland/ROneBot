/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/18/25, 3:41 AM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.milky.api.file

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 上传群文件
 */
@Serializable
internal data class UploadGroupFileAPI(
    @SerialName("group_id")
    val groupId: Long,
    /**
     * 文件 URI，支持 `file://` `http(s)://` `base64://` 三种格式
     */
    @SerialName("file_uri")
    val fileUri: String,
    /**
     * 文件名称
     */
    @SerialName("file_name")
    val fileName: String,
    /**
     * 目标文件夹 ID
     */
    @SerialName("parent_folder_id")
    val parentFolderId: String = "/"
)