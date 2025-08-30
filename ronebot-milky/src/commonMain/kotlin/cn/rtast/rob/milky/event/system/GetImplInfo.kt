/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 8/30/25, 9:31 PM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */


package cn.rtast.rob.milky.event.system

import cn.rtast.rob.milky.enums.QQProtocolType
import cn.rtast.rob.milky.enums.internal.ApiStatus
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 获取协议端信息
 */
@Serializable
public data class GetImplInfo(
    val data: ImplInfo?,
    val status: ApiStatus,
    val message: String?,
) {
    @Serializable
    public data class ImplInfo(
        /**
         * 协议端名称
         */
        @SerialName("impl_name")
        val implName: String,
        /**
         * 	协议端版本
         */
        @SerialName("impl_version")
        val implVersion: String,
        /**
         * 协议端使用的 QQ 协议版本
         */
        @SerialName("qq_protocol_version")
        val qqProtocolVersion: String,
        /**
         * 协议端使用的 QQ 协议平台
         */
        @SerialName("qq_protocol_type")
        val qqProtocolType: QQProtocolType,
        /**
         * 协议端实现的 Milky 协议版本，目前为 "1.0"
         */
        @SerialName("milky_version")
        val milkyVersion: String,
    )
}