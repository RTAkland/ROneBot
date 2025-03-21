/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/24
 */


package cn.rtast.rob.api.set

import com.google.gson.annotations.SerializedName

internal data class SetOnlineStatusApi(
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