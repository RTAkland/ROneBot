/*
 * Copyright © 2025 RTAkland
 * Date: 2025/4/6 09:54
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

@file:Suppress("CLASSNAME")

package cn.rtast.rob.gewechat.entity

import kotlinx.serialization.Serializable


@Serializable
internal data class _SetCallbackUrlRequest(
    val token: String,
    val callbackUrl: String
)