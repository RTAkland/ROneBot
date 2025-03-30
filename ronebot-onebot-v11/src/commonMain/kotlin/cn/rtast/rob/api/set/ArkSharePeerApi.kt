/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/30
 */

@file:OptIn(ExperimentalUuidApi::class)

package cn.rtast.rob.api.set

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 * 获取推荐群聊/好友卡片
 * Napcat
 */
@Serializable
internal data class ArkSharePeerApi(
    val action: String = "ArkSharePeer",
    val params: Params,
    val echo: Uuid
) {
    @Serializable
    data class Params(
        @SerialName("user_id")
        val userId: Long?,
        @SerialName("group_id")
        val groupId: Long?,
    )
}