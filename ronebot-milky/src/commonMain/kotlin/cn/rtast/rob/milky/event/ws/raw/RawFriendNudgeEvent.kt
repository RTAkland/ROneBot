/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/21/25, 11:48 PM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.milky.event.ws.raw

import cn.rtast.rob.milky.enums.internal.MilkyEvents
import cn.rtast.rob.milky.milky.MilkyAction
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

/**
 * 好友戳一戳Json解析
 */
@Serializable
public data class RawFriendNudgeEvent(
    val data: FriendNudge,
    @SerialName("event_type")
    val eventType: MilkyEvents
) {
    @Serializable
    public data class FriendNudge(
        /**
         *好友 QQ 号
         */
        @SerialName("user_id")
        val userId: Long,
        /**
         *是否是自己发送的戳一戳
         */
        @SerialName("is_self_send")
        val isSelfSend: Boolean,
        /**
         *是否是自己接收的戳一戳
         */
        @SerialName("is_self_receive")
        val isSelfReceive: Boolean
    ) {
        @Transient
        lateinit var action: MilkyAction
    }
}