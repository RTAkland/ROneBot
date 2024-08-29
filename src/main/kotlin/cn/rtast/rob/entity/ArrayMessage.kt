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
        val text: String?,
        val file: String?,
        val filename: String?,
        val url: String?,
        val summary: String?,
        val subType: String?,
        val name: String?,
    )
}