/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/26
 */

@file:Suppress("unused", "Deprecation")
@file:OptIn(ExperimentalUuidApi::class)

package cn.rtast.rob.event.raw.message

import cn.rtast.rob.actionable.GroupMessageActionable
import cn.rtast.rob.actionable.MessageActionable
import cn.rtast.rob.entity.IGroupMessage
import cn.rtast.rob.entity.IPrivateMessage
import cn.rtast.rob.enums.SegmentType
import cn.rtast.rob.event.raw.GroupSender
import cn.rtast.rob.event.raw.PrivateSender
import cn.rtast.rob.onebot.MessageChain
import cn.rtast.rob.onebot.NodeMessageChain
import cn.rtast.rob.onebot.OneBotAction
import cn.rtast.rob.onebot.dsl.messageChain
import cn.rtast.rob.segment.*
import kotlinx.coroutines.delay
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import love.forte.plugin.suspendtrans.annotation.JvmAsync
import love.forte.plugin.suspendtrans.annotation.JvmBlocking
import kotlin.time.Duration
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid


/**
 * 定义了一些数组类型消息体的共有字段
 */
@Serializable
public sealed class BaseMessage {
    /**
     * 时间戳
     */
    public val time: Long = 0L

    /**
     * 是否为匿名
     */
    public val anonymous: String? = null

    /**
     * 数组消息
     */
    public val message: List<ArrayMessage> = emptyList()

    /**
     * 消息子类型
     */
    @SerialName("sub_type")
    public val subType: String = ""

    /**
     * 消息ID
     */
    @SerialName("message_id")
    public val messageId: Long = 0L

    /**
     * 用户QQ号
     */
    @SerialName("user_id")
    public val userId: Long = 0L

    /**
     * CQ码消息
     */
    @SerialName("raw_message")
    public val rawMessage: String = ""

    /**
     * 消息样式
     */
    @SerialName("message_style")
    public val messageStyle: MessageStyle? = null

    /**
     * 下面的方法都是推荐仅在Java中使用
     */
    public fun getTexts(): List<String> = this.texts

    public fun getText(): String = this.text

    public fun getImages(): List<ImageSegment> = this.images

    public fun getMFaces(): List<MFaceSegment> = this.mFaces

    public fun getMFace(): MFaceSegment? = this.mFace

    public fun getFaces(): List<FaceSegment> = this.faces

    public fun filterAndSerializeJvm(type: SegmentType): List<MessageSegment> = this.filterAndSerialize(type)

    public fun toPlainTextJvm(): String = this.text
}

