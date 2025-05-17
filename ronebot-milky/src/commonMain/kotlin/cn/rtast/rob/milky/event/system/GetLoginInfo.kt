/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/18/25, 3:26 AM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.milky.event.system

import kotlinx.serialization.Serializable

/**
 * 获取登陆信息
 */
@Serializable
public data class GetLoginInfo(
    val data: LoginInfo
) {
    @Serializable
    public data class LoginInfo(
        /**
         * qq号
         */
        val uin: Long,
        /**
         * 昵称
         */
        val nickname: String
    )
}