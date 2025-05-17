/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/18/25, 3:33 AM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.milky.api.message

import cn.rtast.rob.milky.segment.SendSegment
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 发送群消息
 */
@Serializable
internal data class SendGroupMessageAPI(
    @SerialName("group_id")
    val groupId: Long,
    val message: List<SendSegment>
)