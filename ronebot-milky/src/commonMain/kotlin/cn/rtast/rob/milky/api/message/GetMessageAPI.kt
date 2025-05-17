/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/18/25, 3:33 AM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.milky.api.message

import cn.rtast.rob.milky.enums.MessageScene
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 获取消息
 */
@Serializable
internal data class GetMessageAPI(
    /**
     * 消息类型
     */
    @SerialName("message_scene")
    val messageScene: MessageScene,
    /**
     * qq号或者群号
     */
    @SerialName("peer_id")
    val peerId: Long,
    /**
     * 消息ID
     */
    @SerialName("message_seq")
    val messageSeq: Long
)