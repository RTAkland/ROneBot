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
        val data: Any? = null,
        val id: Any? = null,
        val qq: Any? = null,
        val text: String? = null,
        val file: String? = null,
        val filename: String? = null,
        val url: String? = null,
        val summary: String? = null,
        val subType: String? = null,
        val name: String? = null,
        val type: Any? = null,
        val title: String? = null,
        val lat: Double? = null,
        val lon: Double? = null,
        val content: String? = null,
        val audio: String? = null,
    )
}