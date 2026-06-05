/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/30
 */

package cn.rtast.rob.api.set

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.uuid.Uuid

/**
 * 获取群聊推荐卡片
 * Napcat
 */
@Serializable
internal data class ArkShareGroupApi(
    val action: String = "ArkShareGroup",
    val params: Params,
    val echo: Uuid,
) {
    @Serializable
    data class Params(
        @SerialName("group_id")
        val groupId: Long,
    )
}