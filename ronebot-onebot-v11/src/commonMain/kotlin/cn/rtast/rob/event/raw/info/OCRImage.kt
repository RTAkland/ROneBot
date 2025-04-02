/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/24
 */


package cn.rtast.rob.event.raw.info

import kotlinx.serialization.Serializable

@Serializable
public data class OCRImage(
    val data: ORCResult,
) {
    @Serializable
    public data class ORCResult(
        // 语言
        val language: String,
        /**
         * OCR识别结果
         */
        val texts: List<Text>
    )

    @Serializable
    public data class Text(
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

    @Serializable
    public data class Coordinate(
        val x: Int,
        val y: Int
    )
}