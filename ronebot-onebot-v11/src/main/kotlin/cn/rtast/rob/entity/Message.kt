/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/26
 */

@file:Suppress("unused", "Deprecation")

package cn.rtast.rob.entity

import cn.rtast.rob.actionable.GroupMessageActionable
import cn.rtast.rob.actionable.MessageActionable
import cn.rtast.rob.annotations.ExcludeField
import cn.rtast.rob.entity.lagrange.ForwardMessageId
import cn.rtast.rob.enums.ArrayMessageType
import cn.rtast.rob.onebot.CQMessageChain
import cn.rtast.rob.onebot.MessageChain
import cn.rtast.rob.onebot.NodeMessageChain
import cn.rtast.rob.onebot.OneBotAction
import cn.rtast.rob.segment.Segment
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


private val actionCoroutineScope = CoroutineScope(Dispatchers.IO)

/**
 * 定义了一些数组类型消息体的共有字段
 */
sealed class BaseMessage {
    val message: List<ArrayMessage> = listOf()

    @SerializedName("sub_type")
    val subType: String = ""

    @SerializedName("message_id")
    val messageId: Long = 0L

    @SerializedName("user_id")
    val userId: Long = 0L

    @SerializedName("raw_message")
    val rawMessage: String = ""
    val time: Long = 0L
    val anonymous: Any? = null
}

data class GroupMessage(
    @ExcludeField
    var action: OneBotAction,
    @SerializedName("group_id")
    val groupId: Long,
    var sender: GroupSender
) : GroupMessageActionable, BaseMessage() {
    override suspend fun revoke(delay: Int) {
        super.revoke(delay)
        if (delay != 0) actionCoroutineScope.launch {
            delay(delay * 1000L)
            sender.action.revokeMessage(messageId)
        } else sender.action.revokeMessage(messageId)
    }

    override suspend fun reply(content: Segment): Long? {
        val msg = MessageChain.Builder()
            .addReply(messageId)
            .addSegment(content)
            .build()
        return sender.action.sendGroupMessage(groupId, msg)
    }

    override suspend fun replyAsync(content: Segment) {
        val msg = MessageChain.Builder()
            .addReply(messageId)
            .addSegment(content)
            .build()
        sender.action.sendGroupMessageAsync(groupId, msg)
    }

    override suspend fun reply(content: List<Segment>): Long? {
        val builder = MessageChain.Builder().apply { addReply(messageId) }
        content.forEach { builder.addSegment(it) }
        return sender.action.sendGroupMessage(groupId, builder.build())
    }

    override suspend fun replyAsync(content: List<Segment>) {
        val builder = MessageChain.Builder().apply { addReply(messageId) }
        content.forEach { builder.addSegment(it) }
        sender.action.sendGroupMessageAsync(groupId, builder.build())
    }

    override suspend fun reply(content: MessageChain): Long? {
        val msg = MessageChain.Builder()
            .addReply(messageId)
            .addRawArrayMessage(content.finalArrayMsgList)
            .build()
        return sender.action.sendGroupMessage(groupId, msg)
    }

    override suspend fun replyAsync(content: MessageChain) {
        val msg = MessageChain.Builder()
            .addReply(messageId)
            .addRawArrayMessage(content.finalArrayMsgList)
            .build()
        sender.action.sendGroupMessageAsync(groupId, msg)
    }

    override suspend fun reply(content: String): Long? {
        val msg = MessageChain.Builder().addText(content).build()
        return this.reply(msg)
    }

    override suspend fun replyAsync(content: String) {
        val msg = MessageChain.Builder().addText(content).build()
        this.replyAsync(msg)
    }

    override suspend fun reply(content: CQMessageChain): Long? = this.reply(content.finalString)

    override suspend fun replyAsync(content: CQMessageChain) = this.replyAsync(content.finalString)

    override suspend fun reply(content: NodeMessageChain): ForwardMessageId.ForwardMessageId? =
        sender.action.sendGroupForwardMsg(groupId, content)

    override suspend fun replyAsync(content: NodeMessageChain) =
        sender.action.sendGroupForwardMsgAsync(groupId, content)

    override suspend fun reaction(code: String) = sender.action.reaction(groupId, messageId, code)

    override suspend fun unsetReaction(code: String) = sender.action.reaction(groupId, messageId, code, false)

    override suspend fun setEssence() = sender.action.setEssenceMessage(messageId)

    override suspend fun deleteEssence() = sender.action.deleteEssenceMessage(messageId)

    override suspend fun markAsRead() = sender.action.markAsRead(messageId)
}

