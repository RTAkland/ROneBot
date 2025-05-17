/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/18/25, 3:34 AM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.milky.api.message

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 获取群消息历史
 */
@Serializable
internal data class GetHistoryGroupMessageAPI(
    @SerialName("group_id")
    val groupId: Long,
    @SerialName("start_message_seq")
    val startMessageSeq: Long? = null,
    val limit: Int = 20
)