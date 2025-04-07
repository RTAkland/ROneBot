/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/9/2
 */


package cn.rtast.rob.event.raw.message

import cn.rtast.rob.onebot.OneBotAction
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import kotlin.jvm.JvmName

@Serializable
public data class RawGroupRevokeMessage(
    /**
     * 群号
     */
    @SerialName("group_id")
    val groupId: Long,
    /**
     * 撤回者QQ号
     */
    @SerialName("user_id")
    val userId: Long,
    /**
     * 消息ID
     */
    @SerialName("message_id")
    val messageId: Long,
    /**
     * 操作者QQ号
     */
    @SerialName("operator_id")
    val operatorId: Long,
) {
    @Transient
    lateinit var action: OneBotAction
}

@Serializable
public data class RawPrivateRevokeMessage(
    /**
     * QQ号
     */
    @SerialName("user_id")
    val userId: Long,
    /**
     * 消息ID
     */
    @SerialName("message_id")
    val messageId: Long,
    /**
     * 操作者QQ号
     */
    @SerialName("operator_id")
    val operatorId: Long,
) {
    @Transient
    lateinit var action: OneBotAction
}