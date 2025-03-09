/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/27
 */


package cn.rtast.rob.event.raw

import com.google.gson.annotations.SerializedName

public data class LoginInfo(
    val data: LoginInfo,
) {
    public data class LoginInfo(
        /**
         * QQ号
         */
        @SerializedName("user_id")
        val userId: Long,
        /**
         * 昵称
         */
        val nickname: String,
    )
}