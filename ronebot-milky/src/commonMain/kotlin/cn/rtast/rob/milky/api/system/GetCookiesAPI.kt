/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 8/30/25, 9:45 PM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */


package cn.rtast.rob.milky.api.system

import kotlinx.serialization.Serializable

/**
 * 获取Cookie
 */
@Serializable
internal data class GetCookiesAPI(
    val domain: String
)