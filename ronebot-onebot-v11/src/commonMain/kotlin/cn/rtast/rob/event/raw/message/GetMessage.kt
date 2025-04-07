/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/9/1
 */

@file:Suppress("unused", "Deprecation")

package cn.rtast.rob.event.raw.message

import cn.rtast.rob.enums.InboundMessageType
import cn.rtast.rob.enums.SegmentType
import cn.rtast.rob.event.raw.GroupSender
import cn.rtast.rob.onebot.OneBotAction
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import kotlin.jvm.JvmName

@Serializable
public data class GetMessage(
    val data: Message,
    val echo: String?
) {
    @Serializable
    public data class Message(
        /**
         * 时间戳
         */
        val time: Long,
        /**
         * 消息类型, 可以是`private` 或 `group`
         */
        @SerialName("message_type")
        val messageType: InboundMessageType,
        /**
         * 数组消息
         */
        val message: List<ArrayMessage>,
        /**
         * 消息ID
         */
        @SerialName("message_id")
        val messageId: Long,
        /**
         * 群聊发送者
         */
        val sender: GroupSender,
        /**
         * 消息ID
         */
        val id: String?,
    ) {
        /**
         * action对象
         */
        @Transient
        lateinit var action: OneBotAction

        /**
         * 下面的函数仅推荐在Java中使用
         */
        public fun getTexts(): List<String> = this.texts
        public fun getText(): String = this.text
    }
}

/**
 * 快速从一个数组消息中获取所有的文字部分
 * 返回一个字符串列表
 */
public val GetMessage.Message.texts
    get() = this.message.filter { it.type == SegmentType.text }.mapNotNull { it.data.text }


/**
 * 快速从一个数组消息中获取所有的文字部分
 * 返回一个拼接好的字符串
 */
public val GetMessage.Message.text
    get() = this.message.filter { it.type == SegmentType.text }.mapNotNull { it.data.text }
        .joinToString("")
