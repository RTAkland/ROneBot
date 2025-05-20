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
internal enum class SendSegmentType {
    @SerialName("text")
    Text,
    @SerialName("at")
    AT,
    @SerialName("face")
    Face,
    @SerialName("reply")
    Reply,
    @SerialName("image")
    Image,
    @SerialName("record")
    Record,
    @SerialName("forward")
    Forward,
    @SerialName("video")
    Video
}