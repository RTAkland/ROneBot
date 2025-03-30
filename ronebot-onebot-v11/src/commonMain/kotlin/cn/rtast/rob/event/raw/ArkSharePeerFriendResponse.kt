/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/30
 */

package cn.rtast.rob.event.raw

import kotlinx.serialization.Serializable


/**
 * 获取好友分享卡片
 * Napcat
 */
@Serializable
public data class ArkSharePeerFriendResponse(
    val data: ArkSharePeerFriend
) {
    @Serializable
    public data class ArkSharePeerFriend(
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
        val arkMsg: String
    )
}