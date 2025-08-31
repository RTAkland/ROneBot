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
 * 获取私聊文件下载链接
 */
@Serializable
internal data class GetPrivateFileDownloadUrlAPI(
    @SerialName("user_id")
    val userId: Long,
    /**
     * 文件 ID
     */
    @SerialName("file_id")
    val fileId: String,
    /**
     * 文件的 TriSHA1 哈希值
     */
    @SerialName("file_hash")
    val fileHash: String
)