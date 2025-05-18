/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/18/25, 6:20 AM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

@file:Suppress("EnumEntryName")

package cn.rtast.rob.milky.segment

public enum class ReceiveSegmentType {
    /**
     * 文本
     */
    text,

    /**
     * at @
     */
    at,

    /**
     * 表情
     */
    face,

    /**
    * 回复
     */
    reply,

    /**
     * 图片
     */
    image,

    /**
     * 语音
     */
    record,

    /**
     * 视频消息
     */
    video,

    /**
     * 转发消息
     */
    forward,

    /**
     * 商城表情
     */
    market_face,

    /**
     * 小程序
     */
    light_app,

    /**
     * XML
     */
    xml
}