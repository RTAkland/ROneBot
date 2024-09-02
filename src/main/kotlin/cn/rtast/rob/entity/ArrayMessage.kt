/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/26
 */


package cn.rtast.rob.entity

import cn.rtast.rob.enums.ArrayMessageType

data class ArrayMessage(
    val type: ArrayMessageType,
    val data: Data
) {
    data class Data(
        val data: Any?,
        val id: Any?,
        val qq: Any?,
        val text: String?,
        val file: String?,
        val filename: String?,
        val url: String?,
        val summary: String?,
        val subType: String?,
        val name: String?,
        val type: Any?,
        val title: String?,
        val lat: Double?,
        val lon: Double?,
        val content: String?,
        val audio: String?,
    )
}