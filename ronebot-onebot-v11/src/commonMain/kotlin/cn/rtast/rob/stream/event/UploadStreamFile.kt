/*
 * Copyright © 2025 RTAkland
 * Date: 10/5/25, 6:08 AM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */


package cn.rtast.rob.stream.event

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class UploadStreamFile(
    val data: UploadedFile
) {
    @Serializable
    public data class UploadedFile(
        @SerialName("file_path")
        val filePath: String?
    )
}