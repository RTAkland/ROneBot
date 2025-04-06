/*
 * Copyright © 2025 RTAkland
 * Date: 2025/4/6 09:47
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

@file:Suppress("CLASSNAME")

package cn.rtast.rob.gewechat.entity

import kotlinx.serialization.Serializable

@Serializable
internal data class _DoLoginRequest(
    val appId: String,
    val uuid: String,
    val captchaCode: String
)