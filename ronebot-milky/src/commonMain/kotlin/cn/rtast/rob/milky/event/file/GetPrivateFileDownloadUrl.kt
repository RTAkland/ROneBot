/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/18/25, 9:01 PM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.milky.event.file

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 获取私聊文件下载链接
 */
@Serializable
public data class GetPrivateFileDownloadUrl(
    val data: FileDownloadUrl
) {
    @Serializable
    public data class FileDownloadUrl(
        /**
         * 文件下载链接
         */
        @SerialName("download_url")
        val downloadUrl: String
    )
}