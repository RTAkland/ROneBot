/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/30
 */

package cn.rtast.rob.event.raw.friend

import kotlinx.serialization.Serializable

/**
 * 获取群聊分享卡片
 * Napcat
 */
@Serializable
public data class ArkSharePeerResponse(
    val data: ArkSharePeer
) {
    @Serializable
    public data class ArkSharePeer(
        /**
         * 错误代码
         */
        val errCode: Int,
        /**
         * 错误信息
         */
        val errMsg: String,
        /**
         * 卡片消息json
         */
        val arkJson: String,
    )
}