/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 8/30/25, 9:46 PM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */


package cn.rtast.rob.milky.api.system

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 获取用户个人信息
 */
@Serializable
internal data class GetUserProfileAPI(
    @SerialName("user_id")
    val userId: Long
)