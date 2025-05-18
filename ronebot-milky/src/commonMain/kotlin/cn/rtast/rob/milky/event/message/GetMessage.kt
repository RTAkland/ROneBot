/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/18/25, 8:53 PM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.milky.event.message

import cn.rtast.rob.milky.enums.internal.ApiStatus
import cn.rtast.rob.milky.event.common.Message
import kotlinx.serialization.Serializable

/**
 * 获取消息
 */
@Serializable
public data class GetMessage(
    val data: GetMessage?,
    val status: ApiStatus,
    val message: String?
) {
    @Serializable
    public data class GetMessage(
        val message: Message
    )
}