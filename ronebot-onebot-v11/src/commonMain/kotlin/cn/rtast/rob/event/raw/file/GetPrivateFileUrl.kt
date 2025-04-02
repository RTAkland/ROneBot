/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/4/2
 */

package cn.rtast.rob.event.raw.file

import kotlinx.serialization.Serializable

@Serializable
public data class GetPrivateFileUrl(
    val data: PrivateFileUrl
) {
    @Serializable
    public data class PrivateFileUrl(
        val url: String
    )
}