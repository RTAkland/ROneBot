/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/9/23
 */


package cn.rtast.rob.entity.out.lagrange.get

import java.util.UUID

internal data class FetchCustomFaceOut(
    val action: String = "fetch_custom_face",
    val echo: UUID
)