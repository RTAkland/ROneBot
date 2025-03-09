/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/9/27
 */


package cn.rtast.rob.event.raw.lagrange

public data class GetGroupFileUrl(
    val data: FileURL
) {
    public data class FileURL(
        val url: String
    )
}