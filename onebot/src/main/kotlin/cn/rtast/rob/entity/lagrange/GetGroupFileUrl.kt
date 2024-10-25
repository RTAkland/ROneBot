/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/9/27
 */


package cn.rtast.rob.entity.lagrange

data class GetGroupFileUrl(
    val data: FileURL
) {
    data class FileURL(
        val url: String
    )
}