/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/18/25, 8:17 PM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.milky.event.common

import cn.rtast.rob.milky.segment.ReceiveSegment
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 接收转发消息
 */
@Serializable
public data class ForwardMessage(
    /**
     * 发送者QQ号
     */
    @SerialName("user_id")
    val userId: Long,
    /**
     * 发送者名称
     */
    val name: String,
    /**
     * 消息段列表
     */
    val segments: List<ReceiveSegment>
)