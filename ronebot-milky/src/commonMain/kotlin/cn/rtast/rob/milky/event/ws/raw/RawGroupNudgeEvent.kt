/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/22/25, 12:29 AM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.milky.event.ws.raw

import arrow.core.Either
import cn.rtast.rob.milky.actionable.CommonGroupEventActionable
import cn.rtast.rob.milky.enums.internal.MilkyEvents
import cn.rtast.rob.milky.event.common.Group
import cn.rtast.rob.milky.milky.MilkyAction
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import love.forte.plugin.suspendtrans.annotation.JvmBlocking

/**
 * 群戳一戳Json解析
 */
@Serializable
public data class RawGroupNudgeEvent(
    val data: GroupNudge,
    @SerialName("event_type")
    val eventType: MilkyEvents,
) {
    @Serializable
    public data class GroupNudge(
        /**
         * 群号
         */
        @SerialName("group_id")
        val groupId: Long,
        /**
         * 发送者 QQ 号
         */
        @SerialName("sender_id")
        val senderId: Long,
        /**
         * 接收者 QQ 号
         */
        @SerialName("receiver_id")
        val receiverId: Long,
        /**
         * 戳一戳提示的动作文本
         */
        @SerialName("display_action")
        val displayAction: String,

        /**
         * 戳一戳提示的后缀文本
         */
        @SerialName("display_suffix")
        val displaySuffix: String,
        /**
         * 戳一戳提示的动作图片 URL，用于取代动作提示文本
         */
        @SerialName("display_action_img_url")
        val displayActionImgURL: String,
    ) : CommonGroupEventActionable {
        @Transient
        lateinit var action: MilkyAction

        @JvmBlocking
        override suspend fun getGroupInfo(): Either<String, Group> {
            return action.getGroupInfo(groupId, true)
        }
    }
}