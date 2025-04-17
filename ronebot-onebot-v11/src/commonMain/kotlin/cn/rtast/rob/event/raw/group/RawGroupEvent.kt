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
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
public data class RawMemberBeInviteEvent(
    @SerialName("group_id")
    val groupId: Long,
    @SerialName("user_id")
    val userId: Long,
    val operator: Long,
    val time: Long,
) : OperatorWithOperatedUserActionable {
    @Transient
    lateinit var action: OneBotAction
    override suspend fun getOperatorMemberInfo(): GroupMemberList.MemberInfo {
        return action.getGroupMemberInfo(groupId, operator)
    }

    override suspend fun getOperatedMemberInfo(): GroupMemberList.MemberInfo {
        return action.getGroupMemberInfo(groupId, userId)
    }

    override suspend fun getOperatedInfo(): StrangerInfo.StrangerInfo {
        return action.getStrangerInfo(userId)
    }

    override suspend fun getOperatorInfo(): StrangerInfo.StrangerInfo {
        return action.getStrangerInfo(operator)
    }
}

public data class RawJoinRequestApproveEvent(
    val groupId: Long,
    val userId: Long,
    val operator: Long,
    val time: Long,
    val action: OneBotAction
) : OperatorWithOperatedUserActionable {
    override suspend fun getOperatorMemberInfo(): GroupMemberList.MemberInfo {
        return action.getGroupMemberInfo(groupId, operator)
    }

    override suspend fun getOperatedMemberInfo(): GroupMemberList.MemberInfo {
        return action.getGroupMemberInfo(groupId, userId)
    }

    override suspend fun getOperatedInfo(): StrangerInfo.StrangerInfo {
        return action.getStrangerInfo(userId)
    }

    override suspend fun getOperatorInfo(): StrangerInfo.StrangerInfo {
        return action.getStrangerInfo(operator)
    }
}

public data class RawGroupMemberLeaveEvent(
    val groupId: Long,
    val userId: Long,
    val operator: Long,
    val time: Long,
    val action: OneBotAction
) : OperatorWithOperatedUserActionable {
    override suspend fun getOperatorMemberInfo(): GroupMemberList.MemberInfo {
        return action.getGroupMemberInfo(groupId, operator)
    }

    override suspend fun getOperatedMemberInfo(): GroupMemberList.MemberInfo {
        return action.getGroupMemberInfo(groupId, userId)
    }

    override suspend fun getOperatedInfo(): StrangerInfo.StrangerInfo {
        return action.getStrangerInfo(userId)
    }

    override suspend fun getOperatorInfo(): StrangerInfo.StrangerInfo {
        return action.getStrangerInfo(operator)
    }
}