@Serializable
public data class GroupMessage(
    /**
     * 群号
     */
    @SerialName("group_id")
    val groupId: Long,
    /**
     * 群聊发送者
     */
    var sender: GroupSender,
    override var sessionId: Uuid? = null
) : GroupMessageActionable, BaseMessage(), IGroupMessage {
    @Transient
    lateinit var action: OneBotAction

    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    override suspend fun revokeId(delay: Int, messageId: Long) {
        if (delay != 0) delay(delay * 1000L)
        action.revokeMessage(messageId)
    }

    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    override suspend fun revokeId(delay: Duration, messageId: Long) {
        if (delay.inWholeMilliseconds != 0L) delay(delay)
        action.revokeMessage(messageId)
    }

    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    override suspend fun revoke(delay: Duration) {
        if (delay.inWholeMilliseconds != 0L) delay(delay)
        action.revokeMessage(messageId)
    }

    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    override suspend fun revoke(delay: Int) {
        if (delay != 0) delay(delay * 1000L)
        action.revokeMessage(messageId)
    }

    @JvmBlocking(suffix = "JvmBlocking")
    override suspend fun reply(content: Segment): Long? {
        val msg = MessageChain.Builder()
            .addReply(messageId)
            .addSegment(content)
            .build()
        return sender.action.sendGroupMessage(groupId, msg)
    }

    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    override suspend fun replyAsync(content: Segment) {
        val msg = MessageChain.Builder()
            .addReply(messageId)
            .addSegment(content)
            .build()
        sender.action.sendGroupMessageAsync(groupId, msg)
    }

    @JvmBlocking(suffix = "JvmBlocking")
    override suspend fun reply(content: List<Segment>): Long? {
        val builder = MessageChain.Builder().apply { addReply(messageId) }
        content.forEach { builder.addSegment(it) }
        return sender.action.sendGroupMessage(groupId, builder.build())
    }

    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    override suspend fun replyAsync(content: List<Segment>) {
        val builder = MessageChain.Builder().apply { addReply(messageId) }
        content.forEach { builder.addSegment(it) }
        sender.action.sendGroupMessageAsync(groupId, builder.build())
    }

    @JvmBlocking(suffix = "JvmBlocking")
    override suspend fun reply(content: MessageChain): Long? {
        val msg = MessageChain.Builder()
            .addReply(messageId)
            .addRawArrayMessage(content.finalArrayMsgList)
            .build()
        return sender.action.sendGroupMessage(groupId, msg)
    }

    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    override suspend fun replyAsync(content: MessageChain) {
        val msg = MessageChain.Builder()
            .addReply(messageId)
            .addRawArrayMessage(content.finalArrayMsgList)
            .build()
        sender.action.sendGroupMessageAsync(groupId, msg)
    }

    @JvmBlocking(suffix = "JvmBlocking")
    override suspend fun reply(content: String): Long? {
        val msg = MessageChain.Builder().addText(content).build()
        return this.reply(msg)
    }

    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    override suspend fun replyAsync(content: String) {
        val msg = MessageChain.Builder().addText(content).build()
        this.replyAsync(msg)
    }

    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    override suspend fun reply(content: NodeMessageChain): ForwardMessageId.ForwardMessageId? =
        sender.action.sendGroupForwardMsg(groupId, content)

    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    override suspend fun replyAsync(content: NodeMessageChain): Unit =
        sender.action.sendGroupForwardMsgAsync(groupId, content)

    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    override suspend fun reaction(code: String): Unit = sender.action.reaction(groupId, messageId, code)

    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    override suspend fun unsetReaction(code: String): Unit = sender.action.reaction(groupId, messageId, code, false)

    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    override suspend fun setEssence(): Unit = sender.action.setEssenceMessage(messageId)

    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    override suspend fun deleteEssence(): Unit = sender.action.deleteEssenceMessage(messageId)

    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    override suspend fun markAsRead(): Unit = sender.action.markAsRead(messageId)

    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    override suspend fun sendMessageAsync(content: MessageChain) {
        this.action.sendGroupMessageAsync(groupId, content)
    }

    @JvmBlocking(suffix = "JvmBlocking")
    override suspend fun sendMessage(content: MessageChain): Long? {
        return this.action.sendGroupMessage(groupId, content)
    }

    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    override suspend fun sendMessageAsync(content: String) {
        this.action.sendGroupMessageAsync(groupId, content)
    }

    @JvmBlocking(suffix = "JvmBlocking")
    override suspend fun sendMessage(content: String): Long? {
        return this.action.sendGroupMessage(groupId, content)
    }

    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    override suspend fun sendMessageAsync(content: Segment) {
        this.action.sendGroupMessageAsync(groupId, content)
    }

    @JvmBlocking(suffix = "JvmBlocking")
    override suspend fun sendMessage(content: Segment): Long? {
        return this.action.sendGroupMessage(groupId, content)
    }

    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    override suspend fun sendMessageAsync(content: List<Segment>) {
        val msg = messageChain {
            content.forEach {
                addSegment(it)
            }
        }
        this.action.sendGroupMessageAsync(groupId, msg)
    }

    @JvmBlocking(suffix = "JvmBlocking")
    override suspend fun sendMessage(content: List<Segment>): Long? {
        val msg = messageChain {
            content.forEach {
                addSegment(it)
            }
        }
        return this.action.sendGroupMessage(groupId, msg)
    }
}

