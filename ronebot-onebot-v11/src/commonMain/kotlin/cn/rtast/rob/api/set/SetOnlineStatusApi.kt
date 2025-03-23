/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/24
 */


package cn.rtast.rob.api.set

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class SetOnlineStatusApi(
    val params: Params,
    val action: String = "set_online_status",
) {
    @Serializable
    data class Params(
        val status: Int,
        @SerialName("ext_status")
        val extStatus: Int = 0,
        @SerialName("battery_status")
        val batteryStatus: Int = 0,
    )
}