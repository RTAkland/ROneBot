/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/24
 */


package cn.rtast.rob.entity.out.gocq

data class OCRImage(
    val data: Data,
) {
    data class Data(
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