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
 * 获取临时资源链接
 */
@Serializable
internal data class GetResourceTempUrlAPI(
    @SerialName("resource_id")
    val resourceId: String
)