data class PrivateMessage(
    @ExcludeField
    var action: OneBotAction,
    val sender: PrivateSender,
) : MessageActionable, BaseMessage() {
    override suspend fun revoke(delay: Int) {
        super.revoke(delay)
        if (delay != 0) actionCoroutineScope.launch {
            delay(delay * 1000L)
            sender.action.revokeMessage(messageId)
        } else sender.action.revokeMessage(messageId)
    }

    override suspend fun reply(content: Segment): Long? {
        val msg = MessageChain.Builder()
            .addReply(messageId)
            .addSegment(content)
            .build()
        return sender.action.sendPrivateMessage(userId, msg)
    }

    override suspend fun replyAsync(content: Segment) {
        val msg = MessageChain.Builder()
            .addReply(messageId)
            .addSegment(content)
            .build()
        sender.action.sendPrivateMessageAsync(userId, msg)
    }

    override suspend fun reply(content: List<Segment>): Long? {
        val builder = MessageChain.Builder().apply { addReply(messageId) }
        content.forEach { builder.addSegment(it) }
        return sender.action.sendPrivateMessage(userId, builder.build())
    }

    override suspend fun replyAsync(content: List<Segment>) {
        val builder = MessageChain.Builder().apply { addReply(messageId) }
        content.forEach { builder.addSegment(it) }
        sender.action.sendPrivateMessageAsync(userId, builder.build())
    }

    override suspend fun reply(content: MessageChain): Long? {
        val msg = MessageChain.Builder()
            .addReply(messageId)
            .addRawArrayMessage(content.finalArrayMsgList)
            .build()
        return sender.action.sendPrivateMessage(userId, msg)
    }

    override suspend fun replyAsync(content: MessageChain) {
        val msg = MessageChain.Builder()
            .addReply(messageId)
            .addRawArrayMessage(content.finalArrayMsgList)
            .build()
        sender.action.sendPrivateMessageAsync(userId, msg)
    }

    override suspend fun reply(content: String): Long? {
        val msg = MessageChain.Builder().addText(content).build()
        return this.reply(msg)
    }

    override suspend fun replyAsync(content: String) {
        val msg = MessageChain.Builder().addText(content).build()
        this.replyAsync(msg)
    }

    override suspend fun reply(content: CQMessageChain): Long? = this.reply(content.finalString)

    override suspend fun replyAsync(content: CQMessageChain) = this.replyAsync(content.finalString)

    override suspend fun reply(content: NodeMessageChain): ForwardMessageId.ForwardMessageId? =
        sender.action.sendPrivateForwardMsg(sender.userId, content)

    override suspend fun replyAsync(content: NodeMessageChain) =
        sender.action.sendPrivateForwardMsgAsync(sender.userId, content)

    override suspend fun markAsRead() = sender.action.markAsRead(messageId)
}

/**
 * 获取数组消息的第一个文字部分如果消息中没有
 * text类型的数据就返回一个空字符串
 * 如果包含了类型为reply的元素说明这个消息是一个回复消息
 * 所以直接就返回一个空白字符串
 */
internal val BaseMessage.first: String
    get() {
        if (this.message.any { it.type == ArrayMessageType.reply }) {
            return ""
        }
        return this.message.find { it.type == ArrayMessageType.text }?.data?.text ?: ""
    }

/**
 * 获取第一个文字部分然后将其使用空格分割
 * 然后获取分割后的第一个部分将其返回作为命令部分
 */
internal val BaseMessage.command get() = this.first.split(" ").first()

/**
 * 快速从一个数组消息中获取所有的文字部分
 * 返回一个字符串列表
 */
val BaseMessage.texts get() = this.message.filter { it.type == ArrayMessageType.text }.mapNotNull { it.data.text }


/**
 * 快速从一个数组消息中获取所有的文字部分
 * 返回一个拼接好的字符串
 */
val BaseMessage.text
    get() = this.message.filter { it.type == ArrayMessageType.text }.mapNotNull { it.data.text }
        .joinToString("")

/**
 * 快速从一个数组消息中获取图片(包括普通图片和表情包)
 * 返回一个[MessageData.InboundImage]数组
 */
val BaseMessage.images
    get() = this.message.filter { it.type == ArrayMessageType.image }.map { it.data }
        .map { MessageData.InboundImage(it.file!!, it.filename!!, it.url!!, it.summary!!, it.subType!!) }

/**
 * 快速从一个数组消息中获取mface(商城表情)
 * 返回一个[MessageData.InboundMFace]数组
 */
val BaseMessage.mfaces
    get() = this.message.filter { it.type == ArrayMessageType.mface }.map { it.data }
        .map { MessageData.InboundMFace(it.emojiId!!, it.emojiPackageId!!, it.key!!, it.url!!, it.summary!!) }

/**
 * 快速从一个数组消息中获取mface(商城表情)
 * 返回一个[MessageData.InboundMFace]对象
 */
val BaseMessage.mface
    get() = this.message.filter { it.type == ArrayMessageType.mface }.map { it.data }
        .map { MessageData.InboundMFace(it.emojiId!!, it.emojiPackageId!!, it.key!!, it.url!!, it.summary!!) }
        .firstOrNull()

/**
 * 快速从一个数组消息中获取mface(商城表情)
 * 返回一个[MessageData.InboundFace]数组
 */
val BaseMessage.faces
    get() = this.message.filter { it.type == ArrayMessageType.face }
        .map { MessageData.InboundFace(it.data.id.toString(), it.data.large) }