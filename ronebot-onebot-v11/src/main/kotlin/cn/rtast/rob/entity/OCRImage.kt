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
        /**
         * OCR识别结果
         */
        val texts: List<Text>
    )

    data class Text(
        /**
         * 置信度
         */
        val confidence: Int,
        /**
         * 坐标
         */
        val coordinates: List<Coordinate>,
        /**
         * 文本内容
         */
        val text: String
    )

    data class Coordinate(
        val x: Int,
        val y: Int
    )
}