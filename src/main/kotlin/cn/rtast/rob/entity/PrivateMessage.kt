/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/26
 */


package cn.rtast.rob.entity

import cn.rtast.rob.ROneBotFactory
import cn.rtast.rob.ROneBotFactory.actionCoroutineScope
import cn.rtast.rob.entity.internal.Actionable
import cn.rtast.rob.exceptions.IllegalDelayException
import cn.rtast.rob.util.ob.MessageChain
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class PrivateMessage(
    @SerializedName("sub_type")
    val subType: String,
    @SerializedName("message_id")
    val messageId: Long,
    @SerializedName("user_id")
    val userId: Long,
    val message: Array<ArrayMessage>,
    @SerializedName("raw_message")
    val rawMessage: String,
    val sender: Sender,
    val time: Long,
) : Actionable {
    override suspend fun revoke(delay: Int) {
        if (delay < 0) {
            throw IllegalDelayException("Delay second(s) must great than 0 or equals to 0! >>> $delay")
        }
        if (delay != 0) actionCoroutineScope.launch {
            delay(delay * 1000L)
            ROneBotFactory.action.revokeMessage(messageId)
        } else ROneBotFactory.action.revokeMessage(messageId)
    }

    override suspend fun reply(content: MessageChain) {
        val msg = MessageChain.Builder()
            .addReply(messageId)
            .addRawArrayMessage(content.finalArrayMsgList)
            .build()
        ROneBotFactory.action.sendPrivateMessage(userId, msg)
    }

    override suspend fun reply(content: String) {
        val msg = MessageChain.Builder().addText(content).build()
        this.reply(msg)
    }
}