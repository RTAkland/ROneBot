/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/18/25, 6:20 AM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

@file:Suppress("EnumEntryName")

package cn.rtast.rob.milky.segment

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public enum class ReceiveSegmentType {
    /**
     * 文本
     */
    @SerialName("text")
    Text,

    /**
     * at @
     */
    @SerialName("at")
    AT,

    /**
     * 表情
     */
    @SerialName("face")
    Face,

    /**
     * 回复
     */
    @SerialName("reply")
    Reply,

    /**
     * 图片
     */
    @SerialName("image")
    Image,

    /**
     * 语音
     */
    @SerialName("record")
    Record,

    /**
     * 视频消息
     */
    @SerialName("video")
    Video,

    /**
     * 转发消息
     */
    @SerialName("forward")
    Forward,

    /**
     * 商城表情
     */
    @SerialName("market_face")
    MarketFace,

    /**
     * 小程序
     */
    @SerialName("light_app")
    LightApp,

    /**
     * XML
     */
    @SerialName("xml")
    Xml
}