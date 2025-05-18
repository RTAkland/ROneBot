/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/18/25, 4:23 AM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.milky.event.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 好友分组
 */
@Serializable
public data class Category(
    /**
     * 分组ID
     */
    @SerialName("category_id")
    val categoryId: Int,
    /**
     * 分组名称
     */
    @SerialName("category_name")
    val categoryName: String
)