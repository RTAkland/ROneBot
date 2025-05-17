/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/18/25, 6:20 AM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

@file:Suppress("EnumEntryName")

package cn.rtast.rob.milky.segment

public enum class ReceiveSegmentType {
    text, at, face, reply, image, record,
    video, forward, market_face, light_app,
    xml
}