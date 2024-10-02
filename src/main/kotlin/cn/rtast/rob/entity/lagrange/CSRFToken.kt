/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/2
 */


package cn.rtast.rob.entity.lagrange

data class CSRFToken(
    val data: Data
) {
    data class Data(
        val token: String
    )
}