@Serializable
public data class PrivateMessage(
    /**
     * 私聊发送者
     */
    val sender: PrivateSender,
    override var sessionId: Uuid? = null
) : MessageActionable, BaseMessage(), IPrivateMessage {

    @Transient
    lateinit var action: OneBotAction

    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    override suspend fun revokeId(delay: Int, messageId: Long) {
        if (delay != 0) delay(delay * 1000L)
        action.revokeMessage(messageId)
    }

    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    override suspend fun revokeId(delay: Duration, messageId: Long) {
        if (delay.inWholeMilliseconds != 0L) delay(delay)
        action.revokeMessage(messageId)
    }

    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    override suspend fun revoke(delay: Int) {
        if (delay != 0) delay(delay * 1000L)
        action.revokeMessage(messageId)
    }

    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    override suspend fun revoke(delay: Duration) {
        if (delay.inWholeMilliseconds != 0L) delay(delay)
        action.revokeMessage(messageId)
    }

    @JvmBlocking(suffix = "JvmBlocking")
    override suspend fun reply(content: Segment): Long? {
        val msg = MessageChain.Builder()
            .addReply(messageId)
            .addSegment(content)
            .build()
        return sender.action.sendPrivateMessage(userId, msg)
    }

    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    override suspend fun replyAsync(content: Segment) {
        val msg = MessageChain.Builder()
            .addReply(messageId)
            .addSegment(content)
            .build()
        sender.action.sendPrivateMessageAsync(userId, msg)
    }

    @JvmBlocking(suffix = "JvmBlocking")
    override suspend fun reply(content: List<Segment>): Long? {
        val builder = MessageChain.Builder().apply { addReply(messageId) }
        content.forEach { builder.addSegment(it) }
        return sender.action.sendPrivateMessage(userId, builder.build())
    }

    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    override suspend fun replyAsync(content: List<Segment>) {
        val builder = MessageChain.Builder().apply { addReply(messageId) }
        content.forEach { builder.addSegment(it) }
        sender.action.sendPrivateMessageAsync(userId, builder.build())
    }

    @JvmBlocking(suffix = "JvmBlocking")
    override suspend fun reply(content: MessageChain): Long? {
        val msg = MessageChain.Builder()
            .addReply(messageId)
            .addRawArrayMessage(content.finalArrayMsgList)
            .build()
        return sender.action.sendPrivateMessage(userId, msg)
    }

    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    override suspend fun replyAsync(content: MessageChain) {
        val msg = MessageChain.Builder()
            .addReply(messageId)
            .addRawArrayMessage(content.finalArrayMsgList)
            .build()
        sender.action.sendPrivateMessageAsync(userId, msg)
    }

    @JvmBlocking(suffix = "JvmBlocking")
    override suspend fun reply(content: String): Long? {
        val msg = MessageChain.Builder().addText(content).build()
        return this.reply(msg)
    }

    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    override suspend fun replyAsync(content: String) {
        val msg = MessageChain.Builder().addText(content).build()
        this.replyAsync(msg)
    }

    @JvmBlocking(suffix = "JvmBlocking")
    override suspend fun reply(content: NodeMessageChain): ForwardMessageId.ForwardMessageId? =
        sender.action.sendPrivateForwardMsg(sender.userId, content)

    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    override suspend fun replyAsync(content: NodeMessageChain): Unit =
        sender.action.sendPrivateForwardMsgAsync(sender.userId, content)

    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    override suspend fun markAsRead(): Unit = sender.action.markAsRead(messageId)

    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    override suspend fun sendMessageAsync(content: MessageChain) {
        this.action.sendPrivateMessageAsync(userId, content)
    }

    @JvmBlocking(suffix = "JvmBlocking")
    override suspend fun sendMessage(content: MessageChain): Long? {
        return this.action.sendPrivateMessage(userId, content)
    }

    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    override suspend fun sendMessageAsync(content: String) {
        this.action.sendPrivateMessageAsync(userId, content)
    }

    @JvmBlocking(suffix = "JvmBlocking")
    override suspend fun sendMessage(content: String): Long? {
        return this.action.sendPrivateMessage(userId, content)
    }

    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    override suspend fun sendMessageAsync(content: Segment) {
        this.action.sendPrivateMessageAsync(userId, content)
    }

    @JvmBlocking(suffix = "JvmBlocking")
    override suspend fun sendMessage(content: Segment): Long? {
        return this.action.sendPrivateMessage(userId, content)
    }

    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    override suspend fun sendMessageAsync(content: List<Segment>) {
        val msg = messageChain {
            content.forEach {
                addSegment(it)
            }
        }
        this.action.sendPrivateMessageAsync(userId, msg)
    }

    @JvmBlocking(suffix = "JvmBlocking")
    override suspend fun sendMessage(content: List<Segment>): Long? {
        val msg = messageChain {
            content.forEach {
                addSegment(it)
            }
        }
        return this.action.sendPrivateMessage(userId, msg)
    }
}

