/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/22
 */


package cn.rtast.rob.entity.out.lagrange.set

import cn.rtast.rob.enums.internal.MessageEchoType

internal data class UploadImageOut(
    val params: Params,
    val action: String = "upload_image",
    val echo: MessageEchoType = MessageEchoType.UploadImage,
) {
    data class Params(
        val file: String
    )
}

internal data class UploadImage(val data: String)