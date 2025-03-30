/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/30
 */

package cn.rtast.rob.event.raw

import kotlinx.serialization.Serializable

/**
 * 获取群聊分享卡片结果
 * Napcat
 */
@Serializable
public data class ArkShareGroupResponse(
    /**
     * 卡片消息json
     */
    val data: String
)