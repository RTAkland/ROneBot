/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/27
 */


package cn.rtast.rob.entity

import cn.rtast.rob.enums.UserSex
import com.google.gson.annotations.SerializedName

data class StrangerInfo(
    val data: Data,
) {
    data class Data(
        @SerializedName("user_id")
        val userId: Long,
        @SerializedName("q_id")
        val qId: String,
        val nickname: String,
        val sex: UserSex,
        val age: Int,
        val level: Int
    )
}