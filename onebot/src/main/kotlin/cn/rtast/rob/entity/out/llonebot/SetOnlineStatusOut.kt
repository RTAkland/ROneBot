/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/24
 */


package cn.rtast.rob.entity.out.llonebot

import com.google.gson.annotations.SerializedName

data class SetOnlineStatusOut(
    val params: Params,
    val action: String = "set_online_status",
) {
    data class Params(
        val status: Int,
        @SerializedName("ext_status")
        val extStatus: Int = 0,
        @SerializedName("battery_status")
        val batteryStatus: Int = 0,
    )
}