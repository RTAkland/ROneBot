/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 2025/4/19 20:40
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.onebot.v12.segment

import cn.rtast.rob.onebot.v12.enums.internal.SegmentType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class ArrayMessage(
    val type: SegmentType,
    val data: Message
) {
    @Serializable
    public data class Message(
        @SerialName("user_id")
        val userId: String?,
        val qq: Long?,
        @SerialName("file_id")
        val fileId: String?,
        val text: String?
    )
}