/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/9/23
 */


package cn.rtast.rob.api.get

import java.util.*

internal data class FetchCustomFaceApi(
    val action: String = "fetch_custom_face",
    val echo: UUID
)