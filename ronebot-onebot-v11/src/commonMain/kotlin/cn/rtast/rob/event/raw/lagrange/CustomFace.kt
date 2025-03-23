/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/9/23
 */


package cn.rtast.rob.event.raw.lagrange

import kotlinx.serialization.Serializable

@Serializable
public data class CustomFace(
    val data: List<String>
)