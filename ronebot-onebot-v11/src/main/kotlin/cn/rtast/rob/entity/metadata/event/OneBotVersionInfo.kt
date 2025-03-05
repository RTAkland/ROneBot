/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/27
 */


package cn.rtast.rob.entity.metadata.event

import com.google.gson.annotations.SerializedName

public data class OneBotVersionInfo(
    val data: VersionInfo
) {
    public data class VersionInfo(
        @SerializedName("app_name")
        val appName: String,
        @SerializedName("app_version")
        val appVersion: String,
        @SerializedName("protocol_version")
        val protocolVersion: String,
        @SerializedName("nt_protocol")
        val ntProtocol: String,
    )
}