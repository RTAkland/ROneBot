/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/9/1
 */

@file:Suppress("unused", "Deprecation")

package cn.rtast.rob.event.raw

import cn.rtast.rob.enums.InboundMessageType
import cn.rtast.rob.enums.SegmentType
import cn.rtast.rob.onebot.OneBotAction
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

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

/**
 * 快速从一个数组消息中获取图片(包括普通图片和表情包)
 * 返回一个[MessageData.InboundImage]数组
 */
@Deprecated("已废弃的API, 请使用List<ArrayMessage>.serialize()")
public val GetMessage.Message.images
    get() = this.message.filter { it.type == SegmentType.image }.map { it.data }
        .map { MessageData.InboundImage(it.file!!, it.filename!!, it.url!!, it.summary!!, it.subType!!) }

/**
 * 快速从一个数组消息中获取mface(商城表情)
 * 返回一个[MessageData.InboundMFace]数组
 */
@Deprecated("已废弃的API, 请使用List<ArrayMessage>.serialize()")
public val GetMessage.Message.mFaces
    get() = this.message.filter { it.type == SegmentType.mface }.map { it.data }
        .map { MessageData.InboundMFace(it.emojiId!!, it.emojiPackageId!!, it.key!!, it.url!!, it.summary!!) }

/**
 * 快速从一个数组消息中获取mface(商城表情)
 * 返回一个[MessageData.InboundMFace]对象
 */
@Deprecated("已废弃的API, 请使用List<ArrayMessage>.serialize()")
public val GetMessage.Message.mFace
    get() = this.message.filter { it.type == SegmentType.mface }.map { it.data }
        .map { MessageData.InboundMFace(it.emojiId!!, it.emojiPackageId!!, it.key!!, it.url!!, it.summary!!) }
        .firstOrNull()

/**
 * 快速从一个数组消息中获取mface(商城表情)
 * 返回一个[MessageData.InboundFace]数组
 */
@Deprecated("已废弃的API, 请使用List<ArrayMessage>.serialize()")
public val GetMessage.Message.faces
    get() = this.message.filter { it.type == SegmentType.face }
        .map { MessageData.InboundFace(it.data.id.toString(), it.data.large) }