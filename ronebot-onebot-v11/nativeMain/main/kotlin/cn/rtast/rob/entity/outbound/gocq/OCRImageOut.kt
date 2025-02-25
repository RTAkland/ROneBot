/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/24
 */


package cn.rtast.rob.entity.outbound.gocq

import java.util.*

internal data class OCRImageOut(
    val params: Params,
    val action: String = "ocr_image",
    val echo: UUID
) {
    data class Params(
        val image: String
    )
}