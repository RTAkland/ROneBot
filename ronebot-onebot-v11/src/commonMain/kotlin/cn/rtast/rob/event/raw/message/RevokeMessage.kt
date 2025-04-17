/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/9/2
 */


package cn.rtast.rob.event.raw.message

import cn.rtast.rob.actionable.OperatorActionable
import cn.rtast.rob.actionable.OperatorWithOperatedUserActionable
import cn.rtast.rob.event.raw.group.GroupMemberList
import cn.rtast.rob.event.raw.info.StrangerInfo
import cn.rtast.rob.onebot.OneBotAction
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

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
        return action.getGroupMemberInfo(groupId, userId)
    }

    override suspend fun getOperatedInfo(): StrangerInfo.StrangerInfo {
        return action.getStrangerInfo(userId)
    }
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
) : OperatorActionable {
    @Transient
    lateinit var action: OneBotAction
    override suspend fun getOperatorInfo(): StrangerInfo.StrangerInfo {
        return action.getStrangerInfo(userId)
    }
}