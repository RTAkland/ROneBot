/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 2025/4/17 23:51
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.actionable

import cn.rtast.rob.event.raw.group.GroupMemberList
import cn.rtast.rob.event.raw.info.StrangerInfo

public interface OperatorActionable {
    /**
     * 获取操作者的资料信息
     */
    public suspend fun getOperatorInfo(): StrangerInfo.StrangerInfo
}

public interface OperatorWithOperatedUserActionable : OperatorActionable {
    /**
     * 获取操作者的群信息
     */
    public suspend fun getOperatorMemberInfo(): GroupMemberList.MemberInfo

    /**
     * 获取被操作人的信息
     */
    public suspend fun getOperatedMemberInfo(): GroupMemberList.MemberInfo

    /**
     * 获取操作者的资料信息
     */
    public suspend fun getOperatedInfo(): StrangerInfo.StrangerInfo
}