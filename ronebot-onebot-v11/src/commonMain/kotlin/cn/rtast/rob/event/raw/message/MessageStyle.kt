/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/4/2
 */

package cn.rtast.rob.event.raw.message

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 发送来的消息样式
 * 包含了气泡、头像挂件、字体、字体特效、气泡DIY文字的样式
 */
@Serializable
public data class MessageStyle(
    /**
     * 气泡ID
     */
    @SerialName("bubble_id")
    val bubbleId: Int,

    /**
     * 头像挂件的ID
     */
    @SerialName("pendant_id")
    val pendantId: Int,

    /**
     * 字体ID
     */
    @SerialName("font_id")
    val fontId: Int,

    /**
     * 字体特效ID
     */
    @SerialName("font_effect_id")
    val fontEffectId: Int,

    /**
     * 暂时不知道是什么
     */
    @SerialName("is_cs_font_effect_enabled")
    val isCsFontEffectEnabled: Boolean,

    /**
     * 气泡DIY文字样式ID
     */
    @SerialName("bubble_diy_text_id")
    val bubbleDIYTextId: Int
)