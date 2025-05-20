/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/19/25, 4:18 AM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

@file:Suppress("EnumEntryName")

package cn.rtast.rob.milky.enums.internal

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public enum class ApiStatus {
    @SerialName("failed")
    Failed,

    @SerialName("ok")
    OK
}