/**
 * 获取数组消息的第一个文字部分如果消息中没有
 * text类型的数据就返回一个空字符串
 * 如果包含了类型为reply的元素说明这个消息是一个回复消息
 * 所以直接就返回一个空白字符串
 */
internal val BaseMessage.first: String
    get() {
        if (this.message.any { it.type == SegmentType.reply }) {
            return ""
        }
        return this.message.find { it.type == SegmentType.text }?.data?.text ?: ""
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
public val BaseMessage.texts get() = this.message.filter { it.type == SegmentType.text }.mapNotNull { it.data.text }


/**
 * 快速从一个数组消息中获取所有的文字部分
 * 返回一个拼接好的字符串
 */
public val BaseMessage.text
    get() = this.message.filter { it.type == SegmentType.text }.mapNotNull { it.data.text }
        .joinToString("")

/**
 * 快速从一个数组消息中获取图片(包括普通图片和表情包)
 */
public val BaseMessage.images
    get() = this.message.filter { it.type == SegmentType.image }.map { it.data }
        .map { ImageSegment(it.file!!, it.filename!!, it.url!!, it.summary!!, it.subType!!) }

/**
 * 快速从一个数组消息中获取mface(商城表情)
 */
public val BaseMessage.mFaces
    get() = this.message.filter { it.type == SegmentType.mface }.map { it.data }
        .map { MFaceSegment(it.emojiId!!, it.emojiPackageId!!, it.key!!, it.url!!, it.summary!!) }

/**
 * 快速从一个数组消息中获取mface(商城表情)
 */
public val BaseMessage.mFace
    get() = this.mFaces.firstOrNull()

/**
 * 快速从一个数组消息中获取mface(商城表情)
 */
public val BaseMessage.faces
    get() = this.message.filter { it.type == SegmentType.face }
        .map { FaceSegment(it.data.id?.toInt()!!, it.data.large?.toBoolean()!!) }

/**
 * 过滤器
 */
public fun BaseMessage.filter(type: SegmentType): List<ArrayMessage> = this.message.filter { it.type == type }

/**
 * 过滤器但是过滤后再将其序列化
 */
public fun BaseMessage.filterAndSerialize(type: SegmentType): List<MessageSegment> = this.filter(type).serialize()

/**
 * 将数组消息段中的所有文本提取出
 */
public fun BaseMessage.toPlainText(): String = text

/**
 * 快速撤回一个指定的消息ID
 * ```kotlin
 * val action: OneBotAction = ...
 * 1L.revoke(action)
 * ```
 */
public suspend fun Long.revoke(action: OneBotAction): Unit = this.revoke(0, action)

/**
 * 快速撤回一个消息但是有延迟
 */
public suspend fun Long.revoke(delay: Int, action: OneBotAction) {
    if (delay != 0) delay(delay * 1000L)
    action.revokeMessage(this@revoke)
}

/**
 * 撤回一个消息但是使用[Duration]对象
 */
public suspend fun Long.revoke(delay: Duration, action: OneBotAction) {
    if (delay.inWholeMilliseconds != 0L) delay(delay)
    action.revokeMessage(this@revoke)
}