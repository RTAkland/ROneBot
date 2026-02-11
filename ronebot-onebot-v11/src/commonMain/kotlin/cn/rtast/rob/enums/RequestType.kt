/*
 * Copyright © 2026 RTAkland
 * Date: 2026/2/11 21:37
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */


package cn.rtast.rob.enums

import kotlinx.serialization.SerialName

public enum class RequestType(@Suppress("PropertyName") internal val _serialName: String) {
    /**
     * 被邀请请求类型
     */
    @SerialName("invite")
    Invite("invite"),

    /**
     * 加群请求类型
     */
    @SerialName("add")
    Add("add")
}