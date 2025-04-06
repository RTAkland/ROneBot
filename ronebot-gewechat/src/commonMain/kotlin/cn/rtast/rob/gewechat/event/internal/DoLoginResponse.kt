/*
 * Copyright © 2025 RTAkland
 * Date: 2025/4/6 09:48
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.gewechat.event.internal

import kotlinx.serialization.Serializable

@Serializable
public data class DoLoginResponse(
    val ret: Int,
    val msg: String,
    val data: LoginResponse
) {
    @Serializable
    public data class LoginResponse(
        val uuid: String,
        val headImageUrl: String,
        val nickName: String,
        val expiredTime: Long,
        val status: Int,
        val loginInfo: String? = null
    )
}