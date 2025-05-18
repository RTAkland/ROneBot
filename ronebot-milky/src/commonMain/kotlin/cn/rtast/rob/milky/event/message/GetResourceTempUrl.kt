/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/18/25, 8:58 PM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.milky.event.message

import kotlinx.serialization.Serializable

/**
 * 获取临时资源链接
 */
@Serializable
public data class GetResourceTempUrl(
    val data: TempResourceUrl
) {
    @Serializable
    public data class TempResourceUrl(
        /**
         * 临时资源链接
         */
        val url: String
    )
}