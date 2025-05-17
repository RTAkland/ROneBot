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
 * 上传私聊文件
 */
@Serializable
internal data class UploadPrivateFileAPI(
    @SerialName("user_id")
    val userId: Long,
    @SerialName("file_uri")
    val fileUri: String
)