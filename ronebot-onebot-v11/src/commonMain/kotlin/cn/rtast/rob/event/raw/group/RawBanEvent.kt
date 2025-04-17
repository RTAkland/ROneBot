/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/24
 */


package cn.rtast.rob.event.raw.group

import cn.rtast.rob.actionable.OperatorWithOperatedUserActionable
import cn.rtast.rob.event.raw.info.StrangerInfo
import cn.rtast.rob.onebot.OneBotAction
import kotlinx.serialization.Transient

/**
 * 成员被禁言
 */
public data class RawBanEvent(
    /**
     * 群号
     */
    val groupId: Long,
    /**
     * 操作者的QQ
     */
    val operator: Long,
    /**
     * 时长, 秒
     * 在被解除禁言时始终为0
     */
    val duration: Int,
    /**
     * 时间戳
     */
    val time: Long,
    /**
     * 被禁言的人
     */
    val userId: Long,
    val action: OneBotAction
) : OperatorWithOperatedUserActionable {

    /**
     * 判断是否为解除禁言
     */
    @Transient
    public var isPardon: Boolean = false

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

/**
 * 成员被解除禁言
 */
public typealias RawPardonBanEvent = RawBanEvent