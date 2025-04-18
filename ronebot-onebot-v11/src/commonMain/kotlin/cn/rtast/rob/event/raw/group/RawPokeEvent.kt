/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/9/25
 */


package cn.rtast.rob.event.raw.group

import cn.rtast.rob.actionable.OperatorWithOperatedUserActionable
import cn.rtast.rob.event.raw.info.StrangerInfo
import cn.rtast.rob.onebot.OneBotAction
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
public data class RawPokeEvent(
    /**
     * 戳一戳的类型, 例如`戳一戳`, `揉了揉`, `捏了捏`
     */
    @SerialName("action")
    val pokeAction: String,
    /**
     * 戳一戳后面追加的文字
     */
    val suffix: String,
    /**
     * 戳一戳类型的额外图片URL
     */
    @SerialName("action_img_url")
    val actionImgUrl: String,
    /**
     * 被戳一戳的人的QQ号
     */
    @SerialName("target_id")
    val targetId: Long,
    /**
     * 触发戳一戳的人的QQ号
     */
    @SerialName("user_id")
    val userId: Long,
    /**
     * 群号
     */
    @SerialName("group_id")
    val groupId: Long?,
) : OperatorWithOperatedUserActionable {
    @Transient
    lateinit var action: OneBotAction
    override suspend fun getOperatorInfo(): StrangerInfo.StrangerInfo {
        return action.getStrangerInfo(userId)
    }

    override suspend fun getOperatorMemberInfo(): GroupMemberList.MemberInfo {
        if (groupId == null) {
            error("当前戳一戳为群聊戳一戳无法获取群成员信息")
        }
        return action.getGroupMemberInfo(groupId, userId)
    }

    override suspend fun getOperatedMemberInfo(): GroupMemberList.MemberInfo {
        if (groupId == null) {
            error("当前戳一戳为群聊戳一戳无法获取群成员信息")
        }
        return action.getGroupMemberInfo(groupId, targetId)
    }

    override suspend fun getOperatedInfo(): StrangerInfo.StrangerInfo {
        return action.getStrangerInfo(targetId)
    }
}