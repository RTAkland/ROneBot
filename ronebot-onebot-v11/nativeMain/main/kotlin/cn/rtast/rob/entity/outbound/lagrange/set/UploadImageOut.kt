/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/22
 */


package cn.rtast.rob.entity.outbound.lagrange.set

import java.util.*

internal data class UploadImageOut(
    val params: Params,
    val action: String = "upload_image",
    val echo: UUID,
) {
    data class Params(
        val file: String
    )
}

internal data class UploadImage(val data: String)