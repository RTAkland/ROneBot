/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/9/1
 */

@file:Suppress("unused")

package cn.rtast.rob.entity

import cn.rtast.rob.entity.GetMessage.Message
import cn.rtast.rob.enums.SegmentType
import cn.rtast.rob.enums.InboundMessageType
import com.google.gson.annotations.SerializedName

data class GetMessage(
    val data: Message,
    val echo: String?
) {
    data class Message(
        val time: Long,
        @SerializedName("message_type")
        val messageType: InboundMessageType,
        val message: List<ArrayMessage>,
        @SerializedName("message_id")
        val messageId: Long,
        val sender: GroupSender,
        val id: String?,
    )
}

/**
 * 快速从一个数组消息中获取所有的文字部分
 * 返回一个字符串列表
 */
val Message.texts get() = this.message.filter { it.type == SegmentType.text }.mapNotNull { it.data.text }


/**
 * 快速从一个数组消息中获取所有的文字部分
 * 返回一个拼接好的字符串
 */
val Message.text
    get() = this.message.filter { it.type == SegmentType.text }.mapNotNull { it.data.text }
        .joinToString("")

/**
 * 快速从一个数组消息中获取图片(包括普通图片和表情包)
 * 返回一个[MessageData.InboundImage]数组
 */
val Message.images
    get() = this.message.filter { it.type == SegmentType.image }.map { it.data }
        .map { MessageData.InboundImage(it.file!!, it.filename!!, it.url!!, it.summary!!, it.subType!!) }

/**
 * 快速从一个数组消息中获取mface(商城表情)
 * 返回一个[MessageData.InboundMFace]数组
 */
val Message.mfaces
    get() = this.message.filter { it.type == SegmentType.mface }.map { it.data }
        .map { MessageData.InboundMFace(it.emojiId!!, it.emojiPackageId!!, it.key!!, it.url!!, it.summary!!) }

/**
 * 快速从一个数组消息中获取mface(商城表情)
 * 返回一个[MessageData.InboundMFace]对象
 */
val Message.mface
    get() = this.message.filter { it.type == SegmentType.mface }.map { it.data }
        .map { MessageData.InboundMFace(it.emojiId!!, it.emojiPackageId!!, it.key!!, it.url!!, it.summary!!) }
        .firstOrNull()

/**
 * 快速从一个数组消息中获取mface(商城表情)
 * 返回一个[MessageData.InboundFace]数组
 */
val Message.faces
    get() = this.message.filter { it.type == SegmentType.face }
        .map { MessageData.InboundFace(it.data.id.toString(), it.data.large) }