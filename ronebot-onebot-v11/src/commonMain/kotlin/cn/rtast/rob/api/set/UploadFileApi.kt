/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/9/27
 */


package cn.rtast.rob.api.set

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class UploadPrivateFileApi(
    val action: String = "upload_private_file",
    val params: Params
) {
    @Serializable
    data class Params(
        @SerialName("user_id")
        val userId: Long,
        val file: String,
        val name: String
    )
}

@Serializable
internal data class UploadGroupFileApi(
    val action: String = "upload_group_file",
    val params: Params
) {
    @Serializable
    data class Params(
        @SerialName("group_id")
        val groupId: Long,
        // `local` file path
        val file: String,
        val name: String,
        val folder: String,
    )
}