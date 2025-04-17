/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/24
 */


package cn.rtast.rob.event.raw.group

import cn.rtast.rob.actionable.OperatorWithOperatedUserActionable
import cn.rtast.rob.event.raw.info.StrangerInfo
import cn.rtast.rob.onebot.OneBotAction
import kotlinx.serialization.SerialName

public data class RawBotBeKickEvent(
    @SerialName("group_id")
    val groupId: Long,
    val operator: Long,
    val time: Long,
    @SerialName("user_id")
    val userId: Long,
    val action: OneBotAction
) : OperatorWithOperatedUserActionable {
    override suspend fun getOperatorMemberInfo(): GroupMemberList.MemberInfo {
        return action.getGroupMemberInfo(groupId, operator)
    }

    override suspend fun getOperatorInfo(): StrangerInfo.StrangerInfo {
        return action.getStrangerInfo(operator)
    }

    override suspend fun getOperatedMemberInfo(): GroupMemberList.MemberInfo {
        return action.getGroupMemberInfo(groupId, userId)
    }

    override suspend fun getOperatedInfo(): StrangerInfo.StrangerInfo {
        return action.getStrangerInfo(userId)
    }
}

public data class RawMemberKickEvent(
    @SerialName("group_id")
    val groupId: Long,
    val operator: Long,
    val time: Long,
    @SerialName("user_id")
    val userId: Long,
    val action: OneBotAction
) : OperatorWithOperatedUserActionable {
    override suspend fun getOperatorMemberInfo(): GroupMemberList.MemberInfo {
        return action.getGroupMemberInfo(groupId, operator)
    }

    override suspend fun getOperatorInfo(): StrangerInfo.StrangerInfo {
        return action.getStrangerInfo(operator)
    }

    override suspend fun getOperatedMemberInfo(): GroupMemberList.MemberInfo {
        return action.getGroupMemberInfo(groupId, userId)
    }

    override suspend fun getOperatedInfo(): StrangerInfo.StrangerInfo {
        return action.getStrangerInfo(userId)
    }
}