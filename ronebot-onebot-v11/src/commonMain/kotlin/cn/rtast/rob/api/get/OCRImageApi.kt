/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/24
 */


package cn.rtast.rob.api.get

import java.util.*

internal data class OCRImageApi(
    val params: Params,
    val action: String = "ocr_image",
    val echo: UUID
) {
    data class Params(
        val image: String
    )
}