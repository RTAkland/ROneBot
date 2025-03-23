/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/22
 */


@file:OptIn(ExperimentalUuidApi::class)

package cn.rtast.rob.api.set

import kotlinx.serialization.Serializable
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Serializable
internal data class UploadImageApi(
    val params: Params,
    val action: String = "upload_image",
    val echo: Uuid,
) {
    @Serializable
    data class Params(
        val file: String
    )
}

@Serializable
internal data class UploadImage(val data: String)