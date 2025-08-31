/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 8/31/25, 2:19 PM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */


package cn.rtast.rob.milky.api.group

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class GetGroupEssenceMessagesAPI(
    @SerialName("group_id")
    val groupId: Long,
    /**
     * 页码索引，从 0 开始
     */
    @SerialName("page_index")
    val pageIndex: Int,
    /**
     * 每页包含的精华消息数量
     */
    @SerialName("page_size")
    val pageSize: Int
)