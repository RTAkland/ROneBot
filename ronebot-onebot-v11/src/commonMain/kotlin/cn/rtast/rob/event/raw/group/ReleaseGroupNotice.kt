/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/1
 */


package cn.rtast.rob.event.raw.group

import kotlinx.serialization.Serializable

@Serializable
internal data class ReleaseGroupNotice(
    val data: String
)