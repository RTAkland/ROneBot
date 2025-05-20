/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/18/25, 4:15 AM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

@file:Suppress("EnumEntryName")

package cn.rtast.rob.milky.enums

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 图片的具体类型
 */
@Serializable
public enum class ImageSubType {
    /**
     * 正常图片
     */
    @SerialName("normal")
    Normal,

    /**
     * 贴纸
     */
    @SerialName("sticker")
    Sticker
}