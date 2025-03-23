/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/24
 */


@file:OptIn(ExperimentalUuidApi::class)

package cn.rtast.rob.api.get

import kotlinx.serialization.Serializable
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Serializable
internal data class OCRImageApi(
    val params: Params,
    val action: String = "ocr_image",
    val echo: Uuid
) {
    @Serializable
    data class Params(
        val image: String
    )
}