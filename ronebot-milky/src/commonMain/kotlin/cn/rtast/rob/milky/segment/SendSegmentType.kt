/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/18/25, 6:20 AM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.milky.segment

import kotlinx.serialization.Serializable

@Serializable
internal enum class SendSegmentType {
    text, at, face, reply,
    image, record, forward, video
}