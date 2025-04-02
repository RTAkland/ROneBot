/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/27
 */


package cn.rtast.rob.event.raw.onebot

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class OneBotVersionInfo(
    val data: VersionInfo
) {
    @Serializable
    public data class VersionInfo(
        @SerialName("app_name")
        val appName: String,
        @SerialName("app_version")
        val appVersion: String,
        @SerialName("protocol_version")
        val protocolVersion: String,
        @SerialName("nt_protocol")
        val ntProtocol: String,
    )
}