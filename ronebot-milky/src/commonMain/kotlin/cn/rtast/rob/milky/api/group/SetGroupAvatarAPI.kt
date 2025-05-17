/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/18/25, 3:36 AM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.milky.api.group

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 设置群头像
 */
@Serializable
internal data class SetGroupAvatarAPI(
    @SerialName("group_id")
    val groupId: Long,
    @SerialName("image_uri")
    val imageUri: String
)