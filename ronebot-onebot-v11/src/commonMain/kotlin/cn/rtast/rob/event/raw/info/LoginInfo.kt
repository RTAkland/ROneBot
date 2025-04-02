/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/27
 */


package cn.rtast.rob.event.raw.info

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class LoginInfo(
    val data: LoginInfo,
) {
    @Serializable
    public data class LoginInfo(
        /**
         * QQ号
         */
        @SerialName("user_id")
        val userId: Long,
        /**
         * 昵称
         */
        val nickname: String,
    )
}