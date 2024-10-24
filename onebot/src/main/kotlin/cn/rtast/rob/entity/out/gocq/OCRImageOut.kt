/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/24
 */


package cn.rtast.rob.entity.out.gocq

import cn.rtast.rob.enums.internal.MessageEchoType

internal data class OCRImageOut(
    val params: Params,
    val action: String = "ocr_image",
    val echo: MessageEchoType = MessageEchoType.OCRImage
) {
    data class Params(
        val image: String
    )
}