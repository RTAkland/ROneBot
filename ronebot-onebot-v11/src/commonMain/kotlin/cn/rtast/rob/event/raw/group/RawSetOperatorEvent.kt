/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/24
 */


package cn.rtast.rob.event.raw.group

import cn.rtast.rob.actionable.OperatorWithOperatedUserActionable
import cn.rtast.rob.event.raw.info.StrangerInfo
import cn.rtast.rob.onebot.OneBotAction

public data class RawSetOperatorEvent(
    val groupId: Long,
    val operator: Long,
    val time: Long,
    val userId: Long,
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

public typealias RawUnsetOperatorEvent = RawSetOperatorEvent

