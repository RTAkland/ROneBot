/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/18/25, 3:29 AM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.milky.api.system

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 获取群列表
 */
@Serializable
internal data class GetGroupListAPI(
    @SerialName("no_cache")
    val noCache: Boolean
)