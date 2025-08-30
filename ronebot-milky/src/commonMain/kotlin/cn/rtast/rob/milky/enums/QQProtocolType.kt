/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 8/30/25, 9:33 PM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.milky.enums

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * QQ协议端类别
 */
@Serializable
public enum class QQProtocolType {
    /**
     * windows
     */
    @SerialName("windows")
    Windows,

    /**
     * linux
     */
    @SerialName("linux")
    Linux,

    /**
     * macos
     */
    @SerialName("macos")
    MacOS,

    /**
     * 安卓平板
     */
    @SerialName("android_pad")
    AndroidPad,

    /**
     * 安卓手机
     */
    @SerialName("android_phone")
    AndroidPhone,

    /**
     * ipad
     */
    @SerialName("ipad")
    IPAD,

    /**
     * 鸿蒙
     */
    @SerialName("harmony")
    Harmony,

    /**
     * 手表
     */
    @SerialName("watch")
    Watch
}