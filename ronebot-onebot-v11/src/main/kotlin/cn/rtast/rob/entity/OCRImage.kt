/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/24
 */


package cn.rtast.rob.entity

data class OCRImage(
    val data: ORCResult,
) {
    data class ORCResult(
        // 语言
        val language: String,
        val texts: List<Text>
    )

    data class Text(
        val confidence: Int,
        val coordinates: List<Coordinate>,
        val text: String
    )

    data class Coordinate(
        val x: Int,
        val y: Int
    )
}