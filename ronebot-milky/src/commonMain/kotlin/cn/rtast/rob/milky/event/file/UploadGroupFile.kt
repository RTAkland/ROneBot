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
 * 上传群聊文件
 */
@Serializable
public data class UploadGroupFile(
    val data: GroupFile
) {
    @Serializable
    public data class GroupFile(
        /**
         * 文件 ID
         */
        @SerialName("file_id")
        val fileId: String
    )
}