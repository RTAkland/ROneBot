/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 8/30/25, 9:42 PM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */


package cn.rtast.rob.milky.event.system

import cn.rtast.rob.milky.enums.internal.ApiStatus
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 获取csrf token
 */
@Serializable
public data class GetCSRFToken(
    val status: ApiStatus,
    val message: String?,
    val data: CSRFToken?
) {
    @Serializable
    public data class CSRFToken(
        @SerialName("csrf_token")
        val csrfToken: String
    )
}