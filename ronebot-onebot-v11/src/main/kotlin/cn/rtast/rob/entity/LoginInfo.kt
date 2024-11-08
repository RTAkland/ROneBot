/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/27
 */


package cn.rtast.rob.entity

import com.google.gson.annotations.SerializedName

data class LoginInfo(
    val data: LoginInfo,
) {
    data class LoginInfo(
        @SerializedName("user_id")
        val userId: Long,
        val nickname: String,
    )
}