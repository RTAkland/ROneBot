/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/1
 */


package cn.rtast.rob.event.raw.group

import cn.rtast.rob.actionable.OperatorWithOperatedUserActionable
import cn.rtast.rob.event.raw.info.StrangerInfo
import cn.rtast.rob.onebot.OneBotAction
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
public data class ReactionEvent(
    /**
     * 群号
     */
    @SerialName("group_id")
    val groupId: Long,
    /**
     * 操作者QQ号
     */
    @SerialName("operator_Id")
    val operatorId: Long,
    /**
     * 表示有几个相同的code表情
     */
    val count: Int,
    /**
     * 消息ID
     */
    @SerialName("message_id")
    val messageId: Long,
    /**
     * 自身的QQ号
     */
    @SerialName("self_id")
    val selfId: Long,
    /**
     * code表示一个表情ID
     */
    val code: String
) : OperatorWithOperatedUserActionable {
    @Transient
    lateinit var action: OneBotAction
    override suspend fun getOperatorMemberInfo(): GroupMemberList.MemberInfo {
        return action.getGroupMemberInfo(groupId, operatorId)
    }

    override suspend fun getOperatorInfo(): StrangerInfo.StrangerInfo {
        return action.getStrangerInfo(operatorId)
    }

    override suspend fun getOperatedMemberInfo(): GroupMemberList.MemberInfo {
        return action.getGroupMemberInfo(groupId, selfId)
    }

    override suspend fun getOperatedInfo(): StrangerInfo.StrangerInfo {
        return action.getStrangerInfo(selfId)
    }
}