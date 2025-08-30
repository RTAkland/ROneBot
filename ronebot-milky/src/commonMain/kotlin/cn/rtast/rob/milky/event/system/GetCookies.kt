/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 8/30/25, 9:40 PM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */


package cn.rtast.rob.milky.event.system

import cn.rtast.rob.milky.enums.internal.ApiStatus
import kotlinx.serialization.Serializable

/**
 * 获取 Cookies
 */
@Serializable
public data class GetCookies(
    val data: Cookies?,
    val status: ApiStatus,
    val message: String?
) {
    @Serializable
    public data class Cookies(
        /**
         * 需要获取 Cookies 的域名
         */
        val domain: String
    )
}