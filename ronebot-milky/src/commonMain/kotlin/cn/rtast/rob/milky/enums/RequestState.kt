/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 8/30/25, 10:34 PM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.milky.enums

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 请求状态
 * 可用于好友/加群请求
 */
@Serializable
public enum class RequestState {
    /**
     * 处理中
     */
    @SerialName("pending")
    Pending,

    /**
     * 已同意
     */
    @SerialName("accepted")
    Accepted,

    /**
     * 已拒绝
     */
    @SerialName("rejected")
    Rejected,

    /**
     * 已忽略
     */
    @SerialName("ignored")
    Ignored
}