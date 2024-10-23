/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/26
 */

@file:Suppress("unused")

package cn.rtast.rob.entity

import cn.rtast.rob.ROneBotFactory
import cn.rtast.rob.ROneBotFactory.actionCoroutineScope
import cn.rtast.rob.actionable.GroupMessageActionable
import cn.rtast.rob.actionable.MessageActionable
import cn.rtast.rob.entity.lagrange.ForwardMessageId
import cn.rtast.rob.util.ob.CQMessageChain
import cn.rtast.rob.util.ob.MessageChain
import cn.rtast.rob.util.ob.NodeMessageChain
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


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
}

data class GroupMessage(
    @SerializedName("group_id")
    val groupId: Long,
    var sender: GroupSender
) : GroupMessageActionable, BaseMessage() {
    override suspend fun revoke(delay: Int) {
        super.revoke(delay)
        if (delay != 0) actionCoroutineScope.launch {
            delay(delay * 1000L)
            ROneBotFactory.action.revokeMessage(messageId)
        } else ROneBotFactory.action.revokeMessage(messageId)
    }

    override suspend fun reply(content: MessageChain): Long? {
        val msg = MessageChain.Builder()
            .addReply(messageId)
            .addRawArrayMessage(content.finalArrayMsgList)
            .build()
        return ROneBotFactory.action.sendGroupMessage(groupId, msg)
    }

    override suspend fun replyAsync(content: MessageChain) {
        val msg = MessageChain.Builder()
            .addReply(messageId)
            .addRawArrayMessage(content.finalArrayMsgList)
            .build()
        ROneBotFactory.action.sendGroupMessageAsync(groupId, msg)
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

    override suspend fun reply(content: NodeMessageChain): ForwardMessageId.Data? =
        ROneBotFactory.action.sendGroupForwardMsg(groupId, content)

    override suspend fun replyAsync(content: NodeMessageChain) =
        ROneBotFactory.action.sendGroupForwardMsgAsync(groupId, content)

    override suspend fun reaction(code: String) = ROneBotFactory.action.reaction(groupId, messageId, code)

    override suspend fun unsetReaction(code: String) = ROneBotFactory.action.reaction(groupId, messageId, code, false)

    override suspend fun setEssence() = ROneBotFactory.action.setEssenceMessage(messageId)

    override suspend fun deleteEssence() = ROneBotFactory.action.deleteEssenceMessage(messageId)

    override suspend fun markAsRead() = ROneBotFactory.action.markAsRead(messageId)
}

data class PrivateMessage(
    @SerializedName("raw_message")
    val sender: PrivateSender,
) : MessageActionable, BaseMessage() {
    override suspend fun revoke(delay: Int) {
        super.revoke(delay)
        if (delay != 0) actionCoroutineScope.launch {
            delay(delay * 1000L)
            ROneBotFactory.action.revokeMessage(messageId)
        } else ROneBotFactory.action.revokeMessage(messageId)
    }

    override suspend fun reply(content: MessageChain): Long? {
        val msg = MessageChain.Builder()
            .addReply(messageId)
            .addRawArrayMessage(content.finalArrayMsgList)
            .build()
        return ROneBotFactory.action.sendPrivateMessage(userId, msg)
    }

    override suspend fun replyAsync(content: MessageChain) {
        val msg = MessageChain.Builder()
            .addReply(messageId)
            .addRawArrayMessage(content.finalArrayMsgList)
            .build()
        ROneBotFactory.action.sendPrivateMessageAsync(userId, msg)
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

    override suspend fun reply(content: NodeMessageChain): ForwardMessageId.Data? =
        ROneBotFactory.action.sendPrivateForwardMsg(sender.userId, content)

    override suspend fun replyAsync(content: NodeMessageChain) =
        ROneBotFactory.action.sendPrivateForwardMsgAsync(sender.userId, content)

    override suspend fun markAsRead() = ROneBotFactory.action.markAsRead(messageId)
}