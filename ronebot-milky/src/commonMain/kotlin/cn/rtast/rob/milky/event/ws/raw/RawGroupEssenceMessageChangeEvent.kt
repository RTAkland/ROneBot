/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/22/25, 12:03 AM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.milky.event.ws.raw

import arrow.core.Either
import cn.rtast.rob.milky.actionable.CommonGroupEventActionable
import cn.rtast.rob.milky.actionable.GroupEssenceActionable
import cn.rtast.rob.milky.enums.internal.MilkyEvents
import cn.rtast.rob.milky.event.common.Group
import cn.rtast.rob.milky.event.group.GetGroupEssenceMessages
import cn.rtast.rob.milky.milky.MilkyAction
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import love.forte.plugin.suspendtrans.annotation.JvmAsync
import love.forte.plugin.suspendtrans.annotation.JvmBlocking

/**
 * 群精华消息变更Json解析
 */
@Serializable
public data class RawGroupEssenceMessageChangeEvent(
    val data: GroupEssenceMessageChange,
    @SerialName("event_type")
    val eventType: MilkyEvents
) {
    @Serializable
    public data class GroupEssenceMessageChange(
        /**
         * 群号
         */
        @SerialName("group_id")
        val groupId: Long,
        /**
         * 发生变更的消息序列号
         */
        @SerialName("message_seq")
        val messageSeq: Long,
        /**
         * 是否被设置为精华，false 表示被取消精华
         */
        @SerialName("is_set")
        val isSet: Boolean
    ): CommonGroupEventActionable, GroupEssenceActionable {
        @Transient
        lateinit var action: MilkyAction

        @JvmBlocking
        override suspend fun getGroupInfo(): Either<String, Group> {
            return action.getGroupInfo(groupId, true)
        }

        /**
         * 设置当前消息为精华消息
         */
        @JvmAsync
        @JvmBlocking
        override suspend fun setEssence() {
            action.setGroupEssenceMessage(groupId, messageSeq)
        }

        @JvmAsync
        @JvmBlocking
        override suspend fun setEssence(messageSeq: Long) {
            action.setGroupEssenceMessage(groupId, messageSeq)
        }

        /**
         * 取消设置当前精华消息
         */
        @JvmAsync
        @JvmBlocking
        override suspend fun unsetEssence() {
            action.setGroupEssenceMessage(groupId, messageSeq)
        }

        @JvmAsync
        @JvmBlocking
        override suspend fun unsetEssence(messageSeq: Long) {
            TODO("Not yet implemented")
        }

        @JvmBlocking
        override suspend fun getGroupEssenceMessages(): Either<String, GetGroupEssenceMessages.GroupEssenceMessages> {
            return action.getGroupEssenceMessages(groupId, 0, 0)
        }

        @JvmBlocking
        override suspend fun getGroupEssenceMessages(
            pageIndex: Int,
        ): Either<String, GetGroupEssenceMessages.GroupEssenceMessages> {
            return action.getGroupEssenceMessages(groupId, 0, pageIndex)
        }

        @JvmBlocking
        override suspend fun getGroupEssenceMessages(
            pageSize: Int,
            pageIndex: Int,
        ): Either<String, GetGroupEssenceMessages.GroupEssenceMessages> {
            return action.getGroupEssenceMessages(groupId, pageSize, pageIndex)
        }
    }
}