/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/9/27
 */


@file:OptIn(ExperimentalUuidApi::class)

package cn.rtast.rob.api.set

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Serializable
internal data class UploadPrivateFileApi(
    val action: String = "upload_private_file",
    val params: Params,
    val echo: Uuid
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
    val params: Params,
    val echo: Uuid
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