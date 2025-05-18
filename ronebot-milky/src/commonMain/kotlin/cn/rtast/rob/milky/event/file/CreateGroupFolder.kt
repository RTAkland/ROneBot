/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/18/25, 9:04 PM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.milky.event.file

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 创建群文件夹
 */
@Serializable
public data class CreateGroupFolder(
    val data: CreateGroupFolder
) {
    @Serializable
    public data class CreateGroupFolder(
        /**
         * 文件夹 ID
         */
        @SerialName("folder_id")
        val folderId: String
    )
}