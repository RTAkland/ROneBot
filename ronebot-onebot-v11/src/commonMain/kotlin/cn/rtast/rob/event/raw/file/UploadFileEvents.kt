/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/28
 */

package cn.rtast.rob.event.raw.file

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class UploadGroupFileResponse(
    val data: UploadGroupFile?
) {
    @Serializable
    public data class UploadGroupFile(
        val message: String,
        @SerialName("retcode")
        val retCode: Int
    )
}


@Serializable
public data class UploadPrivateFileResponse(
    val data: UploadPrivateFile?
) {
    @Serializable
    public data class UploadPrivateFile(
        val message: String,
        @SerialName("retcode")
        val retCode: Int
    )
}