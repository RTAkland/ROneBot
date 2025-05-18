/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/18/25, 4:29 AM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.milky.event.system

import cn.rtast.rob.milky.enums.internal.ApiStatus
import cn.rtast.rob.milky.event.common.Group
import kotlinx.serialization.Serializable

/**
 * 获取群信息
 */
@Serializable
public data class GetGroupInfo(
    val data: Group?,
    val status: ApiStatus,
    val message: String?
)